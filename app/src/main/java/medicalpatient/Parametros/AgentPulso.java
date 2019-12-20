package medicalpatient.Parametros;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;


import medicalpatient.model.LocalDataBase;
import medicalpatient.utils.ListDuo;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utils.DefaultCallback;
import utils.NetworkConstants;

public class AgentPulso {

    public static  AgentPulso INSTANCE = null;

    public ListDuo takes;
    public medical.model.MonitorTake take;

    public ListDuo getTakes() {
        return takes;
    }



    public void setTakes(ListDuo takes) {
        this.takes = takes;

    }

    public ArrayList<String> getDates() {
        return dates;
    }

    public void setDates(ArrayList<String> dates) {
        this.dates = dates;
    }

    public ArrayList<String> dates;

    public AgentPulso(){
        dates = new ArrayList<String>();
        takes = new ListDuo();
        INSTANCE = this;
    }

    public void getDataMonitorDate(final String date, final String type, final DefaultCallback notify) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okhttp = new OkHttpClient.Builder()
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .build();



                    RequestBody body = new FormBody.Builder()
                            .add("id", LocalDataBase.getInstance(null).getUser().getId())
                            .add("type", type)
                            .add("date", date)
                            .build();

                    Request request = new Request.Builder()
                            .url(NetworkConstants.URL + NetworkConstants.PATH_MONITORING)
                            .post(body)
                            .build();

                    Response response = okhttp.newCall(request).execute();

                    Log.i("ERROR CODE",""+response.code());


                    if (response.code() == 200) {

                        JSONObject object = new JSONObject(response.body().string());

                        JSONObject monitor = null;
                        if(type.equals("0")){
                            monitor = object.getJSONObject("pulso");
                        }else{
                            monitor = object.getJSONObject("ecg");
                        }

                        take = new medical.model.MonitorTake();
                        take.setDate(date);
                        take.setType(Integer.parseInt(type));
                        take.setTime_start(monitor.getString("horaInicio"));
                        take.setTime_finish(monitor.getString("horaFin"));
                        take.setDuration(monitor.getString("duracion"));
                        take.setTime_pos_start(monitor.getString("horaInicio1"));
                        take.setTime_pos_finish(monitor.getString("horaFin1"));

                        JSONArray val_1 = monitor.getJSONArray("valoresPulso");

                        for (int i = 0; i < val_1.length(); i++)
                            take.getTakes_1().add(val_1.getInt(i));

                        JSONArray val_2 = monitor.getJSONArray("valoresPulso2");

                        for (int i = 0; i < val_2.length(); i++)
                            take.getTakes_2().add(val_2.getInt(i));

                        Log.i("takes_1", take.getTakes_1().size() + "");
                        Log.i("takes_2", take.getTakes_2().size() + "");

                        notify.onFinishProcess(true, "success");
                    } else {
                        notify.onFinishProcess(false, "Error intente nuevamente");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    notify.onFinishProcess(false, "Error en el servidor");
                }
            }
        }).start();
    }

    public void getMonitoringDates(final boolean pulso, final boolean ecg, final DefaultCallback notify) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okhttp = new OkHttpClient.Builder()
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .build();



                    RequestBody body = new FormBody.Builder()
                            .add("id", LocalDataBase.getInstance(null).getUser().getId())
                            .add("type", "0")
                            .add("date","2019/12/1")
                            .build();

                    Request request = new Request.Builder()
                            .url(NetworkConstants.URL + NetworkConstants.PATH_MONITORING)
                            .post(body)
                            .build();

                    Response response = okhttp.newCall(request).execute();

                    if (response.code() == 200) {



                        notify.onFinishProcess(true, "success");
                    } else {
                        notify.onFinishProcess(false, "Error intente nuevamente");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    notify.onFinishProcess(false, "Error en el servidor");
                }
            }
        }).start();
    }
}