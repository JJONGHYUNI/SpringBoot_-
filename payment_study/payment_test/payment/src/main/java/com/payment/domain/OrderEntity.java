package com.payment.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long itemIdx;
    private String itemName;
    private Long itemAmount;
    private Long itemPrice;
    private String orderStatus;

    public void setStatus(String status) {
        this.orderStatus = status;
    }
}
