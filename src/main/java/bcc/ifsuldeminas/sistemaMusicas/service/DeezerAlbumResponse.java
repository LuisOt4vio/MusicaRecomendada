package bcc.ifsuldeminas.sistemaMusicas.service;

import java.util.List;

public class DeezerAlbumResponse {
    private Long id;
    private String title;

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public TrackList getTracks() {
        return tracks;
    }

    public void setTracks(TrackList tracks) {
        this.tracks = tracks;
    }

    public Genre getGenres() {
        return genres;
    }

    public void setGenres(Genre genres) {
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Artist artist;
    private Genre genres;
    private TrackList tracks;

    public static class Artist {
        private Long id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        private String name;
        private String link;


    }

    public static class Genre {
        public List<GenreData> getData() {
            return data;
        }

        public void setData(List<GenreData> data) {
            this.data = data;
        }

        private List<GenreData> data;


    }

    public static class GenreData {
        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String name;


    }

    public static class TrackList {
        public List<Track> getData() {
            return data;
        }

        public void setData(List<Track> data) {
            this.data = data;
        }

        private List<Track> data;

    }

    public static class Track {
        private Long id;
        private String title;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }


    }
}