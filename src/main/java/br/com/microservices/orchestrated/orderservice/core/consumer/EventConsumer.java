package br.com.microservices.orchestrated.orderservice.core.consumer;

import br.com.microservices.orchestrated.orderservice.core.service.OrderService;
import br.com.microservices.orchestrated.orderservice.core.utils.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class EventConsumer {

    private final OrderService service;
    private final JsonUtil jsonUtil;

    @KafkaListener(
        groupId = "${spring.kafka.consumer.group-id}",
        topics = "${spring.kafka.topic.consumer}"
    )
    public void listen(String payload) {
        try {
            log.info("Receiving starting notification event {} from order-start topic", payload);
            var event = jsonUtil.toEvent(payload);
            service.process(event);
        } catch (Exception ex) {
            log.error("Error trying to process event", ex);
        }
    }
}
