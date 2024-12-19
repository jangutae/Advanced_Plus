package com.example.demo.reservation.controller;

import com.example.demo.item.entity.Item;
import com.example.demo.reservation.dto.ReservationRequestDto;
import com.example.demo.reservation.dto.ReservationResponseDto;
import com.example.demo.reservation.entity.ReservationStatus;
import com.example.demo.reservation.service.ReservationService;
import com.example.demo.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody ReservationRequestDto reservationRequestDto) {
        ReservationResponseDto reservation = reservationService.createReservation(reservationRequestDto);

        return ResponseEntity.ok().body(reservation);
    }

    @PatchMapping("/{userId}/update-status")
    public ResponseEntity<String> updateReservation(
            @PathVariable Long userId,
            @RequestBody ReservationStatus status
    ) {
        String reservationStatus = reservationService.updateReservationStatus(userId, status);

        return ResponseEntity.ok().body("예약 상태가 " + reservationStatus + " (으)로 변경되었습니다.");
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> findAll(
            @SessionAttribute(name = "id") User user,
            @SessionAttribute(name = "id") Item item
    ) {
        return ResponseEntity.ok().body(reservationService.getReservations(user.getId(), item.getId()));
    }

    @GetMapping("/search")
    public void searchAll(@RequestParam(required = false) Long userId,
                          @RequestParam(required = false) Long itemId) {
        reservationService.searchAndConvertReservations(userId, itemId);
    }
}
