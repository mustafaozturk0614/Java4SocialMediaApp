package com.bilgeadam.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ErrorType {


    INTERNAL_ERROR(5100,"Sunucu Hatas�", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(4100,"Parametre Hatas�",HttpStatus.BAD_REQUEST),
    USERNAME_DUPLICATE(4110,"Kullan�c� ad� zaten var",HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4111,"Boyle Bir kullan�c� Bulunamad�",HttpStatus.BAD_REQUEST),

    LOGIN_ERROR(4112,"Kullan�c� ad� veya �ifre Hatal�",HttpStatus.BAD_REQUEST);

    private int code;
    private String message;
    HttpStatus httpStatus;
}
