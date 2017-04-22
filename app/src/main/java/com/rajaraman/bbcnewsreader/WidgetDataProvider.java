package com.rajaraman.bbcnewsreader;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {
    private final OrmaDatabase db;
    private Context mContext = null;
    private Cursor cursor;

    WidgetDataProvider(Context context, Intent intent) {
        mContext = context;
        this.db = OrmaDatabase.builder(mContext)
                .name("articles.db")
                .build();
    }

    @Override
    public int getCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews mView = new RemoteViews(mContext.getPackageName(),
                android.R.layout.simple_list_item_1);

        cursor.moveToPosition(position);

        RssItem rssItem = db
                .newRssItemFromCursor(cursor);

        String text = rssItem.title;

        mView.setTextViewText(android.R.id.text1, text);
        mView.setTextColor(android.R.id.text1, Color.BLACK);
        return mView;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onCreate() {
        initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    private void initData() {

        Thread thread = new Thread() {
            public void run() {
                query();
            }
        };

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
        }
    }

    private void query() {
        this.cursor = db.selectFromRssItem().execute();
    }

    @Override
    public void onDestroy() {
        cursor.close();
    }

}
