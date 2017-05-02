package kr.co.triggers.yolo.domain;

import java.util.Date;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Track extends RealmObject {

    public static final String KEY_TRACK_ID = "track.id";
    public static final String KEY_TRACK_NAME = "track.name";
    public static final String KEY_TRACK_LINK = "track.link";

    @PrimaryKey
    private long id;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }


    private int rank;

    public int getRank() { return rank; }
    public void setRank(int rank) { this.rank = rank; }


    private String title;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }


    private Date release;

    public Date getRelease() { return release; }
    public void setRelease(Date release) { this.release = release; }


    private String link;

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }


    private int dropAt;

    public int getDropAt() { return dropAt; }
    public void setDropAt(int dropAt) { this.dropAt = dropAt; }


    private boolean liked;

    public boolean isLiked() { return liked; }
    public void setLiked(boolean liked) { this.liked = liked; }

    @Ignore
    private List<Artist> composers;

    public List<Artist> getComposers() { return composers; }
    public void setComposers(List<Artist> composers) { this.composers = composers; }

    @Ignore
    private List<Data> genres;

    public List<Data> getGenres() { return genres;}
    public void setGenres(List<Data> genres) { this.genres = genres; }

    private Date createdAt;

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }


    private Date updatedAt;

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

}
