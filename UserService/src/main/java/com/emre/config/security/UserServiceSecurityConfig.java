package com.emre.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Rest isteklerinin tümünde yetki kontrolünün aktif edilmesi için
 * EnableGlobalMethodSecurity(prePostEnabled = true) anotasyonunun eklenmesi gerekir.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // gelen bütün metotlarda authority nin geçerli olması için
public class UserServiceSecurityConfig {

    @Bean
    JwtTokenFilter getJwtTokenFilter(){
        return new JwtTokenFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /**
         * Bu kısımda gelen isteklerin filtrelemesi ve belli adreslere gelen isteklerin,
         * hangilerinin public olacağını hangilerininde kısıtlanacağını belirliyoruz.
         * 1. gelen istek -> HttpSecurity
         * 2. kısıtlanacak adreslerin listesi -> antMatchers
         * 2.1. "/api/v1/admin/login" -> admin giriş sayfasını tanımladık
         * 2.2. "/api/v1/user/**" -> kulanıcı isteklerinin tamamını tanımladık.
         * 3. izin verme işlemi -> permitAll()
         * 4. tüm istekleri belirtmek -> anyRequest()
         * 5. authenticated -> gelen isteklerin doğrulanması
         */
        http.authorizeRequests()
                .antMatchers("/api/v1/userprofile/getpage","/login.html",
                        "/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html")
                .permitAll()
                .anyRequest().authenticated();
        /**
         * Eğer oturum açılmamış ve yetki yoksa login page yönlendirilmesini yapmak gerekiyor.
         */
        //http.formLogin();
        /**
         * Form sayfası özelleştirilebilir,kendi form sayfamızı kullanmak istiyorsak.
         */
        //http.formLogin().loginPage("login.html");

        /**
         * Burada gelen isteğin filtre mekanizmasına uğramadan önce JWT ile kontrol edilmesi ve yetkili bir kullanıcıysa
         * kendisine özel bir kimlik kartı tanımlanmasını sağlıyoruz.
         * 1. İlk yapmamız gerekn şey, filtreleme işleminden önce yapılması planlanan kodlamanın
         * ne olduğunu biliyor ve yönlendiriyoruz.
         * 1.1. ilk kısım filtreleme işlemini yaparak jwt kontrolünü sağlayacak olan sınıfı tanımlıyoruz.
         * 1.2. filtreleme işleminden kontrol edilecek sınıf bileşenini veriyoruz.
         */
        http.addFilterBefore(getJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
}
