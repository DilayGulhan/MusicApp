package com.researchecosystems.contentserviceapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "Invoice")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Invoice extends BaseEntity{
    private double fee ;

    private String contractRecordId;
    private boolean isPaid ;

}
