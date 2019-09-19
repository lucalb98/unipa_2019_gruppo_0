package it.eng.unipa.filesharing.model;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class Url {

    @Id
    @SequenceGenerator(name="url_seq", initialValue=1, allocationSize=1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="url_seq")
    private Long id;


   // private String email;
    private UUID uuid;
    private String bucketName;
    private String uniqueId;
    @Column(unique = true)
    private String token;
    /*@Column(nullable = true)
    //private Integer otp*/
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    private Date dataScadenza;


    public Date getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /*public String getEmail() {
        return email;
    }*/

    /*public void setEmail(String email) {
        this.email = email;
    }*/

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /*public Integer getOtp() {
        return otp;
    }*/

    /*public void setOtp(Integer otp) {
        this.otp = otp;
    }*/


}

