package bcc.ifsuldeminas.sistemaMusicas.service;

import java.util.List;

public class DeezerArtistResponse {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Genres getGenres() {
        return genres;
    }

    public void setGenres(Genres genres) {
        this.genres = genres;
    }

    private String name;
    private String link;
    private Genres genres;



    public static class Genres {
        public List<DeezerGenre> getData() {
            return data;
        }

        public void setData(List<DeezerGenre> data) {
            this.data = data;
        }

        private List<DeezerGenre> data;


    }
}
