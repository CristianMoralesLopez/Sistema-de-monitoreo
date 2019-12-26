package medicalpatient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import com.cristian.sistemademonitoreo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import medicalpatient.home.HomeActivity;
import medicalpatient.login.LoginActivity;

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

                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }else{

                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);

                }
            }
        }, 3000);




    }
}
