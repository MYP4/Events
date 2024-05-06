package data.entity;

import java.util.UUID;

public class Ticket {
    private Long id;
    private UUID userId;
    private UUID specificId;
    private int status;
    private UUID uid;

    public Ticket(Long id, UUID userId, UUID specificId, int status, UUID uid) {
        this.id = id;
        this.userId = userId;
        this.specificId = specificId;
        this.status = status;
        this.uid = uid;
    }

    public Ticket() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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