package kr.co.triggers.yolo.network.entity;

import java.util.List;

import kr.co.triggers.yolo.domain.Artist;
import kr.co.triggers.yolo.domain.Track;

public class TagEntity {

    private Artist artist;

    public Artist getArtist() { return artist; }
    public void setArtist(Artist artist) { this.artist = artist; }

    private List<Track> tracks;

    public List<Track> getTracks() { return tracks; }
    public void setTracks(List<Track> tracks) { this.tracks = tracks; }
}
