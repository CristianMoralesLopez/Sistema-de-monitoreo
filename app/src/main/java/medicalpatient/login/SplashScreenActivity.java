package medicalpatient.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.cristian.sistemademonitoreo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import medicalpatient.home.HomeActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private AgentLogin agentLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        agentLogin = new AgentLogin(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (agentLogin.isSingIn())
                    intent = new Intent(getApplicationContext(), HomeActivity.class);
                else
                    intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2500);
    }
}

