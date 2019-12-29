package medicalpatient.login;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONObject;
import java.util.concurrent.TimeUnit;

import medicalpatient.model.LocalDataBase;
import medicalpatient.model.User;
import medicalpatient.utils.DefaultCallback;
import medicalpatient.utils.NetworkConstants;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class AgentLogin {

    private FirebaseAuth firebaseAuth;

    public AgentLogin(Context context) {
        firebaseAuth = FirebaseAuth.getInstance();
        LocalDataBase.getInstance(context);
    }

    public boolean isSingIn() {
        return LocalDataBase.getInstance(null).getUser() != null;//Log.i("Error: ",(LocalDataBase.getInstance(null).getUser() == null)+"");
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

                        System.out.println("QUE PASO"+ task.isSuccessful());
                        if (task.isSuccessful()) {
                            //saveUIDLogin();
                            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                @Override
                                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                    if(!task.isSuccessful()){
                                        Log.v("failed", task.getException()+"");
                                        callback.onFinishProcess(false,"fail process to logIn");
                                    }else{
                                        String token = task.getResult().getToken();
                                        getUserData(callback,token);
                                    }
                                }
                            });

                        } else
                            callback.onFinishProcess(false, null);
                    }
                });
            }
        }).start();
    }


    public void getUserData(final DefaultCallback callback, final String token) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    OkHttpClient okhttp = new OkHttpClient.Builder()
                            .connectTimeout(5, TimeUnit.SECONDS)
                            .readTimeout(5, TimeUnit.SECONDS)
                            .build();

                    RequestBody body = new FormBody.Builder()
                            .add("type", "0")
                            .add("token",token)
                            .add("id", firebaseAuth.getInstance().getCurrentUser().getUid() + "")
                            .build();

                    Request request = new Request.Builder()
                            .url(NetworkConstants.URL + NetworkConstants.PATH_PROFILE)
                            .post(body)
                            .build();

                    Response response = okhttp.newCall(request).execute();



                    if (response.code() == 200) {

                        JSONObject object = new JSONObject(response.body().string());

                        User patient = new User();
                        patient.setUID(object.getString("id"));
                        patient.setName(object.getString("nombre"));
                        patient.setId(object.getString("cedula"));
                        patient.setBirth(object.getString("fecha_nacimiento"));
                        patient.setAge(object.getString("edad"));
                        patient.setRisk(object.getString("riesgo"));
                        patient.setDiagnostic(object.getString("diagnostico"));
                        patient.setEmail(object.getString("email"));
                        patient.setMobile_number(object.getString("celular"));
                        patient.setWeight(object.getString("altura"));
                        patient.setHeight(object.getString("peso"));

                        try {
                            patient.setTelephone(object.getString("telefono"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        patient.setState(object.getString("departamento"));
                        patient.setCity(object.getString("ciudad"));
                        patient.setAddress(object.getString("direccion"));
                        patient.setRef(object.getString("ref"));

                        JSONObject inf_contact = object.getJSONObject("contacto");
                        patient.setName_contact(inf_contact.getString("nombre"));
                        patient.setTelephone_contact(inf_contact.getString("telefono"));
                        patient.setRelation(inf_contact.getString("parentesco"));


                        LocalDataBase.getInstance(null).saveUser(patient);

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
