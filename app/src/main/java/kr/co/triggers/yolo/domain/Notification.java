package kr.co.triggers.yolo.domain;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Notification extends RealmObject {

    public static final String TYPE_EVENT_FIESTA_CHANGES = "fiesta_changes";
    public static final String TYPE_EVENT_FIESTA_SUGGEST = "fiesta_suggest";
    public static final String TYPE_EVENT_ARTIST_SUGGEST = "artist_suggest";
    public static final String TYPE_EVENT_TRACK_SUGGEST = "track_suggest";

    @PrimaryKey
    private long id;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }


    private Fiesta fiesta;

    public Fiesta getFiesta() { return fiesta; }
    public void setFiesta(Fiesta fiesta) { this.fiesta = fiesta; }


    private String message;

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    private String type;

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    private Date read;

    public Date getRead() { return read; }
    public void setRead(Date read) { this.read = read; }


    private Date createdAt;

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }


    private Date updatedAt;

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
