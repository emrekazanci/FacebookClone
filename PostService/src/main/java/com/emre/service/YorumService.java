package com.emre.service;

import com.emre.repository.IYorumRepository;
import com.emre.repository.entity.Yorum;
import com.emre.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class YorumService extends ServiceManager<Yorum, String> {
    private final IYorumRepository yorumRepository;

    public YorumService(IYorumRepository yorumRepository) {
        super(yorumRepository);
        this.yorumRepository = yorumRepository;
    }
}
