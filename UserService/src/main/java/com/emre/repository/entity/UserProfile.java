package com.emre.repository.entity;

import lombok.*;

import org.springframework.data.annotation.Id;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class UserProfile {
    @Id
    String id; //Mongo Db de string olmalı. id leri string olarak üretiyor.
    Long authid;
    String username;
    String email;
    String name;
    String surname;
    String phone;
    String address;
    String avatar;
    EGender gender;
}
