package it.eng.unipa.filesharing.dto;

import java.util.UUID;

public class EmailDTO {
    private String email;

    private UUID uuid;

    private String bucketName;

    private String uniqueId;


    public String getEmail() {
        return email;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getBucketName() {
        return bucketName;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
    public String toString(){
        return "email:"+this.email;
    }

}
