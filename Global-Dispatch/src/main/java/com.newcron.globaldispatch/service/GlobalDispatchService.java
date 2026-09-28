package com.newcron.globaldispatch.service;

import com.newcron.globaldispatch.messaging.SolacePublisher;
import com.newcron.globaldispatch.model.DispatchRequest;
import com.newcron.globaldispatch.model.DispatchResult;
import org.springframework.stereotype.Service;

@Service
public class GlobalDispatchService {

    private final DispatchValidator dispatchValidator;
    private final SolacePublisher solacePublisher;

    public GlobalDispatchService(
            DispatchValidator dispatchValidator,
            SolacePublisher solacePublisher) {

        this.dispatchValidator = dispatchValidator;
        this.solacePublisher = solacePublisher;
    }

    public String getMessage() {
        return "Global Dispatch funcionando correctamente";
    }

    public DispatchResult validateDispatch(
            DispatchRequest request) {

        String validationError =
                dispatchValidator.validate(request);

        // Si la solicitud es inválida
        if (validationError != null) {

            DispatchResult result =
                    new DispatchResult(
                            request.getShipperOrderId(),
                            "Cancelled",
                            validationError
                    );

            try {
                solacePublisher.publishResult(result);
            } catch (Exception e) {
                throw new RuntimeException(
                        "Error publishing cancelled result to Solace",
                        e
                );
            }

            return result;
        }

        // Si la solicitud es válida,
        // se envía a la cola de órdenes para los carriers.
        try {

            solacePublisher.publishDispatch(request);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error publishing dispatch to Solace",
                    e
            );
        }

        return new DispatchResult(
                request.getShipperOrderId(),
                "Valid",
                "Dispatch request is valid."
        );
    }
}