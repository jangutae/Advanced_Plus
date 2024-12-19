package com.example.demo.reservation.entity;

import com.example.demo.item.entity.Item;
import com.example.demo.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "start_at")
    private LocalDateTime startAt;

    @Column(name = "end_at")
    private LocalDateTime endAt;

    @Setter
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;   // PENDING, APPROVED, CANCELED, EXPIRED

    public Reservation(Item item, User user, LocalDateTime startAt, LocalDateTime endAt, ReservationStatus status) {
        this.item = item;
        this.user = user;
        this.startAt = startAt;
        this.endAt = endAt;
        this.status = status;
    }

    public Reservation() {}

    public void updateStatus(ReservationStatus status) {

        this.status = status;
    }

    public void validatedApprovedStatus(Reservation reservation) {
        if (ReservationStatus.PENDING.equals(reservation.getStatus())) {
            throw new IllegalArgumentException("PENDING 상태만 APPROVED로 변경 가능합니다.");
        }
    }

    public void validatedCanceledStatus(Reservation reservation) {
        if (ReservationStatus.PENDING.equals(reservation.getStatus())) {
            throw new IllegalArgumentException("EXPIRED 상태인 예약은 취소할 수 없습니다.");
        }
    }

    public void validatedExpiredStatus(Reservation reservation) {
        if (ReservationStatus.PENDING.equals(reservation.getStatus())) {
            throw new IllegalArgumentException("PENDING 상태만 EXPIRED로 변경 가능합니다.");
        }
    }

    public boolean isDuplicateStatus(Reservation reservation, ReservationStatus status) {
        return reservation.getStatus().equals(status);
    }
}
