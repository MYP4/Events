package data.models;

import java.util.UUID;

public class TicketModel {
    private int userId;
    private int specificId;
    private int status;
    private UUID uid;

    public TicketModel() {
    }

    public TicketModel(int userId, int specificId, int status, UUID uid) {
        this.userId = userId;
        this.specificId = specificId;
        this.status = status;
        this.uid = uid;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSpecificId() {
        return specificId;
    }

    public void setSpecificId(int specificId) {
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
