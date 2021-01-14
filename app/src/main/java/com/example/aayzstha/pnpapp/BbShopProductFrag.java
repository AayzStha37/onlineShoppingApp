package com.example.aayzstha.pnpapp;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.aayzstha.pnpapp.R.id.swipeContainer;

/**
 * Created by Aayz Stha on 11/11/2017.
 */

public class BbShopProductFrag extends Fragment {
    WebView webVw;
    SwipeRefreshLayout refresh;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    ProgressDialog pDiag;

    FloatingActionButton cart, order, big;
    Animation open, close, clock, antiClock;
    String x;

    boolean isOpen = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bb_shop_screen, container, false);

        Bundle bundle = getArguments();
        String webId = bundle.getString("URL");

        ((BottomBarTest) getActivity())
                .setActionBarTitle("Products");

        webVw = (WebView) v.findViewById(R.id.shop_web);
        refresh = (SwipeRefreshLayout) v.findViewById(swipeContainer);
        cart = (FloatingActionButton) v.findViewById(R.id.cart_button);
        order = (FloatingActionButton) v.findViewById(R.id.order_button);
        big = (FloatingActionButton) v.findViewById(R.id.big_button);

        pDiag = new ProgressDialog(getActivity());

        open = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);
        close = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_close);
        clock = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_clockwise);
        antiClock = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_anti_clock);


        firebaseAuth= FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();

        WebSettings webSet = webVw.getSettings();

        final String url1 = webId;
        //setting the webview functions
        webSet.setJavaScriptEnabled(true);
        webSet.setBuiltInZoomControls(true);
        webSet.setUseWideViewPort(true);    //get original dimensions of the webpage
        webSet.setLoadWithOverviewMode(true);   //squeeze the webpage dimensions to the webview
        webVw.setWebViewClient(new WebViewClient());
        webVw.loadUrl(url1);

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webVw.reload();
            }
        });

        if (refresh.isRefreshing()) {
            refresh.setRefreshing(false);
        }


        big.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    cart.startAnimation(close);
                    order.startAnimation(close);
                    big.startAnimation(antiClock);
                    order.setClickable(false);
                    cart.setClickable(false);
                    isOpen = false;

                } else {
                    cart.startAnimation(open);
                    order.startAnimation(open);
                    big.startAnimation(clock);
                    order.setClickable(true);
                    String key = "product";
                    String webUrl1 = webVw.getUrl();
                    Log.i("URLLL",webUrl1);
                    if(webUrl1.contains(key)){
                        cart.setClickable(true);
                    }
                    else{
                        cart.setVisibility(View.INVISIBLE);
                        cart.setClickable(false);
                    }
                    isOpen = true;

                    order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getActivity(), OrderForm.class));
                        }
                    });

                    cart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String webUrl = webVw.getUrl();
                            Toast.makeText(getActivity(),"1item added to cart",Toast.LENGTH_SHORT).show();
                            Ion.with(getActivity())
                                    .load(webUrl)
                                    .asString().setCallback(new FutureCallback<String>() {
                                @Override
                                public void onCompleted(Exception e, String result) {
                                            Pattern pattern = Pattern.compile("<title>(.+?)</title>");
                                            Matcher matcher = pattern.matcher(result);
                                            while (matcher.find()) {
                                                String z = matcher.group(1);
                                               x = "";
                                                for (int i = 0; i < z.length(); i++) {
                                                    char pp = z.charAt(i);
                                                    if (pp != '|')
                                                        x += pp;
                                            else
                                                break;
                                        }
                                        break;
                                            }
                                    Log.i("itile",x);
                                    SavePreferences("title",x);
                                }

                                               /* databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                FirebaseUser user=firebaseAuth.getCurrentUser();
                                                UserInformation userinfo=dataSnapshot.child(user.getUid()).getValue(UserInformation.class);
                                                Log.i("TITLE","sxcfshv");
                                                if(x !=null){
                                                    Log.i("TITLE222",x);
                                                    userinfo.setTitle(x.toString());}
                                                Log.i("TITdasdLE","dfbae");
                                                databaseReference.child(user.getUid()).setValue(userinfo);
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }

                                    }
                                    );*/

                                });

                        }
                    });
                }
            }
        });

        return v;
    }
    private void SavePreferences(String key,String value)
    {
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}

