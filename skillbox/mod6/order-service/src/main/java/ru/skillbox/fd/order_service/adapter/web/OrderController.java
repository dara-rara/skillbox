package ru.skillbox.fd.order_service.adapter.web;

import ru.skillbox.fd.order_service.adapter.dto.Order;
import ru.skillbox.fd.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(value = "/order/", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody Order order) {
        orderService.sendMessage(order);
        return ResponseEntity.noContent().build();
    }

}
