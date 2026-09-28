package com.newcron.globaldispatch.model;

public class DispatchResult {

    private String shipperOrderId;
    private String status;
    private String notes;

    public DispatchResult() {
    }

    public DispatchResult(
            String shipperOrderId,
            String status,
            String notes) {

        this.shipperOrderId = shipperOrderId;
        this.status = status;
        this.notes = notes;
    }

    public String getShipperOrderId() {
        return shipperOrderId;
    }

    public void setShipperOrderId(String shipperOrderId) {
        this.shipperOrderId = shipperOrderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
