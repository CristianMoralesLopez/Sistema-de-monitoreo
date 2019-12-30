package medicalpatient.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LocalDataBase {

    private static LocalDataBase INSTANCE;
    private SharedPreferences preferences;
    private User me;

    public LocalDataBase(Context context) {
        preferences = context.getSharedPreferences("MedicalPaciente", 0);
        loadUser();
    }

    public static LocalDataBase getInstance(Context context) {
        return INSTANCE == null ? (INSTANCE = new LocalDataBase(context)) : INSTANCE;
    }

    public User getUser() {
        return me;
    }

    private void loadUser() {
        if (me == null) {
            Gson gson = new GsonBuilder().create();
            me = gson.fromJson(preferences.getString("user", null), User.class);
        }
    }

    public void saveUser(User user) {

        Gson gson = new GsonBuilder().create();
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("user", gson.toJson(user, User.class));
        if (edit.commit())
            me = user;
    }


    public void deletedCredentials() {
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("user", null);
        if (edit.commit())
            me = null;
    }


}
