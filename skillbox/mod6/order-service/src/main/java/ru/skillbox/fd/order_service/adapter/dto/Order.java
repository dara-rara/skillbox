package ru.skillbox.fd.order_service.adapter.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {

    private String product;
    private Integer quantity;
}
