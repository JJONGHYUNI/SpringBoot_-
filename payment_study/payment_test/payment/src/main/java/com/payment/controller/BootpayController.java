package com.payment.controller;

import com.payment.dto.BootpayDto;
import com.payment.service.BootpayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bootpay")
public class BootpayController {

    private final BootpayService bootpayService;

    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody BootpayDto dto) {
        return bootpayService.priceCheck(dto);
    }
}
