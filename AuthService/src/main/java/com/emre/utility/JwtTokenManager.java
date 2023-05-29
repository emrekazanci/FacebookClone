package com.emre.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service //İhtiyaç duyulan yerlerde çağırılır, bean üretir. Service'dir.
public class JwtTokenManager {
    /**
     * JWT token üretiminde kullanılacak olan kriptolama algoritmalarının kullanacağın anahtar bilgisini
     * burada veriyoruz.
     * !!! DİKKAT !!!
     * Bu anahtar bilgsinin açık bir şekilde kod içinde var olması bir güvenlik açığıdır. Anahtarın
     * kod içinden alınarak ENV şeklinde başka bir repo üzerinden erişilebilir olması daha güvenli olacaktır.
     */
//    @Value("${my-application.jwt.secret-key-env}")
  //  private String secretKey;
    private final String sifreAnahtari = "sT*&r4b1EDra45fEXO7A#rAZaJ$Y-CrA2#L=9lGox!*R@=ReSpUsWOk6T-qeflmi";

    private Long exDate = 1000L * 60 * 2; //2 dk

    /**
     * JWT üretimi yapılırken belli bilgilerin payload içine eklenmesi gereklidir.
     * Yani token verdiğimiz kullanıcıya ait kimlik bilgilerini doğrulayabilecek
     * bilgiler olabilir. Ama dikkat etmeliyiz ki burda bulunan bilgiler açık bir şekilde
     * iletilmektedir. BU nedenle Claim nesnelerinin içine eklenecek bilgilerin özel gizli
     * bilgiler olmamasına dikkat etmeliyiz.
     * Erişim kısıtları ve sayfa geçişleri için belli parametrelerin token içerisinde olması
     * ve eşleştirilmesi gereklidir.
     * @param id
     * @return
     */
    public Optional<String> createToken(Long id) {
        String token;
        try {
            token = JWT.create().withAudience()
                    .withClaim("id", id) //Payload dataları
                    .withClaim("howtopage", "AuthMicroService") //Payload dataları
                    .withClaim("isOne", true) //Payload dataları
                    .withIssuer("Java7") //token üreten uygulama
                    .withIssuedAt(new Date()) //token oluşturma tarihi
                    .withExpiresAt(new Date(System.currentTimeMillis() + exDate)) //token in sona erme zamanı
                    .sign(Algorithm.HMAC512(sifreAnahtari)); //token imzalama algoritması
            return Optional.of(token);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    /**
     * İlk olarak bir JWT token bilgisinin bize ait olup olmadığının, kullanım süresinin dolup dolmadığının kontrol
     * edilmesi gerekiyor. Token içinde önceden bizim tarafımızdan eklenen bilgilerin token Claim nesneleri
     * içinde alınması gereklidir.
     * @param token
     * @return
     */
    public Optional<Long> getIdFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(sifreAnahtari);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("Java7")
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if (decodedJWT == null) return Optional.empty();
            return Optional.of(decodedJWT.getClaim("id").asLong());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
