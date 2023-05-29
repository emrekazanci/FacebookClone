package com.emre.controller;

import com.emre.dto.request.GetMyProfileRequestDto;
import com.emre.dto.request.SaveUserProfileRequestDto;
import com.emre.dto.request.UserProfileUpdateRequestDto;
import com.emre.dto.response.GetMyProfileResponseDto;
import com.emre.repository.entity.UserProfile;
import com.emre.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/userprofile")
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;

    @GetMapping("/getpage")
    public ResponseEntity<String> getPage() {
        return ResponseEntity.ok("User Service Ulaştınız.");
    }

    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody @Valid SaveUserProfileRequestDto dto) {
        userProfileService.save(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update")
    public ResponseEntity<Void> update(UserProfileUpdateRequestDto dto) {
        userProfileService.update(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findall")
    @PreAuthorize("hasAuthority('NE_OLA_KI') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<UserProfile>> findAll() {
        return ResponseEntity.ok(userProfileService.findAll());
    }

    @PostMapping("/getmyprofile")
    @CrossOrigin("*")
    public ResponseEntity<GetMyProfileResponseDto> getMyProfile(@RequestBody @Valid GetMyProfileRequestDto dto){
        return ResponseEntity.ok(userProfileService.getMyProfile(dto));
    }
    @PostMapping("/getotherprofile")
    @CrossOrigin("*")
    public ResponseEntity<UserProfile> getOtherProfile(@RequestBody @Valid GetMyProfileRequestDto dto){
        return ResponseEntity.ok(userProfileService.getOtherProfile(dto));
    }

    @GetMapping("/getnameetoupper")
    public ResponseEntity<String> getNameToUpper(String name) {
        return ResponseEntity.ok(userProfileService.getNameToUpper(name));
    }

    @GetMapping("/clearcache")
    public ResponseEntity<Void> clearCache() {
        userProfileService.clearCacheToUpper();
        return ResponseEntity.ok().build();
    }
}
