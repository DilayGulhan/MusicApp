package com.researchecosystems.contentserviceapi.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "Payment")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment extends BaseEntity {
    private int amount ;
    @ManyToOne
    private Invoice invoice ;
    private String senderCard ;
    private String receiverCard ;
}
