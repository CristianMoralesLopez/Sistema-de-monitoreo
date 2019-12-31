package medicalpatient.gms;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.cristian.sistemademonitoreo.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.concurrent.TimeUnit;

import medicalpatient.login.SplashScreenActivity;
import medicalpatient.model.LocalDataBase;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import medicalpatient.utils.NetworkConstants;


/**
 * Created by camilo on 2/16/18.
 */

public class MessagingReceiver extends FirebaseMessagingService {

    // This is the Notification Channel ID. More about this in the next section
    public static final String NOTIFICATION_CHANNEL_ID = "channel_id";
    //User visible Channel Name
    public static final String CHANNEL_NAME = "Notification Channel";
    // Importance applicable to all the notifications in this Channel
    int importance = NotificationManager.IMPORTANCE_DEFAULT;

    //Request Advisories
    public static final int NOTIFICATION_ID_1 = 1;

    //Started Advisories
    public static final int NOTIFICATION_ID_2 = 2;

    //Send Message to chat
    public static final int NOTIFICATION_ID_3 = 3;

    public static final int NOTIFICATION_ID_4 = 4;

    public static final int NOTIFICATION_ID_5 = 5;

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        if (LocalDataBase.getInstance(this).getUser() != null)
            try {

                OkHttpClient okhttp = new OkHttpClient.Builder()
                        .connectTimeout(5, TimeUnit.SECONDS)
                        .readTimeout(5, TimeUnit.SECONDS)
                        .build();

                RequestBody body = new FormBody.Builder()
                        .add("type", "1")
                        .add("token", s)
                        .add("id", LocalDataBase.getInstance(this).getUser().getUID())
                        .build();


                Request request = new Request.Builder()
                        .url(NetworkConstants.URL + NetworkConstants.PATH_UPDATE_TOKEN)
                        .post(body)
                        .build();

                Response response = okhttp.newCall(request).execute();

                if (response.code() == 200) {
                    Log.i("token", s);
                    Log.i("success", "Update token");
                } else
                    Log.e("failed", "Failed update token");

            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        createNotificationChannel();

        Log.i("ENTRY_PUSH_NOTIFICATION", "TRUE, " + remoteMessage.toString());
        if (LocalDataBase.getInstance(this).getUser() != null)
            try {
//                JSONObject data = new JSONObject(remoteMessage.getData());
                Log.i("Notification_receiver", remoteMessage.getData() + "");
//                int type = Integer.parseInt(data.getString("nType"));

                String title = "primer notificacion";
                String info = "hola, esta es mi primer notificacion";
                String content = "no tiene contenido pero es mi primera notificacion";

                createNotificationChannel();

                Intent intent = new Intent(getApplicationContext(), SplashScreenActivity.class);
                createNotification(title, info, content, intent);


//                createNotification(title, info, content, intent);
//                Advisory advisory = new Advisory();
//                switch (type) {
//                    case NOTIFICATION_ID_1:
//                        title = data.getString("title");
//                        content = data.getString("body");
//                        info = "Toca aqui para ver todos los detalles";
//
//                        Calendar c = Calendar.getInstance();
//
//                        if (data.getBoolean("scheduled")) {
//                            String split[] = data.getString("scheduledDate").split(" ");
//                            String dateString[] = split[0].split("-");
//                            String hourString[] = split[1].split(":");
//
//                            c.set(Integer.parseInt(dateString[0]), Integer.parseInt(dateString[1]), Integer.parseInt(dateString[2]),
//                                    Integer.parseInt(hourString[0]), Integer.parseInt(hourString[1]), 0);
//                        }
//
//                        advisory.isOnline = data.getInt("type") == 1;
//                        advisory.nameAdviser = data.getString("advisor");
//                        advisory.day = c.get(Calendar.DAY_OF_MONTH);
//                        advisory.month = c.get(Calendar.MONTH);
//                        advisory.year = c.get(Calendar.YEAR);
//                        advisory.hour = c.get(Calendar.HOUR_OF_DAY);
//                        advisory.minute = c.get(Calendar.MINUTE);
//                        advisory.reason = data.getString("reason");
//                        advisory.rate = Double.parseDouble(data.getString("advisorRating"));
//                        advisory.id = data.getString("requestId");
//
//                        if (advisory.isOnline && !SearchAdviserActivity.isAlive) {
//
//                            advisory.chatId = data.getString("chatId");
//                            advisory.idAdviser = data.getString("advisorId");
//
//                            intent = amamantapp.advisoryOnline.ResponseActivity.init(this, advisory, true);
//
//                            createNotification(title, info, content, intent, type);
//
//                        } else if (!amamantapp.advisoryHome.SearchAdviserActivity.isAlive) {
//
//                            intent = ResponseActivity.init(this, advisory, true);
//                            intent.setAction("advisory_receiver_home");
//
//                            LocalBroadcastManager.getInstance(this).sendBroadcastSync(intent);
//                        createNotification(title, info, content, intent, type);
//                        }
//                        break;
//                    case NOTIFICATION_ID_2:
//                        title = data.getString("title");
//                        content = data.getString("body");
//                        info = "Toca aqui para ver todos los detalles";
//
//                        Advisory onCourse = new Advisory();
//
//                        onCourse.chatId = data.getString("chatId");
//                        onCourse.reason = data.getString("reason");
//                        onCourse.id = data.getString("advisoryId");
//                        onCourse.idAdviser = data.getString("advisorId");
//                        onCourse.isOnline = data.getInt("type") == 1;
//                        onCourse.isSchedule = data.getBoolean("scheduled");
//
//                        c = Calendar.getInstance();
//
//                        if (onCourse.isSchedule) {
//
//                            String schedule = data.getString("scheduledDate");
//
//                            String split[] = schedule.split(" ");
//                            String dateString[] = split[0].split("-");
//                            String hourString[] = split[1].split(":");
//
//                            c.set(Integer.parseInt(dateString[0]), Integer.parseInt(dateString[1]), Integer.parseInt(dateString[2]),
//                                    Integer.parseInt(hourString[0]), Integer.parseInt(hourString[1]), 0);
//                        }
//
//                        onCourse.day = c.get(Calendar.DAY_OF_MONTH);
//                        onCourse.month = c.get(Calendar.MONTH);
//                        onCourse.year = c.get(Calendar.YEAR);
//                        onCourse.hour = c.get(Calendar.HOUR_OF_DAY);
//                        onCourse.minute = c.get(Calendar.MINUTE);
//
//                        if (onCourse.isOnline)
//                            intent = new Intent(getApplicationContext(), AdvisoryActivity.class);
//                        else
//                            intent = ChatActivity.init(getApplicationContext(), true);
//
//                        //Save Advisory OnCourse
//                        LocalDataBase.getInstance(null).saveAdvisoryOnCourse(onCourse);
//                        Log.i("Success_save_advisory", onCourse.id + "");
//                        break;
//                    case NOTIFICATION_ID_3:
//                        advisory.id = data.getString("advisoryId");
//                        advisory.chatId = data.getString("chatId");
//                        advisory.idAdviser = data.getString("idFrom");
//                        advisory.nameAdviser = data.getString("userFrom");
//
//                        title = "Tienes un nuevo mensaje";
//                        content = data.getString("body");
//                        info = "Toca aqui para ver los mensajes";
//
//                        Message message = new Message();
//                        message.type = Message.RECEIVED;
//                        message.text = content;
//                        message.timestamp = data.getLong("timestamp");
//                        advisory.receivingMessage = message;
//
//                        intent = ChatActivity.init(getApplicationContext(), true);
//                        createNotification(title, info, content, intent);
//
//                        break;
//                    case NOTIFICATION_ID_4:
//                        break;
//                    case NOTIFICATION_ID_5:
//                        break;
//                }


            } catch (Exception e) {
                e.printStackTrace();
            }


    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = CHANNEL_NAME;
            String description = "descripcion del canal";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    private void createNotification(String title, String info, String content, Intent myintent) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(info)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


//
//
//        // Intent
//        myintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        myintent.putExtra("notification", true);
//
//        // Pending intent
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 8,
//                myintent, PendingIntent.FLAG_CANCEL_CURRENT);
//
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setAutoCancel(true)
//                .setContentTitle(title)
//                .setSubText("Estamos contigo")
//                .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
//                .setContentTitle(title)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setContentText(info);
//
//
//        mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
//        mBuilder.setPriority(Notification.PRIORITY_HIGH);
//
//
//        mBuilder.setContentIntent(contentIntent);

        NotificationManager mNotificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(0, builder.build());

    }
}
