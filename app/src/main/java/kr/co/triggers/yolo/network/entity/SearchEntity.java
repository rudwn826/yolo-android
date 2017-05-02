package kr.co.triggers.yolo.network.entity;

import java.util.List;

import kr.co.triggers.yolo.domain.Artist;
import kr.co.triggers.yolo.domain.Fiesta;
import kr.co.triggers.yolo.domain.Track;

public class SearchEntity {

    private List<Artist> artists;

    public List<Artist> getArtists() { return artists; }
    public void setArtists(List<Artist> artists) { this.artists = artists; }

    private List<Fiesta> fiesta;

    public List<Fiesta> getFiesta() { return fiesta; }
    public void setFiesta(List<Fiesta> fiesta) { this.fiesta = fiesta; }

    private List<Track> tracks;

    public List<Track> getTracks() { return tracks; }
    public void setTracks(List<Track> tracks) { this.tracks = tracks; }
}
