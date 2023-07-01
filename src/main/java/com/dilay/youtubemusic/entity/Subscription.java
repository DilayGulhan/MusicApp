package com.dilay.youtubemusic.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;


@Table(name = "subscription")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Subscription extends BaseEntity {

    private String name ;

    private double monthlyFee ;

    private int duration ;

    private boolean isActive  ;

}
