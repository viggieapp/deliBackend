package net.simihost.deli.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * Created by Rashed on 19/03/2019
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @XmlTransient
    private static final long serialVersionUID = -3424777851855986787L;
    @XmlTransient
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @XmlTransient
    @Version
    @JsonIgnore
    private Integer version;
    @XmlTransient
    @JsonIgnore
    private String status;
    @XmlTransient
    @Column(name = "creation_time")
    @JsonIgnore
    private Date creationTime;
    @XmlTransient
    @Column(name = "last_updated")
    @JsonIgnore
    private Date lastUpdated;

    public BaseEntity() {
        super();
    }

    @PrePersist
    public void prePersist() {
        this.creationTime = new Date();
        this.lastUpdated = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.lastUpdated = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
