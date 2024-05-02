package data.models;

import java.util.UUID;

public class EventModel {
    private String name;
    private String description;
    private UUID adminId;
    private UUID uid;

    public EventModel(String name, String description, UUID adminId, UUID uid) {
        this.name = name;
        this.description = description;
        this.adminId = adminId;
        this.uid = uid;
    }

    public EventModel() {
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
