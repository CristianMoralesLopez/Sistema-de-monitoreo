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

    public ListDuo getTakes() {
        return takes;
    }

    public void setTakes(ListDuo takes) {
        this.takes = takes;
        INSTANCE = this;
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
    }

    public void getDataMonitorDate(final String date, final DefaultCallback notify) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okhttp = new OkHttpClient.Builder()
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .build();


                    RequestBody body = new FormBody.Builder()
                            .add("id", "b4Suc3zhlPbR3LJyy7QY7vGtHUQ2")
                            .add("type", "0")
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

                        JSONArray array = object.getJSONArray("pulso");

                        takes = new ListDuo();

                        for (int i = 0; i < array.length(); i++) {

                            JSONArray auxArray =  array.getJSONArray(i);
                            String fecha =  auxArray.get(0).toString();
                            String valor =  auxArray.get(1).toString();

                            takes.add(fecha,valor);
                        }

                        Log.i("COUNT_TAKES",takes.size()+"");

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