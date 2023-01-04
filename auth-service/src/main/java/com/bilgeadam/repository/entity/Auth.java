package com.bilgeadam.repository.entity;
/*
1- username,pasword email activatedcode
 Role ,Status -> Enum olacak
 bunlarla birlikte entityimiz repositorymizi servisimizi  controller olustuarlým
 birde kaydet metodu olusturalým (gerekli database ve bagýmlýlýk ayarlarýný yapamayý unutmayalým)

2- Register için bir request bir response olusturalým exception paketimizi olusturalým
-mapperlarý yapalým kayýt yaparken username uniq olacak ona uygun kayýt metodunu yazýnýz
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
