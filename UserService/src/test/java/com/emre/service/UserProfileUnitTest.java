package com.emre.service;

import com.emre.dto.request.GetMyProfileRequestDto;
import com.emre.dto.response.GetMyProfileResponseDto;
import com.emre.repository.IUserProfileRepository;
import com.emre.repository.entity.UserProfile;
import com.emre.utility.JwtTokenManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserProfileUnitTest {

    @InjectMocks
    UserProfileService userProfileService;
    @Mock
    IUserProfileRepository userProfileRepository;
    @Mock
    JwtTokenManager jwtTokenManager;

    @Test
    void getMyProfileTest(){
        when(jwtTokenManager.getIdFromToken(any())).thenReturn(Optional.of(9l));
        when(userProfileRepository.findOptionalByAuthid(any())).thenReturn(Optional.of(
                UserProfile.builder()
                        .phone("0 555 666")
                        .avatar("")
                        .name("Emre")
                        .surname("Öğrenci")
                        .username("emre")
                        .build()
        ));
        GetMyProfileResponseDto responseDto = userProfileService.getMyProfile(GetMyProfileRequestDto.builder()
                        .token("234234")
                .build());
        Assertions.assertTrue(responseDto != null);
    }
}
