package com.rajaraman.bbcnewsreader;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Setter;
import com.github.gfx.android.orma.annotation.Table;

@Table
public class RssItem {
    @Column
    public final String title;

    @Column
    public final String link;

    @PrimaryKey
    public final String guid;

    @Column
    public final String description;

    @Column(indexed = true)
    public final String pubDate;

    @Column
    public final String thumbnailUrl;

    @Column
    public final int thumbnailWidth;

    @Column
    public final int thumbnailHeight;

    @Setter
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
}
