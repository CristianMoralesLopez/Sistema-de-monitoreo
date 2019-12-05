package medicalpatient.Parametros;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cristian.sistemademonitoreo.R;

import medicalpatient.graphicecg.GraficaECG2;
import medicalpatient.graphicecg.graficaECG;

public class DateAdapter2  extends RecyclerView.Adapter<DateAdapter2.DateHolder> {
    private AgentMonitor agent;
    private Activity activity;


    public DateAdapter2(AgentMonitor agent, Activity activity) {
        this.agent = agent;
        this.activity = activity;
    }

    @NonNull
    @Override
    public DateHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_date, parent, false);
        return new DateHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateHolder holder, int position) {
        holder.position = position;
        holder.textView.setText(agent.dates.get(position)+"");
    }

    @Override
    public int getItemCount() {
        return agent.dates.size();
    }

    public class DateHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public int position;

        public TextView textView;

        public DateHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent in = new Intent(activity, GraficaECG2.class);
            in.putExtra("id", agent.dates.get(position));
            in.putExtra("fecha", agent.dates.get(position));
            activity.startActivity(in);
        }
    }
}
