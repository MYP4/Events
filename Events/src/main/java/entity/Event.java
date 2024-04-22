package entity;

import java.math.BigDecimal;
import java.util.UUID;

public class Event {
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String address;
    private double rating;
    private UUID adminId;
    private UUID uid;

    public Event(int id, String name, String description, BigDecimal price, String address, float rating, UUID adminId, UUID uid) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.address = address;
        this.rating = rating;
        this.adminId = adminId;
        this.uid = uid;
    }

    public Event() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public UUID getAdminId() {
        return adminId;
    }

    public void setAdminId(UUID adminId) {
        this.adminId = adminId;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }
}
