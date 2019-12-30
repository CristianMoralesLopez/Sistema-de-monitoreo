package medicalpatient.help;

import java.util.concurrent.TimeUnit;

import medicalpatient.utils.DefaultCallback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import medicalpatient.utils.NetworkConstants;

public class AgentHelp {

    public void sendMessageSupport(final String reason, final String message, final DefaultCallback notify) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okhttp = new OkHttpClient.Builder()
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .build();


                    RequestBody body = new FormBody.Builder()
                            .add("userId", "123_ID_USUARIO_QUEMADO_APP")
                            .add("reason", reason)
                            .add("message", message)
                            .build();

                    Request request = new Request.Builder()
                            .url(NetworkConstants.URL + NetworkConstants.PATH_HELP)
                            .put(body)
                            .build();

                    Response response = okhttp.newCall(request).execute();

                    if (response.isSuccessful()) {
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
