package com.Quokka.Jobhunter.domain.dto;

import java.time.Instant;

import com.Quokka.Jobhunter.util.constant.GenderEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResCreateUserDTO {
    private long id;
    private String name;
    private String email;
    private int age;
    private GenderEnum gender;
    private Instant createAt;
    private String address;
}
