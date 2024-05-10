package data.entity;

import java.util.Objects;
import java.util.UUID;

public class Event {
    private Long id;
    private String name;
    private String description;
    private UUID adminId;
    private UUID uid;

    public Event(Long id, String name, String description, UUID adminId, UUID uid) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.adminId = adminId;
        this.uid = uid;
    }

    public Event() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return  Objects.equals(name, event.name) && Objects.equals(description, event.description) && Objects.equals(adminId, event.adminId) && Objects.equals(uid, event.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, adminId, uid);
    }
}
