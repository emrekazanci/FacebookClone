package com.emre.manager;

import com.emre.dto.request.SaveUserProfileRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * Hangi service ile iletişime geçileceğini burda yazarız.
 */
@FeignClient(name = "user-profile-service",
        url = "http://localhost:9093/userprofile"
)
public interface IUserProfileManager {
    @PostMapping("/save")
    ResponseEntity<Void> save(@RequestBody @Valid SaveUserProfileRequestDto dto);
}
