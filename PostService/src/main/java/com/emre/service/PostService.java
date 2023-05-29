package com.emre.service;

import com.emre.dto.request.GetMyProfileRequestDto;
import com.emre.dto.request.GetPostRequestDto;
import com.emre.dto.response.GetPostResponseDto;
import com.emre.manager.IUserProfileManager;
import com.emre.repository.IPostRepository;
import com.emre.repository.entity.Post;
import com.emre.repository.entity.UserProfile;
import com.emre.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostService  extends ServiceManager<Post,String> {
    private final IPostRepository repository;
    private final PostResimService postResimService;
    private final IUserProfileManager userProfileManager;
    public PostService(IPostRepository repository,
                       PostResimService postResimService,
                       IUserProfileManager userProfileManager) {
        super(repository);
        this.repository=repository;
        this.postResimService=postResimService;
        this.userProfileManager = userProfileManager;
    }

    public List<GetPostResponseDto> getPosts(GetPostRequestDto dto) {
        /**
         * öncelikle kullanıcıya dönülecek olan listenin boş halini oluşturuyoruz.
         */
        List<GetPostResponseDto> result = new ArrayList<>();
        /**
         * Tüm Postların listesini çekiyoruz.
         */
        List<Post> postList = repository.findAll();
        postList.forEach(post->{
            List<String> posturls = postResimService.getUrlsByPostId(post.getId());
            UserProfile userProfile = userProfileManager
                    .getOtherProfile(GetMyProfileRequestDto.builder()
                            .token(dto.getToken())
                            .userid(post.getUserid())
                            .build()).getBody();
            /**
             * Kullanıcıya dönülecek response dto oluşturuluyor.
             */
            GetPostResponseDto getPDto = GetPostResponseDto.builder()
                    .likecount(post.getBegenisayisi())
                    .username(userProfile.getUsername())
                    .useravatar(userProfile.getAvatar())
                    .sharedtime(new Date(post.getPaylasimzamani())+"")
                    .posttext(post.getAciklama())
                    .posturls(posturls)
                    .build();
            result.add(getPDto);
        });
        return result;
    }
}