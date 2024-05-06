package data.entity;


import java.util.UUID;

public class Specific {
    private Long id;
    private UUID eventId;
    private String description;
    private int ticketCount;
    private float price;
    private String address;
    private UUID uid;

    public Specific(long id, UUID eventId, String description, int ticketCount, float price, String address, UUID uid) {
        this.id = id;
        this.eventId = eventId;
        this.description = description;
        this.ticketCount = ticketCount;
        this.price = price;
        this.address = address;
        this.uid = uid;
    }

    public Specific() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UUID getUid() { return uid; }

    public void setUid(UUID uid) {
        this.uid = uid;
    }
}