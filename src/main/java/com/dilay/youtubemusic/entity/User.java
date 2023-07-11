package com.dilay.youtubemusic.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.dynamic.scaffold.MethodGraph;

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

    @JoinTable(name = "user_video",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "video_id"))
    @ManyToMany(fetch = FetchType.LAZY)
    @Builder.Default
    private Set <Video> favoriteVideos= new HashSet<>();




}
