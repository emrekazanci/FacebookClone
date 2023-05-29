package com.emre.service;

import com.emre.repository.IPostResimRepository;
import com.emre.repository.entity.PostResim;
import com.emre.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostResimService extends ServiceManager<PostResim, String> {
    private final IPostResimRepository postResimRepository;

    public PostResimService(IPostResimRepository postResimRepository) {
        super(postResimRepository);
        this.postResimRepository = postResimRepository;
    }

    public List<String> getUrlsByPostId(String postId) {
        /**
         * Burada resimlerin listesi string olarak dönmüyor. entity olarak datalar dönüyor.
         * BU nedenle buradaki bilgileri bir string listesi haline çevirmemiz gerekiyor.
         */
        List<PostResim> postResimList = postResimRepository.findAllByPostid(postId);
        List<String> urlList = new ArrayList<>();
        /**
         * ilgili pos'a ait resim listesi entity sini foreach ile dönerek oluşturduğum listeye
         * resimlerin url'lerini tek tek ekledim.
         */
        postResimList.forEach(x -> {
            urlList.add(x.getUrl());
        });
        return urlList;
    }
}
