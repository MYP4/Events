package data.entity;


import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Specific {
    private Long id;
    private UUID eventId;
    private String description;
    private int ticketCount;
    private BigDecimal price;
    private String address;
    private UUID uid;

    public Specific(long id, UUID eventId, String description, int ticketCount, BigDecimal price, String address, UUID uid) {
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Specific specific = (Specific) o;
        return ticketCount == specific.ticketCount && Objects.equals(eventId, specific.eventId) && Objects.equals(description, specific.description) && Objects.equals(price, specific.price) && Objects.equals(address, specific.address) && Objects.equals(uid, specific.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, description, ticketCount, price, address, uid);
    }
}