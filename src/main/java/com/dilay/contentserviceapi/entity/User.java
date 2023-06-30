package com.dilay.contentserviceapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Table(name = "users")
@Entity
@Getter
@Setter
public class User extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "verification_code_expiration_date")
    private ZonedDateTime verificationCodeExpirationDate;

    @Column(name = "recovery_code")
    private String recoveryCode;

    @Column(name = "recovery_code_expiration_date")
    private ZonedDateTime recoveryCodeExpirationDate;

    @Column(name = "is_verified")
    private boolean verified;

    @ManyToOne
    private Subscription subscription;

    @OneToOne
    private Invoice invoice;

//    @ManyToMany(fetch = FetchType.LAZY)
//
//    private List<Video> favoriteVideos= new LinkedList<>();




}
