package kr.co.triggers.yolo.domain;

import java.util.Date;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Fiesta extends RealmObject {

    public static final String STATUS_ATTEND = "attend";
    public static final String STATUS_LIKE = "like";
    public static final String STATUS_NONE = "none";

    @PrimaryKey
    private long id;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }


    private String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }


    private String description;

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }


    private Date startTime;

    public Date getStartTime() { return startTime; }
    public void setStartTime(Date startTime) { this.startTime = startTime; }


    private Date endTime;

    public Date getEndTime() { return endTime; }
    public void setEndTime(Date endTime) { this.endTime = endTime; }


    private String location;

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }


    private double latitude;

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    private double longitude;

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }


    private String status;

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Ignore
    private List<Photo> photos;

    public List<Photo> getPhotos() { return photos; }
    public void setPhotos(List<Photo> photos) { this.photos = photos; }


    @Ignore
    private List<Data> meta;

    public List<Data> getMeta() { return meta; }
    public void setMeta(List<Data> meta) { this.meta = meta; }

    @Ignore
    private List<Perform> performs;

    public List<Perform> getPerforms() { return performs; }
    public void setPerforms(List<Perform> performs) { this.performs = performs; }


    private Date createdAt;

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }


    private Date updatedAt;

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

}
