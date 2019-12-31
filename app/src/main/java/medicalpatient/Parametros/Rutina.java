package medicalpatient.Parametros;

import java.io.Serializable;
import java.util.ArrayList;

public class Rutina implements Serializable {

    private ArrayList <Integer> valoresPulso;
    private ArrayList <Integer> valoresPulso2;


    private String horaInicio;
    private String horaFin;
    private String duracion;
    private String horaInicio1;
    private String horaFin1;


    public Rutina(ArrayList<Integer> valoresPulso, ArrayList<Integer> valoresPulso2, String horaInicio, String horaFin, String duracion) {
        this.valoresPulso = valoresPulso;
        this.valoresPulso2 = valoresPulso2;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.duracion = duracion;
    }

    public Rutina (){

    }

    public ArrayList<Integer> getValoresPulso() {
        return valoresPulso;
    }

    public void setValoresPulso(ArrayList<Integer> valoresPulso) {
        this.valoresPulso = valoresPulso;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public ArrayList<Integer> getValoresPulso2() {
        return valoresPulso2;
    }

    public void setValoresPulso2(ArrayList<Integer> valoresPulso2) {
        this.valoresPulso2 = valoresPulso2;
    }

    public String getHoraInicio1() {
        return horaInicio1;
    }

    public void setHoraInicio1(String horaInicio1) {
        this.horaInicio1 = horaInicio1;
    }

    public String getHoraFin1() {
        return horaFin1;
    }

    public void setHoraFin1(String horaFin1) {
        this.horaFin1 = horaFin1;
    }
}