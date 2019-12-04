package medicalpatient.login;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import medicalpatient.model.LocalDataBase;
import medicalpatient.utils.NetworkConstants;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utils.DefaultCallback;
import medicalpatient.model.LocalDataBase;
import medicalpatient.model.User;

public class AgentLogin {
    private FirebaseAuth firebaseAuth;

    public AgentLogin(Context context) {
        firebaseAuth = FirebaseAuth.getInstance();
        LocalDataBase.getInstance(context);
    }

    public boolean isSingIn() {
        return LocalDataBase.getInstance(null).getUser() != null;
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        LocalDataBase.getInstance(null).deletedCredentials();
    }

    public void registrar(final String email, final String password, final DefaultCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //saveUIDLogin();
                            getUserData(callback);
                        } else
                            callback.onFinishProcess(false, null);
                    }
                });

            }
        }).start();

    }

    private void getUserData(final DefaultCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    OkHttpClient okhttp = new OkHttpClient.Builder()
                            .connectTimeout(5, TimeUnit.SECONDS)
                            .readTimeout(5, TimeUnit.SECONDS)
                            .build();

                    Request request = new Request.Builder()
                            .url(NetworkConstants.URL + NetworkConstants.PATH_PROFILE)
                            .get()
                            .addHeader("id", firebaseAuth.getInstance().getCurrentUser().getUid())
                            .build();

                    Response response = okhttp.newCall(request).execute();

                    if (response.code() == 200) {

                        JSONObject object = new JSONObject(response.body().string());

                        Log.i("funciono", object + "");
                        User user = new User();

                        user.setId(object.getJSONObject("data").getString("email"));
                        user.setName(object.getJSONObject("data").getString("email"));
                        user.setEmail(object.getJSONObject("data").getString("email"));
                        user.setAdress(object.getJSONObject("data").getString("email"));
                        user.setDepartment(object.getJSONObject("data").getString("email"));
                        user.setMunicipality(object.getJSONObject("data").getString("email"));
                        user.setBirth(object.getJSONObject("data").getString("email"));
                        user.setPhone(object.getJSONObject("data").getString("email"));
                        user.setNumber(object.getJSONObject("data").getString("email"));
                        /*localDataBase.saveUser(user);*/
                        LocalDataBase.getInstance(null).saveUser(user);
                        callback.onFinishProcess(true, null);
                    } else
                        callback.onFinishProcess(false, null);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

}
