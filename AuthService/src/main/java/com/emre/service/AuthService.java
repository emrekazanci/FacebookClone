package com.emre.service;

import com.emre.dto.request.LoginRequestDto;
import com.emre.dto.request.RegisterRequestDto;
import com.emre.dto.request.SaveUserProfileRequestDto;
import com.emre.exception.AuthException;
import com.emre.exception.ErrorType;
import com.emre.manager.IUserProfileManager;
import com.emre.mapper.IAuthMapper;
import com.emre.rabbitmq.model.CreateUserModel;
import com.emre.rabbitmq.producer.CreateUserProducer;
import com.emre.repository.IAuthRepository;
import com.emre.repository.entity.Auth;
import com.emre.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth, Long> {
    private final IAuthRepository authRepository;
    private final IUserProfileManager userProfileManager;
    private final CreateUserProducer createUserProducer;

    public AuthService(IAuthRepository authRepository,
                       IUserProfileManager userProfileManager,
                       CreateUserProducer createUserProducer) {
        super(authRepository);
        this.authRepository = authRepository;
        this.userProfileManager = userProfileManager;
        this.createUserProducer = createUserProducer;
    }

    public Optional<Auth> doLogin(LoginRequestDto dto) {
        return authRepository.findOptionalByUsernameAndPassword(dto.getUsername(), dto.getPassword());
    }

    public void register(RegisterRequestDto dto) {
        if (authRepository.existsByUsername(dto.getUsername()))
            throw new AuthException(ErrorType.ERROR_USERNAME);
        /**
         * save metodu kayıt işleminden sonra bize auth nesnesini dönmektedir.
         */
        Auth auth = save(IAuthMapper.INSTANCE.toAuth(dto));
        /**
         * Bir kullanıcı uygulamamızda üyelik açtıktan sonra bu kullanıcıya ait bilgiler ile
         * userprofile bilgisininde oluşturulması gerekiyor. Bunu sağlamak için UserProfile servisine
         * istek atmak üzere FeignClient kullanıyoruz.
         * Kaydetme işlemi için, manager bizden DTO istemektedir. Bu nedenle auth için yapılan kayıt bilgilerini
         * dto nun içine koyarakk istek atıyoruz.
         */
        SaveUserProfileRequestDto requestDto = SaveUserProfileRequestDto.builder()
                .username(auth.getUsername())
                .email(auth.getEmail())
                .authid(auth.getId())
                .build();
        /**
         * Bu kısımda, DTO içindeki alanları gerekl olan datalar girilir. FeignClient bizim için
         * veridğimiz parametreleri iletişime geçeceğimiz UserProfile servisinin save metoduna
         * jsonObject olarak gönderir ve böylece o save metodunun çalışmasını sağlar.
         */
        //userProfileManager.save(requestDto); //Feign Client olan kısmı için.
        createUserProducer.convertAndSendData(CreateUserModel.builder()
                .authid(auth.getId())
                .email(auth.getEmail())
                .username(auth.getUsername())
                .build());
    }
}
