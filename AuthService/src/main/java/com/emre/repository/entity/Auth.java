package com.emre.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tblauth")
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String username;
    String password;
    String email;
    /**
     * Kullanıcının kayıt edilme tarihini tutan değer. Long olarak tutulur.
     */
    Long createat;
    /**
     * 0- Kullanıcı kayıt edilmiş ama onaylanmamış
     * 1- Kullanıcı kayıt edilmiş ve onaylanmış
     * 2- Kullanıcı kayıt edilmiş ve onaylanmış fakat hesabı kilitlenmiş
     * 3- Kullanıcı kayıt edilmiş ve onaylanmış fakat hesabı silinmiş
     */
    int status;

//    /**
//     * Kullanıcının durumu yukarıdaki değiken ile tutulabileceği gibi
//     * bir enum şeklinde de tutulabilir. Aşağıda tanımlanan enum içinde,
//     * Aktif,pasif,silinmiş tutumları tutabiliriz.
//     */
//    @Enumerated(EnumType.STRING)
//    EStatus status;


}
