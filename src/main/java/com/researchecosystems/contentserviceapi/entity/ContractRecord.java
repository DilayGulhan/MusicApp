package com.researchecosystems.contentserviceapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "ContractRecord")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContractRecord extends BaseEntity{
    private String name ;
    private double monthlyFee ;
    private int duration ;
    private boolean isActive ;

    private String userId ;


}
