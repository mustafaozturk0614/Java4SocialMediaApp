package com.bilgeadam.repository.entity;

import com.bilgeadam.repository.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.ObjectInputFilter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private  Long authId;
    @Column(unique = true)
    private  String username;
    private  String name;
    private    String email;
    private    String phone;
    private    String avatar;
    private    String address;
    private    String about;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status=Status.PENDING;
    long createdDate;
    long updatedDate;

}
