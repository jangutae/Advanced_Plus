package com.example.demo.user.dto;

import com.example.demo.user.entity.UserStatus;
import lombok.Getter;

import java.util.List;

@Getter
public class ReportRequestDto {
    private List<Long> userIds;


    public ReportRequestDto() {
    }
}
