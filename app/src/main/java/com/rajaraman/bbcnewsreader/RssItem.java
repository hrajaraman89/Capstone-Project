package com.rajaraman.bbcnewsreader;

import com.google.gson.Gson;

public class RssItem {
    public String title;

    public String link;

    public String guid;

    public String description;

    public String pubDate;

    public String thumbnailUrl;

    public int thumbnailWidth;

    public int thumbnailHeight;

    public RssItem() {
    }

    public RssItem(String title,
                   String link,
                   String guid,
                   String description,
                   String pubDate,
                   String thumbnailUrl,
                   int thumbnailWidth,
                   int thumbnailHeight) {
        this.title = title;
        this.link = link;
        this.guid = guid;
        this.description = description;
        this.pubDate = pubDate;
        this.thumbnailUrl = thumbnailUrl;
        this.thumbnailWidth = thumbnailWidth;
        this.thumbnailHeight = thumbnailHeight;
    }

    public String toString() {
        return new Gson().toJson(this);
    }
}
