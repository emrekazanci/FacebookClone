package com.emre.dto.request;

import com.emre.repository.entity.EGender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileUpdateRequestDto {
    @NotEmpty
    @Size(min = 5)
    String token;
    String name;
    String surname;
    String avatar;
    String address;
    String phone;
    EGender gender;
}
