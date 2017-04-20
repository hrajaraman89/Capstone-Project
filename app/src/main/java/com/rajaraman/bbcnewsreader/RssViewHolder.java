package com.rajaraman.bbcnewsreader;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

class RssViewHolder extends RecyclerView.ViewHolder {
    private TextView title, summary, dateAndAuthor;
    private SimpleDraweeView article;

    RssViewHolder(View itemView) {
        super(itemView);

        title = (TextView) itemView.findViewById(R.id.text_view_title);
        summary = (TextView) itemView.findViewById(R.id.text_view_summary);
        dateAndAuthor = (TextView) itemView.findViewById(R.id.text_view_date_and_author);

        article = (SimpleDraweeView) itemView.findViewById(R.id.image_view_article);
    }

    void updateView(RssItem item) {
        title.setText(item.title);
        summary.setText(item.description);
        dateAndAuthor.setText(item.pubDate);

        article.setImageURI(item.thumbnailUrl);
    }
}
