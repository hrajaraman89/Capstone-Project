package com.rajaraman.bbcnewsreader.util;

/**
 * Created by hrajaram on 1/8/17.
 */

public interface Function<P, R> {
    R apply(P parameter);
}

