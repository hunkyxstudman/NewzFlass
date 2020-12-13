package com.dikamjitborah.hobarb.newzflass.Model;

public class NewsSchema {


    /**
     * id : 5fd4f6a6d4ee52001bfa8295
     * title : Virgin Galactic aborts SpaceShipTwo suborbital spaceflight
     * url : https://spacenews.com/virgin-galactic-aborts-spaceshiptwo-suborbital-spaceflight/
     * imageUrl : https://spacenews.com/wp-content/uploads/2020/12/ss2takeoff-2020dec12.jpg
     * newsSite : SpaceNews
     * summary : Virgin Galactic’s SpaceShipTwo aborted an attempted suborbital spaceflight Dec. 12 from Spaceport America in southern New Mexico.
     * publishedAt : 2020-12-12T16:58:14.000Z
     * updatedAt : 2020-12-12T16:58:15.039Z
     * featured : false
     * launches : []
     * events : []
     */

    private String id;
    private String title;
    private String url;
    private String imageUrl;
    private String newsSite;
    private String summary;
    private String publishedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNewsSite() {
        return newsSite;
    }

    public void setNewsSite(String newsSite) {
        this.newsSite = newsSite;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}