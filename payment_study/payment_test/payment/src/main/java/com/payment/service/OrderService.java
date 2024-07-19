package com.payment.service;

import com.payment.domain.OrderEntity;
import com.payment.dto.OrderSaveDto;
import com.payment.dto.ResponseDto;
import com.payment.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public ResponseEntity<?> save(OrderSaveDto dto) {
        OrderEntity entityForSaving = OrderEntity.builder()
                .itemIdx(dto.getItem().getItemIdx())
                .itemPrice(dto.getItem().getItemPrice())
                .itemName(dto.getItem().getItemName())
                .itemAmount(dto.getItem().getItemAmount())
                .build();

        OrderEntity order = orderRepository.save(entityForSaving);

        return new ResponseEntity<>(
                ResponseDto.builder()
                        .code(0)
                        .message("주문 저장 성공")
                        .data(order.getId())
                        .build(), HttpStatus.OK
        );
    }
}
