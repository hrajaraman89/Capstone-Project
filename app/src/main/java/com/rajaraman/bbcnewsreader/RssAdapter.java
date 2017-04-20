package com.rajaraman.bbcnewsreader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rajaraman.bbcnewsreader.util.Consumer;

import java.util.ArrayList;
import java.util.List;

public class RssAdapter extends RecyclerView.Adapter<RssViewHolder> {
    private final Context context;
    private final Consumer<RssItem> onClicked;
    private List<RssItem> items;

    RssAdapter(Context c, Consumer<RssItem> onClicked) {
        this.context = c;
        items = new ArrayList<>();
        this.onClicked = onClicked;
    }

    @Override
    public RssViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.card_rss_item, parent, false);
        final RssViewHolder rssViewHolder = new RssViewHolder(v);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClicked.accept(items.get(rssViewHolder.getAdapterPosition()));
            }
        });
        return rssViewHolder;
    }

    @Override
    public void onBindViewHolder(RssViewHolder holder, int position) {
        holder.updateView(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItems(List<RssItem> rssItems) {
        items.addAll(rssItems);
        notifyDataSetChanged();
    }

    public void setItems(List<RssItem> rssItems) {
        items.clear();
        addItems(rssItems);
    }
}
