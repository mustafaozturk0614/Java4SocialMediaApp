package com.bilgeadam.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ErrorType {


    INTERNAL_ERROR(5100,"Sunucu Hatas?", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(4100,"Parametre Hatas?",HttpStatus.BAD_REQUEST),
    USERNAME_DUPLICATE(4110,"Kullan?c? ad? zaten var",HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4111,"Boyle Bir kullan?c? Bulunamad?",HttpStatus.BAD_REQUEST),

    LOGIN_ERROR(4112,"Kullan?c? ad? veya ?ifre Hatal?",HttpStatus.BAD_REQUEST),

    STATUS_NOT_FOUND(4113,"Boyle bir kullanici durumu bulunmamaktadir",HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND(4114,"Boyle bir kullanici rolu bulunmamaktadir",HttpStatus.BAD_REQUEST),
    LOGIN_STATUS_ERROR(4115,"Yetkisiz kullan?c? giri?i lütfen hesab?n?z? aktif ediniz",HttpStatus.BAD_REQUEST)
    ;

    private int code;
    private String message;
    HttpStatus httpStatus;
}
