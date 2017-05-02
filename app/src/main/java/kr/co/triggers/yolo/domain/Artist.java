package kr.co.triggers.yolo.domain;

import java.util.Date;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Artist extends RealmObject {

    public static final String KEY_ARTIST_ID = "artist.id";
    public static final String KEY_ARTIST_NAME = "artist.name";

    @PrimaryKey
    private long id;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }


    private String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Ignore
    private List<Feed> feeds;

    public List<Feed> getFeeds() { return feeds; }
    public void setFeeds(List<Feed> feeds) { this.feeds = feeds; }

    @Ignore
    private List<Data> data;

    public List<Data> getData() { return data; }
    public void setData(List<Data> data) { this.data = data; }

    @Ignore
    private List<Track> tracks;


    public List<Track> getTracks() { return tracks; }
    public void setTracks(List<Track> tracks) { this.tracks = tracks; }


    private boolean liked;

    public boolean isLiked() { return liked; }
    public void setLiked(boolean liked) { this.liked = liked; }


    private long rank = Long.MAX_VALUE;

    public long getRank() { return rank; }
    public void setRank(long rank) { this.rank = rank; }


    private Date createdAt;

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }


    private Date updatedAt;

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
