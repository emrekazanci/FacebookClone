package com.emre.service;

import com.emre.dto.request.GetMyProfileRequestDto;
import com.emre.dto.request.SaveUserProfileRequestDto;
import com.emre.dto.response.GetMyProfileResponseDto;
import com.emre.exception.ErrorType;
import com.emre.repository.entity.UserProfile;
import com.emre.utility.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

/**
 * Standart testlere nazaran spring test yaparken bazı anotasyonları kullanmak zorundayız.
 *
 * @SpringBootTest
 * @ActiveProfile("test")
 */
@SpringBootTest
public class UserProfileServiceTest {
    /**
     * Test yaparken dikkat edilecekler.
     * 1- her bie sınıf için ayrı bir test sınıfı oluşturmalısınız.
     * 2- tüm metodları test etmelisiniz.
     * 2.1- ÖNemli, bir metodun tüm ihtimalleri için ayrı ayrı test case leri yazılmalı.
     * 3- test edilecekk sınıfların ve metodların dış bileşenlerden ayrılarak soyutlanmış testlerinin yapılması
     * önemlidir. böylece sadece yazdığınız kodun testini yaparsınız.
     */
    @Autowired
    UserProfileService userProfileService;
    @Autowired
    JwtTokenManager jwtTokenManager;

    @Test
    void testDeneme() {
        userProfileService.findAll().forEach(System.out::println);
    }

    @Test
    void saveTest() {
        userProfileService.save(SaveUserProfileRequestDto.builder()
                .authid(4L)
                .email("emre@gmail.com")
                .username("emre")
                .build());
        Optional<UserProfile> user = userProfileService.findAll().stream().filter(x -> x.getUsername().equals("emre")).findFirst();
        Assertions.assertTrue(user.isPresent());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/userProfile.csv")
    void saveCSVTest(Long authid,String email,String username) {
        UserProfile userProfile = UserProfile.builder()
                .email(email)
                .username(username)
                .authid(authid)
                .build();
        UserProfile userProfileSave = userProfileService.save(userProfile);
        Assertions.assertTrue(userProfileSave.getId() != null);
    }


    @Test
    void getMyProfileInvalidTokenTest() {
        Exception exception = Assertions.assertThrows(
                Exception.class, () -> userProfileService.getMyProfile(GetMyProfileRequestDto.builder()
                        .token("2123323523")
                        .build())
        );
        Assertions.assertEquals(ErrorType.ERROR_INVALID_TOKEN.getMessage(), exception.getMessage());
    }

    @Test
    void getMyProfileInvalidUserTest() {
        Optional<String> token = jwtTokenManager.createToken(2000L);
        Exception exception = Assertions.assertThrows(
                Exception.class, () -> userProfileService.getMyProfile(GetMyProfileRequestDto.builder()
                        .token(token.get())
                        .build())
        );
        Assertions.assertEquals(ErrorType.USER_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    void getMyProfileTest() {
        Optional<String> token = jwtTokenManager.createToken(1L);
        GetMyProfileResponseDto responseDto = userProfileService.getMyProfile(GetMyProfileRequestDto.builder()
                .token(token.get())
                .build());
    }
}
