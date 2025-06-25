package com.example.autoever_1st.reservation.service.Impl;

import com.example.autoever_1st.auth.entities.Member;
import com.example.autoever_1st.auth.repository.MemberRepository;
import com.example.autoever_1st.common.exception.CustomStatus;
import com.example.autoever_1st.common.exception.exception_class.business.DataNotFoundException;
import com.example.autoever_1st.common.exception.exception_class.business.ValidationException;
import com.example.autoever_1st.reservation.dto.req.ReservationReqDto;
import com.example.autoever_1st.reservation.dto.req.RoomTimeReqDto;
import com.example.autoever_1st.reservation.dto.res.ReservationStatusDto;
import com.example.autoever_1st.reservation.dto.res.ReservedTimeDto;
import com.example.autoever_1st.reservation.entities.Reservation;
import com.example.autoever_1st.reservation.repository.ReservationRepository;
import com.example.autoever_1st.reservation.service.ReservationService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReservationImpl implements ReservationService {

    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;

    public ReservationImpl(MemberRepository memberRepository, ReservationRepository reservationRepository) {
        this.memberRepository = memberRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public ReservationStatusDto getReservationStatus(Authentication authentication) {
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("회원 정보를 찾을 수 없습니다.", CustomStatus.NOT_HAVE_DATA));
        LocalDate today = LocalDate.now();

        List<Reservation> myReservations = reservationRepository.findByMemberAndReservationDate(member, today);
        // 나의 예약 정보
        List<ReservationStatusDto.MyReservationInfo> myReservationInfos = myReservations.stream()
                .map(r -> new ReservationStatusDto.MyReservationInfo(r.getRoomNum(), r.getStartTime()))
                .collect(Collectors.toList());

        List<Reservation> allTodayReservations = reservationRepository.findByReservationDate(today);
        // 방 번호별 예약 시간 개수
        Map<Integer, Long> reservationCountByRoom = allTodayReservations.stream()
                .collect(Collectors.groupingBy(Reservation::getRoomNum, Collectors.counting()));
        // 모든 시간이 예약된 방
        List<Integer> fullyBookedRooms = reservationCountByRoom.entrySet().stream()
                .filter(e -> e.getValue() >= 12)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return new ReservationStatusDto(myReservationInfos, fullyBookedRooms);
    }

    @Override
    public ReservedTimeDto getReservedTimesForRoom(RoomTimeReqDto roomTimeReqDto) {
        if (roomTimeReqDto.getRoomNum() < 1 || roomTimeReqDto.getRoomNum() > 7) {
            throw new ValidationException("존재하지 않는 방입니다.", CustomStatus.INVALID_INPUT);
        }
        LocalDate today = LocalDate.now();
        List<Reservation> reservations = reservationRepository.findByRoomNumAndReservationDate(roomTimeReqDto.getRoomNum(), today);

        List<Integer> reservedTimes = reservations.stream()
                .map(Reservation::getStartTime)
                .sorted()
                .collect(Collectors.toList());
        return new ReservedTimeDto(reservedTimes);
    }

    @Override
    public void makeReservation(ReservationReqDto reservationReqDto, Authentication authentication) {
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("회원 정보를 찾을 수 없습니다.", CustomStatus.NOT_HAVE_DATA));

        LocalDate today = LocalDate.now();
        // 이미 에약 2번 했는지 확인
        long todayCount = reservationRepository.countByMemberAndReservationDate(member, today);
        if (todayCount >= 2) {
            throw new ValidationException("하루 최대 2회까지 예약할 수 있습니다.", CustomStatus.INVALID_INPUT);
        }
        // 에약 시간 8시부터 20시까지
        if (reservationReqDto.getStartTime() < 8 || reservationReqDto.getStartTime() > 20) {
            throw new ValidationException("하루 최대 2회까지 예약할 수 있습니다.", CustomStatus.INVALID_INPUT);
        }
        // 중복 시간 예약 방지
        boolean alreadyReserved = reservationRepository.existsByRoomNumAndStartTimeAndReservationDate(
                reservationReqDto.getRoomNum(), reservationReqDto.getStartTime(), today);
        if (alreadyReserved) {
            throw new ValidationException("해당 시간은 이미 예약되었습니다.", CustomStatus.INVALID_INPUT);
        }

        Reservation reservation = Reservation.builder()
                .member(member)
                .roomNum(reservationReqDto.getRoomNum())
                .startTime(reservationReqDto.getStartTime())
                .headCount(reservationReqDto.getHeadCount())
                .reservationDesc(reservationReqDto.getReservationDesc())
                .reservationDate(today)
                .build();

        reservationRepository.save(reservation);
    }

    @Override
    public void cancelReservation(int roomNum, int startTime, Authentication authentication) {
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("회원 정보를 찾을 수 없습니다.", CustomStatus.NOT_HAVE_DATA));

        LocalDate today = LocalDate.now();

        Reservation reservation = reservationRepository.findByRoomNumAndStartTimeAndDateAndMember(roomNum, startTime, today, member.getId())
                .orElseThrow(() -> new DataNotFoundException("해당 예약을 찾을 수 없습니다.", CustomStatus.NOT_HAVE_DATA));

        reservationRepository.delete(reservation);
    }

}
