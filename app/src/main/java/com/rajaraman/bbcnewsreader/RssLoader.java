package com.rajaraman.bbcnewsreader;

import android.os.AsyncTask;
import android.util.Log;

import com.rajaraman.bbcnewsreader.util.Consumer;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import org.jdom2.Attribute;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class RssLoader extends AsyncTask<Void, Void, List<RssItem>> {
    private static final String TAG = RssLoader.class.getCanonicalName();
    private final Consumer<List<RssItem>> onComplete;
    private final String rssUrl;

    RssLoader(String rssUrl, Consumer<List<RssItem>> onComplete) {
        this.onComplete = onComplete;
        this.rssUrl = rssUrl;
    }

    @Override
    protected List<RssItem> doInBackground(Void... params) {
        try {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(new URL(rssUrl)));

            List<RssItem> result = new ArrayList<>();

            List<SyndEntry> entries = feed.getEntries();

            for (SyndEntry entry : entries) {

                Media m = null;

                for (org.jdom2.Element e : entry.getForeignMarkup()) {
                    if (e.getName().equals("thumbnail")) {
                        m = new Media();
                        for (Attribute a : e.getAttributes()) {
                            if (a.getName().equals("height")) {
                                m.height = Integer.parseInt(a.getValue());
                            } else if (a.getName().equals("width")) {
                                m.width = Integer.parseInt(a.getValue());
                            } else if (a.getName().equals("url")) {
                                m.url = a.getValue();
                            }
                        }
                        break;
                    }

                }
                RssItem item;

                if (m == null) {
                    item = new RssItem(entry.getTitle(),
                            entry.getLink(),
                            entry.getLink(),
                            entry.getDescription().getValue(),
                            entry.getPublishedDate().toString(),
                            null,
                            0,
                            0);

                } else {
                    item = new RssItem(entry.getTitle(),
                            entry.getLink(),
                            entry.getLink(),
                            entry.getDescription().getValue(),
                            entry.getPublishedDate().toString(),
                            m.url,
                            m.width,
                            m.height);
                }
                result.add(item);
            }

            return result;


        } catch (IOException | FeedException e) {
            Log.e(TAG, "can't load rss url", e);
        }

        return new ArrayList<>();
    }

    @Override
    protected void onPostExecute(List<RssItem> rssItems) {
        onComplete.accept(rssItems);
    }

    private static class Media {
        private int height, width;
        private String url;
    }
}
