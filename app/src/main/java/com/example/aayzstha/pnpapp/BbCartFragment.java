package com.example.aayzstha.pnpapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Aayz Stha on 10/21/2017.
 */

public class BbCartFragment extends Fragment {
    Button proceed;
    ListView lView;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.5F);
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.bb_cart_screen,container,false);

        ((BottomBarTest) getActivity())
                .setActionBarTitle("My cart");

        proceed = (Button) v.findViewById(R.id.bt_checkout);
        lView = (ListView) v.findViewById(R.id.lv_cart);

        SharedPreferences pref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String proTitle = pref.getString("title", "empty");
        Log.i("butt",proTitle);
        String[] items = {proTitle};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.cart_items,items);
        lView.setAdapter(adapter);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                startActivity(new Intent(getActivity(),OrderForm.class));
            }
        });
        return v;
    }
}
