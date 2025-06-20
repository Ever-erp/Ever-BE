package com.example.autoever_1st.domain.service.impl;

//public class ReservationImpl implements ReservationService {
//
//    private final MemberRepository memberRepository;
//
//    public ReservationImpl(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//
//    @Override
//    public ReservationStatusDto getReservationStatus(Authentication authentication) {
//        String email = authentication.getName();
//        Member member = memberRepository.findByEmail(email)
//                .orElseThrow(() -> new DataNotFoundException("회원 정보를 찾을 수 없습니다.", CustomStatus.NOT_HAVE_DATA));
//        LocalDate today = LocalDate.now();
//
//
//
//
//    }
//}
