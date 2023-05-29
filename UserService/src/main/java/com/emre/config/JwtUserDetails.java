package com.emre.config;

import com.emre.exception.ErrorType;
import com.emre.exception.UserException;
import com.emre.repository.IUserProfileRepository;
import com.emre.repository.entity.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtUserDetails implements UserDetailsService {
    private final IUserProfileRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
    public UserDetails loadUserByAuthId(Long authId) {
        /**
         * Öncelikle authid üzerinden kullanıcı profinin olup olmadını doğruluyoruz.
         */
        Optional<UserProfile> userProfile = repository.findOptionalByAuthid(authId);
        /**
         * Kullanıcıya ait yetki bilgilerini bir tabloda tutmak gereklidir. bu tablo genellikle ROLE tablosudur.
         * kullanıcı rollerinin tutulduğu yetkilerinin olduğu bir tablodur. bu tablodan bilgiler çekiilere
         * kullanıcıya ait yetkileri UserDetails nesnesine eklemek gereklidir.
         */
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        authorities.add(new SimpleGrantedAuthority("CANIM_ISTEDI_BIR_ROL_YAZDIM"));
        authorities.add(new SimpleGrantedAuthority("NE_OLA_KI"));
        authorities.add(new SimpleGrantedAuthority("BIR_TANE_DE_SENI_ICIN_YAZAYIM"));

        if (userProfile.isEmpty()) throw new UserException(ErrorType.USER_NOT_FOUND);
        UserDetails user = User.builder()
                .accountExpired(false) //hesap süresi dolmuş mu? bunu userprofile bilgisinin içinde tutuyor olmamız gerekiyor.
                                        //ayrı bir tabloda tutuyor olmamız gerekir.
                .accountLocked(false) // hesap kilitli mi? burada kişi hesabını bir şekilde kilitlemiş yada bizm tarafımızdan kapanmış olabilir.
                .username(userProfile.get().getUsername())
                .password("")
                .authorities(authorities) // kullanıcının sayfalarda yapacağı işlemlerin yetkileri. her endpoint üzerine hangi
                .build();
        return user;
    }
}
