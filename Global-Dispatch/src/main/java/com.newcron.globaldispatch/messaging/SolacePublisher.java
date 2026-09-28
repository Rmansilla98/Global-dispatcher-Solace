package com.newcron.globaldispatch.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newcron.globaldispatch.model.DispatchRequest;
import com.newcron.globaldispatch.model.DispatchResult;
import jakarta.annotation.PostConstruct;
import jakarta.jms.Connection;
import jakarta.jms.Queue;
import jakarta.jms.QueueConnection;
import jakarta.jms.QueueConnectionFactory;
import jakarta.jms.QueueSender;
import jakarta.jms.QueueSession;
import jakarta.jms.TextMessage;
import org.springframework.stereotype.Component;

import com.solacesystems.jms.SolConnectionFactory;
import com.solacesystems.jms.SolJmsUtility;

@Component
public class SolacePublisher {

    private final SolConnectionFactory connectionFactory;
    private final ObjectMapper objectMapper =
        new ObjectMapper().findAndRegisterModules();

    public SolacePublisher() throws Exception {

        this.connectionFactory =
                SolJmsUtility.createConnectionFactory();

        connectionFactory.setHost(
                "tcps://mr-connection-fgha20nn787.messaging.solace.cloud:55443"
        );

        connectionFactory.setVPN("global-dispatch");

        connectionFactory.setUsername("solace-cloud-client");

        connectionFactory.setPassword(
                System.getenv("SOLACE_PASSWORD")
        );
    }

    @PostConstruct
    public void testConnection() throws Exception {

        Connection connection =
                connectionFactory.createConnection();

        connection.start();
        connection.close();

        System.out.println("Solace Publisher conectado correctamente.");
    }

    public void publishDispatch(DispatchRequest request) throws Exception {

        publishToQueue(
                "global-dispatch-queue",
                request
        );
    }

    public void publishResult(DispatchResult result) throws Exception {

        publishToQueue(
                "global-dispatch-results",
                result
        );
    }

    private void publishToQueue(
            String queueName,
            Object messageObject) throws Exception {

        QueueConnection connection =
                ((QueueConnectionFactory) connectionFactory)
                        .createQueueConnection();

        connection.start();

        try (QueueSession session =
                     connection.createQueueSession(
                             false,
                             jakarta.jms.Session.AUTO_ACKNOWLEDGE)) {

            Queue queue =
                    session.createQueue(queueName);

            QueueSender sender =
                    session.createSender(queue);

            String json =
                    objectMapper.writeValueAsString(messageObject);

            TextMessage message =
                    session.createTextMessage(json);

            sender.send(message);

            sender.close();
        }

        connection.close();

        System.out.println(
                "Mensaje publicado en queue: " + queueName
        );
    }
}

