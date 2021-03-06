package com.rajaraman.bbcnewsreader;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rajaraman.bbcnewsreader.util.Consumer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RssAdapter adapter;

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, "ca-app-pub-2429807662274221~3515272994");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        this.adapter = new RssAdapter(this, new Consumer<RssItem>() {
            @Override
            public void accept(RssItem item) {
                Intent i = new Intent(MainActivity.this, ArticleActivity.class);
                i.putExtra(ArticleActivity.URL, item.link);

                startActivity(i);
            }
        });

        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        fetch();

        this.swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetch();

            }
        });

        EventBus.getDefault().register(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFeedChangedEvent(FeedChangedEvent event) {
        fetch();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void fetch() {

        String key = this.getString(R.string.selected_feed_key);

        String rssUrl = PreferenceManager.getDefaultSharedPreferences(this).getString(key, null);

        if (rssUrl == null) {
            return;
        }

        RssLoader loader = new RssLoader(rssUrl, new Consumer<List<RssItem>>() {
            @Override
            public void accept(final List<RssItem> rssItems) {
                adapter.setItems(rssItems);
                swipeRefreshLayout.setRefreshing(false);

                for (RssItem r : rssItems) {

                    mDatabase.child("rssItems")
                            .child(r.guid.hashCode() + "")
                            .setValue(r);
                }
            }
        });

        loader.execute();
    }


    static class FeedChangedEvent {

    }
}
