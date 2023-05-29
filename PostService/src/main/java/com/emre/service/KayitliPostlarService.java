package com.emre.service;

import com.emre.repository.IKayitliPostlarRepository;
import com.emre.repository.entity.KayitliPostlar;
import com.emre.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class KayitliPostlarService extends ServiceManager<KayitliPostlar,String> {
    private final IKayitliPostlarRepository kayitliPostlarRepository;

    public KayitliPostlarService(IKayitliPostlarRepository kayitliPostlarRepository) {
        super(kayitliPostlarRepository);
        this.kayitliPostlarRepository = kayitliPostlarRepository;
    }
}
