package com.dikamjitborah.hobarb.newzflass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {

    Toolbar toolbar;
    WebView newswebview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        toolbar = findViewById(R.id.tb_wv);
        setSupportActionBar(toolbar);

        Intent indisplay = getIntent();
        String url = indisplay.getStringExtra("link");
        String id = indisplay.getStringExtra("id");
        toolbar.setTitleTextAppearance(this, R.style.font);
        toolbar.setTitle("" + id);


        newswebview = (WebView) findViewById(R.id.webView_wv);
        newswebview.setWebViewClient(new WebViewClient());

        WebSettings webSettings = newswebview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        newswebview.getSettings().setSupportZoom(true);       //Zoom Control on web (You don't need this
        //if ROM supports Multi-Touch
        newswebview.getSettings().setBuiltInZoomControls(true); //Enable Multitouch if supported by ROM


        newswebview.loadUrl(url);

    }
}