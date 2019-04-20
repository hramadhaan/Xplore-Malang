package com.xploremalang.xploremalang.UploadFoto;

public class Upload {
    private String postId;
    private String imageFeed;
    private String deskripsi;
    private String publisher;
    private String gambarPublisher;

    public Upload(String postId, String imageFeed, String deskripsi, String publisher, String gambarPublisher) {
        this.postId = postId;
        this.imageFeed = imageFeed;
        this.deskripsi = deskripsi;
        this.publisher = publisher;
        this.gambarPublisher = gambarPublisher;
    }

    public Upload() {
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getImageFeed() {
        return imageFeed;
    }

    public void setImageFeed(String imageFeed) {
        this.imageFeed = imageFeed;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getGambarPublisher() {
        return gambarPublisher;
    }

    public void setGambarPublisher(String gambarPublisher) {
        this.gambarPublisher = gambarPublisher;
    }
}
