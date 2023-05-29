package com.emre.utility;

import com.emre.manager.IElasticServiceManager;
import com.emre.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Component olarak işaretlenen bu sınıf, Spring Context'i başlatıldığında çalışır.
 */
@Component
@RequiredArgsConstructor
public class Runner {
    private final UserProfileService userProfileService;
    private final IElasticServiceManager elasticServiceManager;

    /**
     * @PostConstructor olarak işaretlenen bu metod constructor olarak çalışmayı sağlayacak
     * yani sınıftan nesne oluşturulurken çalışacak.
     * Sonrada verileri elastic service e gönderecek.
     */
    //@PostConstruct
    public void init() {
        userProfileService.findAll().forEach(x -> {
            elasticServiceManager.save(x);
        });
    }
}
