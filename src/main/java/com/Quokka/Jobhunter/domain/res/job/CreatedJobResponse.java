package com.Quokka.Jobhunter.domain.res.job;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.Instant;
import java.util.List;

import com.Quokka.Jobhunter.util.constant.LevelEnum;

@Getter
@Setter
public class CreatedJobResponse {
    private Long id;
    private String name;
    private String location;
    private double salary;
    private int quantity;
    private LevelEnum level;
    private boolean isActive;
    private String description;
    private Instant startDate;
    private Instant endDate;
    private String createdBy;
    private Instant createdDate;
    private List<String> skills;
}
