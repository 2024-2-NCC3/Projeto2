package br.fecap.pi.voice.model;

public class Noticia {

    private String imageUrl;
    private String title;
    private String date;
    private String externalUrl;

    public Noticia(String imageUrl, String title, String date, String externalUrl) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.date = date;
        this.externalUrl = externalUrl;
    }

    // Getters
    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getExternalUrl() {
        return externalUrl;
    }
}