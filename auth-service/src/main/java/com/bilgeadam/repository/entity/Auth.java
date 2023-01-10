package com.bilgeadam.repository.entity;
/*
1- username,pasword email activatedcode
 Role ,Status -> Enum olacak
 bunlarla birlikte entityimiz repositorymizi servisimizi  controller olustuarlım
 birde kaydet metodu olusturalım (gerekli database ve bagımlılık ayarlarını yapamayı unutmayalım)

2- Register için bir request bir response olusturalım exception paketimizi olusturalım
-mapperları yapalım kayıt yaparken username uniq olacak ona uygun kayıt metodunu yazınız
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
    @Column(unique = true)
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
