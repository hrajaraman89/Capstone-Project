package com.rajaraman.bbcnewsreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

public class ArticleActivity extends AppCompatActivity {

    public static final String URL = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Intent i = getIntent();

        String url = i.getStringExtra(URL);

        WebView wv = (WebView) findViewById(R.id.web_view_article);

        wv.loadUrl(url);
    }
}
