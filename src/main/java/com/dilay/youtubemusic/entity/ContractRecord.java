package com.dilay.youtubemusic.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ContractRecord")
public class ContractRecord extends BaseEntity{
    private String name ;
    private double monthlyFee ;
    private int duration ;
    private boolean isActive ;

    private String userId ;
    @ManyToOne
    private Invoice invoice;


}
