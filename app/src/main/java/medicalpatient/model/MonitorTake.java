package medicalpatient.model;


import java.util.ArrayList;

public class MonitorTake {



    public final static int PULSO = 0;
    public final static int ECG = 1;

    private String time_start;
    private String time_finish;
    private String time_pos_start;
    private String time_pos_finish;
    private String date;
    private String duration;
    private String pasos;
    private String pulsoMaximo;
    private String pulsoMinimo;
    private String pulsoPromedio;
    private String pulsoMaximo1;
    private String pulsoMinimo1;
    private String pulsoPromedio1;

    private String kgCalorias;
    private int type;
    private ArrayList<Integer> takes_1;
    private ArrayList<Integer> takes_2;


    public MonitorTake() {
        takes_1 = new ArrayList<>();
        takes_2 = new ArrayList<>();
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_finish() {
        return time_finish;
    }

    public void setTime_finish(String time_finish) {
        this.time_finish = time_finish;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<Integer> getTakes_1() {
        return takes_1;
    }

    public void setTakes_1(ArrayList<Integer> takes) {
        this.takes_1 = takes;
    }

    public String getTime_pos_start() {
        return time_pos_start;
    }

    public void setTime_pos_start(String time_pos_start) {
        this.time_pos_start = time_pos_start;
    }

    public String getTime_pos_finish() {
        return time_pos_finish;
    }

    public void setTime_pos_finish(String time_pos_finish) {
        this.time_pos_finish = time_pos_finish;
    }

    public String getPasos() {
        return pasos;
    }

    public void setPasos(String pasos) {
        this.pasos = pasos;
    }

    public String getPulsoMaximo() {
        return pulsoMaximo;
    }

    public void setPulsoMaximo(String pulsoMaximo) {
        this.pulsoMaximo = pulsoMaximo;
    }

    public String getPulsoMinimo() {
        return pulsoMinimo;
    }

    public void setPulsoMinimo(String pulsoMinimo) {
        this.pulsoMinimo = pulsoMinimo;
    }

    public String getKgCalorias() {
        return kgCalorias;
    }

    public void setKgCalorias(String kgCalorias) {
        this.kgCalorias = kgCalorias;
    }

    public String getPulsoPromedio() {
        return pulsoPromedio;
    }

    public void setPulsoPromedio(String pulsoPromedio) {
        this.pulsoPromedio = pulsoPromedio;
    }

    public String getPulsoMaximo1() {
        return pulsoMaximo1;
    }

    public void setPulsoMaximo1(String pulsoMaximo1) {
        this.pulsoMaximo1 = pulsoMaximo1;
    }

    public String getPulsoMinimo1() {
        return pulsoMinimo1;
    }

    public void setPulsoMinimo1(String pulsoMinimo1) {
        this.pulsoMinimo1 = pulsoMinimo1;
    }

    public String getPulsoPromedio1() {
        return pulsoPromedio1;
    }

    public void setPulsoPromedio1(String pulsoPromedio1) {
        this.pulsoPromedio1 = pulsoPromedio1;
    }

    public ArrayList<Integer> getTakes_2() {
        return takes_2;
    }

    public void setTakes_2(ArrayList<Integer> takes_2) {
        this.takes_2 = takes_2;
    }
}
