package medicalpatient.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.cristian.sistemademonitoreo.R;
import com.google.android.material.navigation.NavigationView;

import medicalpatient.Electrocardiograma;
import medicalpatient.Parametros.ListaElectrocardiograma;
import medicalpatient.Parametros.ListadoPulso;
import medicalpatient.help.HelpActivity;
import medicalpatient.login.LoginActivity;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private CustomViewPager pager;
    private TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
//00        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.nameUserNav);

        pager = findViewById(R.id.view_pager);
        pager.setPagingEnabled(false);

        title = findViewById(R.id.title);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue_strong));
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent in;
        switch (item.getItemId()) {
            /*
            case R.id.home:
                Toast.makeText(this, "Home",Toast.LENGTH_LONG);
                break;
*/
            case R.id.ecgActivity:
                Toast.makeText(this, "Electrocardiograma", Toast.LENGTH_LONG);

                in = new Intent(HomeActivity.this, Electrocardiograma.class);
                startActivity(in);
                break;
/*
            case R.id.agenda:
                Toast.makeText(this, "Agenda",Toast.LENGTH_LONG);
                break;
*/
            case R.id.pulso:
                Toast.makeText(this, "Pulso", Toast.LENGTH_LONG);
                in = new Intent(HomeActivity.this, ListadoPulso.class);
                startActivity(in);
                break;
            case R.id.help:
                Toast.makeText(this, "Help", Toast.LENGTH_LONG);

                in = new Intent(HomeActivity.this, HelpActivity.class);
                startActivity(in);
                break;
            case R.id.electocardiograma:
                Toast.makeText(this, "Electrocardiograma", Toast.LENGTH_LONG);

                in = new Intent(HomeActivity.this, ListaElectrocardiograma.class);
                startActivity(in);
                break;

            case R.id.logout:
                Toast.makeText(this, "Salio", Toast.LENGTH_LONG);
               // (new AgentLogin(this)).logout();
                in = new Intent(this, LoginActivity.class);
                startActivity(in);
                finish();
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
}
