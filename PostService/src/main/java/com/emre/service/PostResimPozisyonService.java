package com.emre.service;

import com.emre.repository.IPostResimPozisyonRepository;
import com.emre.repository.entity.PostResimPozisyon;
import com.emre.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class PostResimPozisyonService extends ServiceManager<PostResimPozisyon,String> {
    private final IPostResimPozisyonRepository postResimPozisyonRepository;

    public PostResimPozisyonService(IPostResimPozisyonRepository postResimPozisyonRepository) {
        super(postResimPozisyonRepository);
        this.postResimPozisyonRepository = postResimPozisyonRepository;
    }
}
