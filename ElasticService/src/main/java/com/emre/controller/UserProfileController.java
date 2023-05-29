package com.emre.controller;

import com.emre.dto.request.UserProfileDto;
import com.emre.repository.entity.UserProfile;
import com.emre.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.emre.constants.EndPointList.*;

@RestController
@RequestMapping("/elastic/userprofile")
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;

    /*
    Veriyi diğer taraftan istersek UserProfile şeklinde alabiliriz. İstersek de dto şeklinde alabiliriz.
     */
    @PostMapping(SAVE)
    public ResponseEntity<Void> save(@RequestBody UserProfileDto dto) {
        userProfileService.save(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(FIND_ALL)
    public ResponseEntity<Iterable<UserProfile>> findAll() {
        return ResponseEntity.ok(userProfileService.findAll());
    }

    @GetMapping("findall_page")
    public ResponseEntity<Page<UserProfile>> findAll(int currentPage, int size, String sortParameter, String sortDirection) {
        return ResponseEntity.ok(userProfileService.findAll(currentPage, size, sortParameter, sortDirection));
    }
}
