package medicalpatient.Parametros;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


import medicalpatient.model.LocalDataBase;
import medicalpatient.utils.DefaultCallback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import medicalpatient.utils.NetworkConstants;

public class AgentMonitor {

    public ArrayList<String> dates;

    public AgentMonitor(){
        dates = new ArrayList<String>();
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
                    String type = "";

                    if(pulso){

                        type = "0";
                    }

                    if(ecg){
                        type = "1";
                    }



                    RequestBody body = new FormBody.Builder()
                            .add("id", LocalDataBase.getInstance(null).getUser().getUID())
                            .add("type", type)
                            .build();

                    Request request = new Request.Builder()
                            .url(NetworkConstants.URL + NetworkConstants.PATH_MONITOR_DATES)
                            .post(body)
                            .build();

                    Response response = okhttp.newCall(request).execute();

                    if (response.code() == 200) {

                        JSONObject object = new JSONObject(response.body().string());

                        Log.i("Visaje: ",object.toString());
                        JSONArray array = object.getJSONArray("tomas");

                        dates = new ArrayList<String>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject aux = new JSONObject(array.get(i).toString());
                            dates.add(aux.getString("fecha"));
                        }

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