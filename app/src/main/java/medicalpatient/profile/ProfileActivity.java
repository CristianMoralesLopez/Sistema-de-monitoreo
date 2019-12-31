package medicalpatient.profile;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.cristian.sistemademonitoreo.R;
import com.squareup.picasso.Picasso;


public class ProfileActivity extends Activity {

    /**
     * Atributos del paciente
     */
    private TextView name;
    private TextView id;
    private TextView birth_date;
    private TextView age;
    private TextView risk;
    private TextView diagnostic;
    private TextView email;
    private TextView telephone;
    private TextView mobile_number;
    private TextView state;
    private TextView city;
    private TextView address;
    private TextView weight;
    private TextView height;

    private TextView password;
    private TextView password_confirm;

    /**
     * Atributos del contacto del paciente
     */

    private TextView name_contact;
    private TextView telephone_contact;
    private TextView relation;

    private ImageView imageProfile;

    /**
     * Atributos de la clase
     */


    /**
     * Atributos de la clase
     */
    private AgentProfile agent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue_strong));

        agent = new AgentProfile();

        // init patient attributes
        imageProfile = findViewById(R.id.image_profile);
        name = findViewById(R.id.name);
        name.setText(agent.getUser().getName());
        id = findViewById(R.id.id);
        id.setText(agent.getUser().getId());
        birth_date = findViewById(R.id.birth_date);
        birth_date.setText(agent.getUser().getBirth());
        age = findViewById(R.id.age);
        age.setText(agent.getUser().getAge());
        risk = findViewById(R.id.risk);
        risk.setText(agent.getUser().getRisk());
        diagnostic = findViewById(R.id.diagnostic);
        diagnostic.setText(agent.getUser().getDiagnostic());
        email = findViewById(R.id.email);
        email.setText(agent.getUser().getEmail());
        telephone = findViewById(R.id.telephone);
        telephone.setText(agent.getUser().getTelephone());
        mobile_number = findViewById(R.id.mobile_number);
        mobile_number.setText(agent.getUser().getMobile_number());
        state = findViewById(R.id.state);
        state.setText(agent.getUser().getState());
        city = findViewById(R.id.city);
        city.setText(agent.getUser().getCity());
        address = findViewById(R.id.address);
        address.setText(agent.getUser().getAddress());

        weight = findViewById(R.id.weight);
        weight.setText(agent.getUser().getWeight());

        height = findViewById(R.id.height);
        height.setText(agent.getUser().getHeight());


        // init contact attributes
        name_contact = findViewById(R.id.name_contact);
        name_contact.setText(agent.getUser().getName_contact());
        telephone_contact = findViewById(R.id.telephone_contact);
        telephone_contact.setText(agent.getUser().getTelephone_contact());
        relation = findViewById(R.id.relation);
        relation.setText(agent.getUser().getRelation());


        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/grade-project-pdg.appspot.com/o/Captura.PNG?alt=media&token=70d1a7a3-7742-4193-8898-4e590877156e").into(imageProfile);


    }
}
