package com.researchecosystems.contentserviceapi.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "Payment")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment extends BaseEntity {
    private double amount ;
    private String invoiceId ;
    private String senderCard ;
    private String receiverCard ;
}
