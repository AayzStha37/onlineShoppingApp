package com.example.aayzstha.pnpapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Aayz Stha on 7/15/2017.
 */
public class SignUp extends Activity implements View.OnClickListener {
    Button fb,google,signup,prevLogin;
    EditText name, phone, email,pw,confirm;
    private FirebaseAuth firebaseauth;
    private DatabaseReference dbRef;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseauth=FirebaseAuth.getInstance();
        dbRef= FirebaseDatabase.getInstance().getReference();

        fb= (Button) findViewById(R.id.bt_fb_login);
        google= (Button) findViewById(R.id.bt_google_login);
        signup= (Button) findViewById(R.id.bt_signup);
        prevLogin= (Button) findViewById(R.id.bt_prev_login);
        name= (EditText) findViewById(R.id.edtxt_name);
        phone= (EditText) findViewById(R.id.edtxt_phone);
        email= (EditText) findViewById(R.id.edtxt_email);
        pw= (EditText) findViewById(R.id.edtxt_sp_pw);
        confirm= (EditText) findViewById(R.id.edtxt_sp_confirmpw);

        progressDialog = new ProgressDialog(this);
        signup.setOnClickListener(this);
        prevLogin.setOnClickListener(this);
    }

    private void registerUser(){
        String uemail= email.getText().toString().trim();
        String password= pw.getText().toString().trim();
        String confirmPw= confirm.getText().toString().trim();

        if(TextUtils.isEmpty(uemail) || TextUtils.isEmpty(password)){
            Toast.makeText(this, "The field can not be empty!",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.equals(password,confirmPw)==false){
            Toast.makeText(this, "Password do not match!",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering you...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        firebaseauth.createUserWithEmailAndPassword(uemail, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Welcome :)" ,Toast.LENGTH_SHORT).show();
                            saveUserInfo();
                            finish();
                            startActivity(new Intent(getApplicationContext(),BottomBarTest.class));
                            //Intent i= new Intent(getApplicationContext(),BottomBarTest.class);
                            //startActivity(i);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Registration Error!" ,Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    private void saveUserInfo(){
        String uName= name.getText().toString().trim();
        String contact= phone.getText().toString().trim();
        String uemail= email.getText().toString().trim();

        UserInformation info =new UserInformation(uName,uemail,contact);

        FirebaseUser user = firebaseauth.getCurrentUser();

        dbRef.child(user.getUid()).setValue(info);
    }

    @Override
    public void onClick(View v) {
        if (v==signup){
            registerUser();
        }
        if(v==prevLogin){
            Intent intent = new Intent(SignUp.this,LogIn.class);
            startActivity(intent);
        }
    }
    @Override
    public void onBackPressed() {
        finish();
       startActivity(new Intent(SignUp.this,LogIn.class));
    }
}
