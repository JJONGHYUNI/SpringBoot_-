package com.payment.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ResponseDto<T> {
    private Integer code;
    private String message;
    private T data;
}
