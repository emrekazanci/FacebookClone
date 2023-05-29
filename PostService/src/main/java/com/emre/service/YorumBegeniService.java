package com.emre.service;

import com.emre.repository.IYorumBegeniRepository;
import com.emre.repository.entity.YorumBegeni;
import com.emre.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class YorumBegeniService extends ServiceManager<YorumBegeni,String> {
    private final IYorumBegeniRepository yorumBegeniRepository;

    public YorumBegeniService(IYorumBegeniRepository yorumBegeniRepository) {
        super(yorumBegeniRepository);
        this.yorumBegeniRepository = yorumBegeniRepository;
    }
}
