package ru.skilbox.fd.order_status_service.listener;

import ru.skilbox.fd.order_status_service.model.OrderEvent;
import ru.skilbox.fd.order_status_service.service.StatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMessageListener {

    private final StatusService service;

    @KafkaListener(
            topics = "${app.kafka.orderTopic}",
            groupId = "${app.kafka.orderGroupId}",
            containerFactory = "kafkaMessageConcurrentKafkaListenerContainerFactory"
    )
    public void listen(@Payload OrderEvent message) {
        if (message.getProduct().isEmpty()) {
            service.sendMessage("PROCESS");
        } else {
            service.sendMessage("CREATED");
        }
    }

}
