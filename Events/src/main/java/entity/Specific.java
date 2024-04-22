package entity;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Specific {
    private long id;
    private long eventId;
    private String description;
    private int ticketCount;
    private float price;
    private String address;
    private Timestamp date;
    private Integer dayOfWeek;
    private boolean isPrivate;
    private String code;
    private float rating;
    private UUID uid;

    public Specific(long id, long eventId, String description, int ticketCount, float price, String address, Timestamp date, Integer dayOfWeek, boolean isPrivate, String code, float rating, UUID uid) {
        this.id = id;
        this.eventId = eventId;
        this.description = description;
        this.ticketCount = ticketCount;
        this.price = price;
        this.address = address;
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.isPrivate = isPrivate;
        this.code = code;
        this.rating = rating;
        this.uid = uid;
    }

    public Specific() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
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

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }
}