package com.example.aayzstha.pnpapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Aayz Stha on 8/16/2017.
 */
public class OrderForm extends AppCompatActivity {
    WebView webVw;
    SwipeRefreshLayout refresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_form);

        getSupportActionBar().setTitle("Order Form");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webVw= (WebView) findViewById(R.id.webvw);
        WebSettings webSet =webVw.getSettings();
        String url="https://docs.google.com/forms/d/e/1FAIpQLSdZn6dFHper3TMFiGJIcTpohRgFy9Z4ZetvHTu51U3wOT0Giw/viewform";
        //setting the webview functions
        webSet.setJavaScriptEnabled(true);
        webSet.setBuiltInZoomControls(true);
        webSet.setUseWideViewPort(true);    //get original dimensions of the webpage
        webSet.setLoadWithOverviewMode(true);   //squeeze the webpage dimensions to the webview
        webVw.setWebViewClient(new WebViewClient());
        webVw.loadUrl(url);

        refresh= (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webVw.reload();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
