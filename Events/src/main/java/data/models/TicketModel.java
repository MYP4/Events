package data.models;

import java.util.UUID;

public class TicketModel {
    private UUID userId;
    private UUID specificId;
    private int status;
    private UUID uid;

    public TicketModel() {
    }

    public TicketModel(UUID userId, UUID specificId, int status, UUID uid) {
        this.userId = userId;
        this.specificId = specificId;
        this.status = status;
        this.uid = uid;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getSpecificId() {
        return specificId;
    }

    public void setSpecificId(UUID specificId) {
        this.specificId = specificId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }
}
