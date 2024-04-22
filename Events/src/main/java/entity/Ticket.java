package entity;

import java.util.UUID;

public class Ticket {
    private long id;
    private int userId;
    private int specificId;
    private int status;
    private String feedback;
    private double rating;
    private UUID uid;

    public Ticket(long id, int userId, int specificId, int status, String feedback, double rating, UUID uid) {
        this.id = id;
        this.userId = userId;
        this.specificId = specificId;
        this.status = status;
        this.feedback = feedback;
        this.rating = rating;
        this.uid = uid;
    }

    public Ticket() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }
}