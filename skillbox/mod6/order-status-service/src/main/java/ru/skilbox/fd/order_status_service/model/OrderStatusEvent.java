package ru.skilbox.fd.order_status_service.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.Instant;

@Data
@Builder
@ToString
public class OrderStatusEvent {

    private String status;
    private Instant date;
}
