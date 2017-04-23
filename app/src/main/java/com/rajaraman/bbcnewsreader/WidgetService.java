package com.rajaraman.bbcnewsreader;


import android.content.Intent;
import android.widget.RemoteViewsService;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        WidgetDataProvider widgetDataProvider = new WidgetDataProvider(getApplicationContext());

        query(widgetDataProvider);
        
        return widgetDataProvider;
    }

    private void query(final WidgetDataProvider widgetDataProvider) {

        final List<RssItem> items = new ArrayList<>();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference()
                .child("rssItems");

        mDatabase.limitToFirst(5)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            RssItem item = ds.getValue(RssItem.class);
                            items.add(item);
                        }

                        widgetDataProvider.setItems(items);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
