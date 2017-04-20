package com.rajaraman.bbcnewsreader.util;

import android.net.Uri;

public class ImageUtil {

    private static final String URL = "http://image.tmdb.org/t/p/w500/";

    public static Uri toPoster(String path) {
        return Uri.parse(URL + path);
    }
}
