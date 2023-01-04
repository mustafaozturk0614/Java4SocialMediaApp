package com.bilgeadam.repository.entity;
/*
1- username,pasword email activatedcode
 Role ,Status -> Enum olacak
 bunlarla birlikte entityimiz repositorymizi servisimizi  controller olustuarl�m
 birde kaydet metodu olustural�m (gerekli database ve bag�ml�l�k ayarlar�n� yapamay� unutmayal�m)

2- Register i�in bir request bir response olustural�m exception paketimizi olustural�m
-mapperlar� yapal�m kay�t yaparken username uniq olacak ona uygun kay�t metodunu yaz�n�z
 */

import com.bilgeadam.repository.enums.Roles;
import com.bilgeadam.repository.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;
    private String activationCode;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Roles role=Roles.USER;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status=Status.PENDING;


}
