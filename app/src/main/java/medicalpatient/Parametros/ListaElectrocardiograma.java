package medicalpatient.Parametros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.cristian.sistemademonitoreo.R;

import utils.DefaultCallback;

public class ListaElectrocardiograma extends AppCompatActivity implements DefaultCallback {

    private AgentMonitor agent;
    private RecyclerView recycler;
    private DateAdapter2 adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_pulso);

        agent = new AgentMonitor();

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue_strong));

        adapter = new DateAdapter2(agent, this);
        recycler = findViewById(R.id.recycler_fechas);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);

        agent.getMonitoringDates(false, true, this);

    }


    @Override
    public void onFinishProcess(boolean hasSucceeded, Object result) {
        if(hasSucceeded) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"FAIL",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
