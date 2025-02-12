package com.Quokka.Jobhunter.domain.dto;

import java.time.Instant;
import com.Quokka.Jobhunter.util.constant.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResUserDTO {
    private long id;
    private String email;
    private String name;
    private GenderEnum gender;
    private String address;
    private int age;
    private Instant updateAt;
    private Instant createAt;
}
