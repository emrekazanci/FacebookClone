package com.emre.config.security;

import com.emre.config.JwtUserDetails;
import com.emre.exception.ErrorType;
import com.emre.exception.UserException;
import com.emre.utility.JwtTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenManager jwtTokenManager;
    @Autowired
    private JwtUserDetails jwtUserDetails;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        /**
         * Burası geln tüm isteklerin yönlendirildiği yerdir. Burada Header içinde Authorization olup olmadığını kontrol ediyoruz.
         * Eğer varsa token içindeki bilgileri alıp işlem yapıyoruz.
         */
        final String authHeader = request.getHeader("Authorization");
        /**
         * Bearer token olup olmadığının kontrolünü yapıyoruz.
         */
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            /**
             * Bearer token içinden jwt bilgisini ayırıyoruz.
             */
            final String token = authHeader.substring(7);
            /**
             * Token bilgisinin geçerliliğini kontrol ediyoruz.
             */
            Optional<Long> authId = jwtTokenManager.getIdFromToken(token);
            if (authId.isPresent()) {
                /**
                 * DİKKAT!! tüm oturum açma işlemleri burada yapılacak ve spring için özel bir
                 * kullanıcı tanımı oluşturacağız.
                 */
                UserDetails userDetails = jwtUserDetails.loadUserByAuthId(authId.get());
                /**
                 * Araya girme işleminde kimlik doğrulama yöntemi olarak 'UsernamePasswordAuthenticationFilter' sınıfı için
                 * gerekli olan kimlik bilgilerini oluşturuyoruz. bu kimlik bilgileri ile ihtiyacı olan tüm parametreleri içeren
                 * UsernamePasswordAuthenticationToken nesnesi yaratarak bu nesneyi context içine auth bilgisi olarak geçiyoruz.
                 */
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                /**
                 * Geçerli context içine kimlik bilglerini ekliyoruz. böylece spring security nin yönetebilecei bir
                 * kimlik yönetimi sağlanmış oluyor.
                 */
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                /**
                 * Token geçersiz olduğu için token hatası fırlatıyoruz.
                 */
                throw new UserException(ErrorType.ERROR_INVALID_TOKEN);
            }
        }
        filterChain.doFilter(request,response);
    }
}
