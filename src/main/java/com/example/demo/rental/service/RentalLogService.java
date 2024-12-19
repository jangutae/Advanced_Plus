package com.example.demo.rental.service;

import com.example.demo.rental.entity.RentalLog;
import com.example.demo.rental.repository.RentalLogRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RentalLogService {

    private final RentalLogRepository rentalLogRepository;

    @Transactional
    public void save(RentalLog rentalLog) {
        rentalLogRepository.save(rentalLog);
//        if (rentalLog != null) {
//            throw new RuntimeException();
//        }
    }
}
