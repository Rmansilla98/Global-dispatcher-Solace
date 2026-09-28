package com.newcron.globaldispatch.service;

import com.newcron.globaldispatch.model.DispatchRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class DispatchValidator {

    public String validate(DispatchRequest request) {

        LocalDate today = LocalDate.now();

        // 1. pickupDate no puede ser anterior a hoy
        if (request.getPickupDate() == null) {
            return "Pickup date is required.";
        }

        if (request.getPickupDate().isBefore(today)) {
            return "Pickup date cannot be earlier than the current date.";
        }

        // 2. Si pickupDate es hoy, no se puede recibir después de las 3 PM
        if (request.getPickupDate().isEqual(today)
                && LocalTime.now().isAfter(LocalTime.of(15, 0))) {

            return "Same-day pickup requests cannot be submitted after 3:00 p.m.";
        }

        // 3. deliveryDate debe existir
        if (request.getDeliveryDate() == null) {
            return "Delivery date is required.";
        }

        // 4. deliveryDate debe ser al menos un día después de pickupDate
        if (!request.getDeliveryDate().isAfter(request.getPickupDate())) {
            return "Delivery date must be at least one day after the pickup date.";
        }

        // Todo correcto
        return null;
    }
}

