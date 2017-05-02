package kr.co.triggers.yolo.domain;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Feed extends RealmObject {

    @PrimaryKey
    private String id;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }


    private String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }


    private String picture;

    public String getPicture() { return picture; }
    public void setPicture(String picture) { this.picture = picture; }


    private Date createdAt;

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }


    private Date updatedAt;

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
