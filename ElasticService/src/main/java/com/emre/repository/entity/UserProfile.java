package com.emre.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "userprofile")
public class UserProfile {
    @Id
    String id; //Bu elastic 'in kendi id si olmuş olacak.
    String userid; //user service'indeki user'ın id'sine eşit olacak
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
