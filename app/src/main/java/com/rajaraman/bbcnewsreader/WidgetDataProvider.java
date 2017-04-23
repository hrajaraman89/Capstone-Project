package com.rajaraman.bbcnewsreader;

import android.content.Context;
import android.graphics.Color;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext = null;
    private List<RssItem> items;


    WidgetDataProvider(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        while (items == null) {

        }

        return items.size();
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


        RssItem rssItem = items.get(position);

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
    }

    @Override
    public void onDataSetChanged() {
    }


    @Override
    public void onDestroy() {
    }

    public void setItems(List<RssItem> items) {
        this.items = items;
    }
}
