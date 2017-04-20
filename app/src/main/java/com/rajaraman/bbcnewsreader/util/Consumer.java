package com.rajaraman.bbcnewsreader.util;

/**
 * Created by hrajaram on 12/14/16.
 */

public interface Consumer<T> {
    void accept(T t);
}
