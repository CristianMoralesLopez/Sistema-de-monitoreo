package medicalpatient.help;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.core.content.ContextCompat;

import com.cristian.sistemademonitoreo.R;

public class HelpActivity extends Activity {

    Spinner spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue));
        }


        spin = (Spinner) findViewById(R.id.spinner);

// Array of Months acting as a data pump
        String[] objects = { "Seleccionar", "Sugerencia", "Queja", "Reclamo"};

// Declaring an Adapter and initializing it to the data pump
        ArrayAdapter adapter = new ArrayAdapter(
                getApplicationContext(),android.R.layout.simple_list_item_1 ,objects);

// Setting Adapter to the Spinner
        spin.setAdapter(adapter);

    }



}
