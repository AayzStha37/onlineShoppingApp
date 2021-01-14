package com.example.aayzstha.pnpapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Aayz Stha on 10/21/2017.
 */

public class BbUserFragment extends Fragment implements View.OnClickListener {
    Button ordderHistory;
    Button contact;
    Button address;
    Button logOut;
    TextView uName;
    TextView email;
    TextView phone;
    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.bb_user_info_frag,container,false);

        ((BottomBarTest) getActivity())
                .setActionBarTitle("Customer Details");

        firebaseAuth=FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()==null){
            getActivity().finish();
            startActivity( new Intent(getActivity(),LogIn.class));
        }


        logOut = (Button) v.findViewById(R.id.logout);
        contact = (Button) v.findViewById(R.id.contact);
        address = (Button) v.findViewById(R.id.address);

        uName = (TextView) v.findViewById(R.id.txt_user_name);
        email = (TextView) v.findViewById(R.id.txt_email);
        phone = (TextView) v.findViewById(R.id.txt_phn);

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                UserInformation uInfo=dataSnapshot.child(user.getUid()).getValue(UserInformation.class);

                String userName = uInfo.getuName();
                String userEmail = uInfo.getEmail();
                String userPhone = uInfo.getContact();

                uName.setText(userName);
                email.setText(userEmail);
                phone.setText(userPhone);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        logOut.setOnClickListener(this);
        contact.setOnClickListener(this);
        address.setOnClickListener(this);
        return v;
    }

    //for button animation effect
    private AlphaAnimation buttonClick = new AlphaAnimation(2F, 0.5F);

    @Override
    public void onClick(View v) {
        if(v==contact){
            v.startAnimation(buttonClick);
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:9823487220"));
            startActivity(intent);
        }
        if(v==logOut){
            v.startAnimation(buttonClick);
            firebaseAuth.signOut();
            getActivity().finish();
            startActivity(new Intent(getActivity(),LogIn.class));
        }
        if(v==address){
            v.startAnimation(buttonClick);
            startActivity(new Intent(getActivity(),AddressRecord.class));
        }
    }
}
