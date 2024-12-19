package com.example.demo.reservation.service;


import com.example.demo.item.entity.Item;
import com.example.demo.item.repository.ItemRepository;
import com.example.demo.rental.entity.LogType;
import com.example.demo.rental.entity.RentalLog;
import com.example.demo.rental.service.RentalLogService;
import com.example.demo.reservation.dto.ReservationRequestDto;
import com.example.demo.reservation.dto.ReservationResponseDto;
import com.example.demo.reservation.entity.Reservation;
import com.example.demo.reservation.entity.ReservationStatus;
import com.example.demo.reservation.repository.ReservationRepository;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final RentalLogService rentalLogService;

    // TODO: 1. 트랜잭션 이해 @Transactional 어노테이션으로 한 트렌잭션안에서 하나의 로직이 실패할 경우 ROLLBACK
    @Transactional
    public ReservationResponseDto createReservation(ReservationRequestDto requestDto) {
        // 쉽게 데이터를 생성하려면 아래 유효성검사 주석 처리
//        List<Reservation> haveReservations = reservationRepository.findConflictingReservations(itemId, startAt, endAt);
//        if(!haveReservations.isEmpty()) {
//            throw new ReservationConflictException("해당 물건은 이미 그 시간에 예약이 있습니다.");
//        }

        Item item = itemRepository.findItemByIdOrElseThrow(requestDto.getItemId());
        User user = userRepository.findUserByIdOrElseThrow(requestDto.getUserId());
        Reservation reservation = new Reservation(item, user, requestDto.getStartAt(), requestDto.getEndAt(), ReservationStatus.PENDING);
        Reservation savedReservation = reservationRepository.save(reservation);

        RentalLog rentalLog = new RentalLog(savedReservation, "로그 메세지", LogType.SUCCESS);
        rentalLogService.save(rentalLog);

        return ReservationResponseDto.toDto(savedReservation);
    }

    // TODO: 3. N+1 문제  ReservationRepository 에 JOIN FETCH를 활용하여 N+1 문제 해결
        public List<ReservationResponseDto> getReservations(Long userId, Long itemId) {
        List<Reservation> reservations = reservationRepository.findByUserIdAndItemId(userId, itemId);

        return reservations.stream().map(ReservationResponseDto::toDto).toList();
    }

    // TODO: 5. QueryDSL 검색 개선 pass
    public List<ReservationResponseDto> searchAndConvertReservations(Long userId, Long itemId) {

        List<Reservation> reservations = searchReservations(userId, itemId);

        return convertToDto(reservations);
    }

    public List<Reservation> searchReservations(Long userId, Long itemId) {

        if (userId != null && itemId != null) {
            return reservationRepository.findByUserIdAndItemId(userId, itemId);
        } else if (userId != null) {
            return reservationRepository.findByUserId(userId);
        } else if (itemId != null) {
            return reservationRepository.findByItemId(itemId);
        } else {
            return reservationRepository.findAll();
        }
    }

    private List<ReservationResponseDto> convertToDto(List<Reservation> reservations) {
        return reservations.stream()
                .map(reservation -> new ReservationResponseDto(
                        reservation.getId(),
                        reservation.getUser().getNickname(),
                        reservation.getItem().getName(),
                        reservation.getStartAt(),
                        reservation.getEndAt()
                ))
                .toList();
    }

    // TODO: 7. 리팩토링
    @Transactional
    public String updateReservationStatus(Long reservationId, ReservationStatus status) {
        Reservation reservation = reservationRepository.findReservationByIdOrElseThrow(reservationId);

        if (reservation.isDuplicateStatus(reservation, status)) {
            throw new IllegalArgumentException("동일한 값으로 변경할 수 없습니다.");
        }

        switch (status) {
            case APPROVED :
                reservation.validatedApprovedStatus(reservation);
                reservation.updateStatus(ReservationStatus.APPROVED);
                break;
            case CANCELED:
                reservation.validatedCanceledStatus(reservation);
                reservation.updateStatus(ReservationStatus.CANCELED);
                break;
            case EXPIRED:
                reservation.validatedExpiredStatus(reservation);
                reservation.updateStatus(ReservationStatus.EXPIRED);
                break;
        }

        return reservation.getStatus().toString();

//        if (ReservationStatus.APPROVED.equals(status)) {
//            if (ReservationStatus.PENDING.equals(reservation.getStatus())) {
//                throw new IllegalArgumentException("PENDING 상태만 APPROVED로 변경 가능합니다.");
//            }
//            reservation.updateStatus(ReservationStatus.APPROVED);
//        } else if (ReservationStatus.CANCELED.equals(status)) {
//            if (ReservationStatus.EXPIRED.equals(reservation.getStatus())) {
//                throw new IllegalArgumentException("EXPIRED 상태인 예약은 취소할 수 없습니다.");
//            }
//            reservation.updateStatus(ReservationStatus.CANCELED);
//        } else if (ReservationStatus.EXPIRED.equals(status)) {
//            if (!ReservationStatus.PENDING.equals(reservation.getStatus())) {
//                throw new IllegalArgumentException("PENDING 상태만 EXPIRED로 변경 가능합니다.");
//            }
//            reservation.updateStatus(ReservationStatus.EXPIRED);
//        } else {
//            throw new IllegalArgumentException("올바르지 않은 상태: " + status);
//        }
    }
}
