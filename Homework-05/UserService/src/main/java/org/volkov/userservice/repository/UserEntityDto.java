package org.volkov.userservice.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntityDto {
    private String name;

    private String email;

    private int age;

    private OffsetDateTime createdAt;
}
