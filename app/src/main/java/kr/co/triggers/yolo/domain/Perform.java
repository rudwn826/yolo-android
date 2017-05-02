package kr.co.triggers.yolo.domain;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Perform extends RealmObject {


    @PrimaryKey
    private long id;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }


    private Artist artist;

    public Artist getArtist() { return artist; }
    public void setArtist(Artist artist) { this.artist = artist; }


    private Date startTime;

    public Date getStartTime() { return startTime; }
    public void setStartTime(Date startTime) { this.startTime = startTime; }


    private Date endTime;

    public Date getEndTime() { return endTime; }
    public void setEndTime(Date endTime) { this.endTime = endTime; }


    private String stage;

    public String getStage() { return stage; }
    public void setStage(String stage) { this.stage = stage; }


    private boolean headliner;

    public boolean isHeadliner() { return headliner; }
    public void setHeadliner(boolean headliner) { this.headliner = headliner; }


    private Date createdAt;

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }


    private Date updatedAt;

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public boolean equals(Object obj) {

        if (obj == null || !(obj instanceof Perform))
            return false;

        Perform perform = (Perform) obj;
        return this.getId() == perform.getId();
    }
}
