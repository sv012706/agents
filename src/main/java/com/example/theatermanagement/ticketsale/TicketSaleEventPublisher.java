package com.example.theatermanagement.ticketsale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class TicketSaleEventPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketSaleEventPublisher.class);

    private final KafkaTemplate<String, TicketSaleCreatedEvent> kafkaTemplate;
    private final String topic;

    public TicketSaleEventPublisher(
            KafkaTemplate<String, TicketSaleCreatedEvent> kafkaTemplate,
            @Value("${theater.kafka.ticket-sales-topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publish(TicketSaleCreatedEvent event) {
        kafkaTemplate.send(topic, event.saleId().toString(), event)
                .whenComplete((result, failure) -> {
                    if (failure != null) {
                        LOGGER.error("Failed to publish ticket sale event for sale {}", event.saleId(), failure);
                    } else {
                        LOGGER.debug("Published ticket sale event for sale {}", event.saleId());
                    }
                });
    }
}
