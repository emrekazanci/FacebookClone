package com.emre.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ErrorType {
    USER_NOT_FOUND(2004,"Kullanıcı bulunamadı", HttpStatus.NOT_FOUND),
    NAME_SURNAME_NULL(2005,"İsim ve Soyisim boş bırakılamaz.",HttpStatus.NOT_FOUND),
    BAD_REQUEST(4000,"Geçersiz istek yada parametre",HttpStatus.BAD_REQUEST),
    ERROR(9000,"Beklenmeyen bir hata oluştu. Lütfen tekrar deneyin",HttpStatus.INTERNAL_SERVER_ERROR),
    ERROR_INVALID_TOKEN(3000,"Geçersiz token bilgisi",HttpStatus.UNAUTHORIZED),
    ERROR_ACCESS_DENIED(3001,"Yetkisiz erişim. Lütfen ge.erli bir kullanucu ile tekrar deneyin.",HttpStatus.UNAUTHORIZED);

    int code;
    String message;
    HttpStatus httpStatus;
}
