package com.newcron.globaldispatch.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newcron.globaldispatch.model.DispatchRequest;
import com.newcron.globaldispatch.service.GlobalDispatchService;

import jakarta.annotation.PostConstruct;
import jakarta.jms.MessageConsumer;
import jakarta.jms.Queue;
import jakarta.jms.QueueConnection;
import jakarta.jms.QueueConnectionFactory;
import jakarta.jms.QueueSession;
import jakarta.jms.TextMessage;

import org.springframework.stereotype.Component;

import com.solacesystems.jms.SolConnectionFactory;
import com.solacesystems.jms.SolJmsUtility;

@Component
public class SolaceConsumer {

    private SolConnectionFactory connectionFactory;

    private ObjectMapper objectMapper =
        new ObjectMapper().findAndRegisterModules();

    private final GlobalDispatchService globalDispatchService;

    public SolaceConsumer(
            GlobalDispatchService globalDispatchService) throws Exception {

        this.globalDispatchService = globalDispatchService;

        this.connectionFactory =
                SolJmsUtility.createConnectionFactory();

        connectionFactory.setHost(
                "tcps://mr-connection-fgha20nn787.messaging.solace.cloud:55443"
        );

        connectionFactory.setVPN("global-dispatch");

        connectionFactory.setUsername("solace-cloud-client");

        connectionFactory.setPassword("h300mlhm9bahig3mkatd2h66o9");
    }

    @PostConstruct
    public void startConsumer() throws Exception {

        QueueConnection connection =
                ((QueueConnectionFactory) connectionFactory)
                        .createQueueConnection();

        connection.start();

        QueueSession session =
                connection.createQueueSession(
                        false,
                        jakarta.jms.Session.AUTO_ACKNOWLEDGE
                );

        Queue queue =
                session.createQueue("global-dispatch-queue");

        MessageConsumer consumer =
                session.createConsumer(queue);

        consumer.setMessageListener(message -> {

            try {

                if (message instanceof TextMessage textMessage) {

                    String contenido = textMessage.getText();

                    DispatchRequest request =
                            objectMapper.readValue(
                                    contenido,
                                    DispatchRequest.class
                            );

                    System.out.println(
                            "=== DISPATCH RECIBIDO DESDE SOLACE ==="
                    );

                    System.out.println(
                            "Shipper Order ID: "
                                    + request.getShipperOrderId()
                    );

                    System.out.println(
                            "Pickup Date: "
                                    + request.getPickupDate()
                    );

                    System.out.println(
                            "Delivery Date: "
                                    + request.getDeliveryDate()
                    );

                    System.out.println(
                            "Price: "
                                    + request.getPrice()
                    );

                    System.out.println(
                            "========================================"
                    );
                }

            } catch (Exception e) {

                e.printStackTrace();
            }
        });

        System.out.println(
                "SolaceConsumer escuchando en: global-dispatch-queue"
        );
    }
}