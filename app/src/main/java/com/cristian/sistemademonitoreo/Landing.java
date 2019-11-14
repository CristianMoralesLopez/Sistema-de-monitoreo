package com.cristian.sistemademonitoreo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Landing extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        firebaseAuth = FirebaseAuth.getInstance();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                if(firebaseUser ==null) {

                    Intent intent = new Intent(Landing.this, Login.class);
                    startActivity(intent);
                }else{

                    Intent intent = new Intent(Landing.this, MainActivity.class);
                    startActivity(intent);

                }
            }
        }, 3000);




    }
}
