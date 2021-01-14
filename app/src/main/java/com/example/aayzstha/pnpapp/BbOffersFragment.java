package com.example.aayzstha.pnpapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Aayz Stha on 10/21/2017.
 */

public class BbOffersFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.bb_offer_screen,container,false);
        ((BottomBarTest) getActivity())
                .setActionBarTitle("Offers");
        return v;
    }
}
