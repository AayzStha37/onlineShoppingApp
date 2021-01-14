package com.example.aayzstha.pnpapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Aayz Stha on 7/15/2017.
 */
public class LogIn extends Activity implements View.OnClickListener{
    EditText uname,pswd;
    Button login,fpw,signup;
    ProgressDialog progressDialog;
    private FirebaseAuth fireBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a);

        fireBase =FirebaseAuth.getInstance();

        if(fireBase.getCurrentUser() != null){
            Intent i= new Intent(getApplicationContext(),BottomBarTest.class);
            startActivity(i);
        }

        uname= (EditText) findViewById(R.id.mal);
        pswd= (EditText) findViewById(R.id.pswd);
        login= (Button) findViewById(R.id.login);
        fpw= (Button) findViewById(R.id.pw);
        signup= (Button) findViewById(R.id.act);
        progressDialog = new ProgressDialog(this);

        login.setOnClickListener(this);
        fpw.setOnClickListener(this);
        signup.setOnClickListener(this);

    }

    private void userLogin(){
        String email= uname.getText().toString().trim();
        String password= pswd.getText().toString().trim();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(this, "The field can not be empty!",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Just a moment!");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        fireBase.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            Intent i= new Intent(getApplicationContext(),BottomBarTest.class);
                            startActivity(i);
                        }
                    }
                });

    }
    @Override
    public void onClick(View v) {
        int id= v.getId();
        switch (id){
            case R.id.login:
                userLogin();
                break;
            case R.id.pw:
                Intent intent = new Intent(LogIn.this,ForgotPw.class);
                startActivity(intent);
                break;
            case R.id.act:
                finish();
                startActivity(new Intent(LogIn.this,SignUp.class));
                break;
        }
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
