package com.emre.service;

import com.emre.repository.IBegeniRepository;
import com.emre.repository.entity.Begeni;
import com.emre.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class BegeniService extends ServiceManager<Begeni, String> {
    private final IBegeniRepository begeniRepository;

    public BegeniService(IBegeniRepository begeniRepository) {
        super(begeniRepository);
        this.begeniRepository = begeniRepository;
    }
}
