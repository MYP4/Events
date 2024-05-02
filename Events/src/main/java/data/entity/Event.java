package data.entity;

import java.util.UUID;

public class Event {
    private long id;
    private String name;
    private String description;
    private UUID adminId;
    private UUID uid;

    public Event(long id, String name, String description, UUID adminId, UUID uid) {
        this.id = id;
        this.name = name;
        this.description = description;
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
