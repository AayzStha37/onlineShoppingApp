package com.example.aayzstha.pnpapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

/**
 * Created by Aayz Stha on 10/21/2017.
 */

public class BbShopFragment extends Fragment implements View.OnClickListener{
    Button mobile;
    Button audio;
    Button decor;
    Button watch;
    Button game;
    //FragmentCommunicator comm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.bb_shop_catalog,container,false);

        ((BottomBarTest) getActivity())
                .setActionBarTitle("Shop");

        mobile = (Button) v.findViewById(R.id.bt_mob);
        audio = (Button) v.findViewById(R.id.bt_audio);
        decor = (Button) v.findViewById(R.id.bt_decor);
        watch = (Button) v.findViewById(R.id.bt_watch);
        game = (Button) v.findViewById(R.id.bt_game);

        mobile.setOnClickListener(this);
        audio.setOnClickListener(this);
        decor.setOnClickListener(this);
        watch.setOnClickListener(this);
        game.setOnClickListener(this);

        return v;
    }
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.5F);


    @Override
    public void onClick(View v) {
        if(v==mobile){
            v.startAnimation(buttonClick);
            String mobUrl = "https://www.facebook.com/pg/Picknpay.pvt.ltd/shop/?cid=628239617301404";
            Bundle bundle = new Bundle();
            bundle.putString("URL",mobUrl);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            BbShopProductFrag fragment = new BbShopProductFrag();
            fragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.frag1, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        if(v==audio){
            v.startAnimation(buttonClick);
            String mobUrl1 = "https://www.facebook.com/pg/Picknpay.pvt.ltd/shop/?cid=628239910634708";
            Bundle bundle = new Bundle();
            bundle.putString("URL",mobUrl1);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            BbShopProductFrag fragment = new BbShopProductFrag();
            fragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.frag1, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }
        if(v==decor){
            v.startAnimation(buttonClick);
            String mobUrl2 = "https://www.facebook.com/pg/Picknpay.pvt.ltd/shop/?cid=628240230634676";
            Bundle bundle = new Bundle();
            bundle.putString("URL",mobUrl2);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            BbShopProductFrag fragment = new BbShopProductFrag();
            fragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.frag1, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }
        if(v==watch){
            v.startAnimation(buttonClick);
            String mobUrl3= "https://www.facebook.com/pg/Picknpay.pvt.ltd/shop/?cid=628243547301011";
            Bundle bundle = new Bundle();
            bundle.putString("URL",mobUrl3);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            BbShopProductFrag fragment = new BbShopProductFrag();
            fragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.frag1, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }
        if(v==game){
            v.startAnimation(buttonClick);
            String mobUrl4 = "https://www.facebook.com/pg/Picknpay.pvt.ltd/shop/?cid=761201870671844";
            Bundle bundle = new Bundle();
            bundle.putString("URL",mobUrl4);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            BbShopProductFrag fragment = new BbShopProductFrag();
            fragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.frag1, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

    }
}
