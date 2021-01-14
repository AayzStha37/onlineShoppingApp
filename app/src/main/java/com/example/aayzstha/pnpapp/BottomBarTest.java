package com.example.aayzstha.pnpapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.google.firebase.auth.FirebaseAuth;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarBadge;
import com.roughike.bottombar.OnMenuTabClickListener;

public class BottomBarTest extends AppCompatActivity {
    private BottomBar mBottomBar;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        firebaseAuth= FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity( new Intent(getApplicationContext(),LogIn.class));
        }

        setContentView(R.layout.activity_bottombar);
        mBottomBar=BottomBar.attach(this,savedInstanceState);
        mBottomBar.setItemsFromMenu(R.menu.menu_bottombar_items, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int i) {
                if(i==R.id.baritem1){
                    BbHomeFragment f = new BbHomeFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag1,f).commit();
                }
                else if(i==R.id.baritem2){
                    BbOffersFragment f = new BbOffersFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag1,f).commit();
                }
                else if(i==R.id.baritem3){
                    BbShopFragment f = new BbShopFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag1,f).commit();
                }
                else if(i==R.id.baritem4){
                    BbCartFragment f = new BbCartFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag1,f).commit();
                }
                else if(i==R.id.baritem5){
                    BbUserFragment f = new BbUserFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag1,f).commit();
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {

            }
        });
        mBottomBar.mapColorForTab(0,"#cc931a");
        mBottomBar.mapColorForTab(1,"#cc931a");
        mBottomBar.mapColorForTab(2,"#cc931a");
        mBottomBar.mapColorForTab(3,"#cc931a");
        mBottomBar.mapColorForTab(4,"#cc931a");
        BottomBarBadge unSeen;
        unSeen= mBottomBar.makeBadgeForTabAt(3,"#FF0000",0);
        unSeen.show();
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bottombar_items,menu);
        return true;
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
