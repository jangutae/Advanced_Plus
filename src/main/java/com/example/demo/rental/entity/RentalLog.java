package com.example.demo.rental.entity;

import com.example.demo.reservation.entity.Reservation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RentalLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String logMessage;

    @Column
    @Enumerated(EnumType.STRING)
    private LogType logType; // SUCCESS, FAILURE -> enum

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    public RentalLog(Reservation reservation, String logMessage, LogType logType) {
        this.reservation = reservation;
        this.logMessage = logMessage;
        this.logType = logType;
    }
}
