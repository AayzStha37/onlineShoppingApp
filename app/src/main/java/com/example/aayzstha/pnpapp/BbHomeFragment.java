package com.example.aayzstha.pnpapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Aayz Stha on 10/21/2017.
 */

public class BbHomeFragment extends Fragment {
    SwipeRefreshLayout refresh;
    WebView webVw1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.bb_home_screen,container,false);

        ((BottomBarTest) getActivity())
                .setActionBarTitle("Pick N Pay NP");
        refresh= (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer1);
        webVw1= (WebView) v.findViewById(R.id.home);
        WebSettings webSet = webVw1.getSettings();
        String url2 = "https://www.facebook.com/Picknpay.pvt.ltd/";
        webSet.setJavaScriptEnabled(true);
        webSet.setBuiltInZoomControls(true);
        webSet.setUseWideViewPort(true);    //get original dimensions of the webpage
        webSet.setLoadWithOverviewMode(true);   //squeeze the webpage dimensions to the webview
        webVw1.setWebViewClient(new WebViewClient());
        webVw1.loadUrl(url2);

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webVw1.reload();
            }
        });

        if (refresh.isRefreshing()) {
            refresh.setRefreshing(false);
        }

        return v;
    }
}
