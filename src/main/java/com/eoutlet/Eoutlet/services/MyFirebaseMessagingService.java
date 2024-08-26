/**
 * Copyright 4016 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 4.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-4.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.eoutlet.Eoutlet.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.eoutlet.Eoutlet.R;
import com.eoutlet.Eoutlet.activities.MainActivity;
import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;
import com.eoutlet.Eoutlet.utility.Constants;
import com.eoutlet.Eoutlet.utility.Util;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.moengage.firebase.MoEFireBaseHelper;
import com.moengage.pushbase.MoEPushHelper;


import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    public static Intent intent = null;
    String CHANNEL_ID = "my_channel_01";// The id of the channel.
    CharSequence name = "Eoutlet Notifications";
    Bitmap image;

   public static String id = " ",catagoryname=" ",children = " ";








    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //if(ChatDetail.active){

        if (MoEPushHelper.getInstance().isFromMoEngagePlatform(remoteMessage.getData())) {


            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    if(remoteMessage.getData().get("id")!=null && remoteMessage.getData().get("name")!=null && remoteMessage.getData().get("page") !=null  ) {
                       // Log.e("remoteMessage id", remoteMessage.getData().get("id"));
                        //Log.e("remoteMessage name", remoteMessage.getData().get("name"));
                        //Log.e("remoteMessage page", remoteMessage.getData().get("page"));
                        MySharedPreferenceClass.setDeeplinkingNotification(getApplicationContext(), "true");
                        MySharedPreferenceClass.setDeeplinkingId(getApplicationContext(), remoteMessage.getData().get("id"));
                        MySharedPreferenceClass.setDeeplinkingName(getApplicationContext(), remoteMessage.getData().get("name"));
                        MySharedPreferenceClass.setDeeplinkingPage(getApplicationContext(), remoteMessage.getData().get("page"));
//                    Toast.makeText(getApplicationContext(), remoteMessage.getData().get("Product_Id"), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            MoEFireBaseHelper.Companion.getInstance().passPushPayload(getApplicationContext(), remoteMessage.getData());
        } else {
            Log.d("msg", "onMessageReceived: " + remoteMessage.getData().get("message"));
            Map<String, String> data = null;
            if (remoteMessage.getData().size() > 0) {
                Log.d(TAG, "Message data payload: " + remoteMessage.getData());

                JSONObject object = new JSONObject(remoteMessage.getData());

                Log.e("JSON_OBJECT", object.toString());


                data = remoteMessage.getData();
                try {

                    id = object.optString("id");

                    catagoryname = object.optString("name");

                    children = object.optString("children");


                } catch (Exception e) {


                }
            }


            if (remoteMessage.getNotification().getImageUrl() != null) {
                try {
                    Log.d(TAG, "Message notification payload: " + remoteMessage.getNotification().getImageUrl().toString());


                    URL url = new URL(remoteMessage.getNotification().getImageUrl().toString());
                    image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
            sendNotification(data, remoteMessage, image);
        }
    }

    void sendNotification(Map<String, String> data, RemoteMessage remoteMessage, Bitmap image) {


        if (Util.isAppIsInBackground(getApplicationContext()))//show notification
        {




             NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            int notificationId = 0;// new Random().nextInt(); // just use a counter in some util class...

            notificationManager.cancel(notificationId);

            intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            //  intent.putExtra(Constants.DOCTOR_ID,doctorId);
            //intent.putExtra(Constants.PATIENT_ID,patientId);
            //intent.putExtra(Constants.BOOKING_ID,bookingId);

            //intent.putExtra(NotificationActivity.NOTIFICATION_ID,notificationId);


            //   PendingIntent dismissIntent = NotificationActivity.getDismissIntent(notificationId, getBaseContext());

            NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle()
                    .bigPicture(image);


            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            String channelId = "Default";
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.icon_new2).setStyle(style)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody()).setAutoCancel(true).setContentIntent(pendingIntent);
            ;
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
              // NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);

                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID,
                        name,
                        NotificationManager.IMPORTANCE_HIGH);

                manager.createNotificationChannel(mChannel);
            }

            manager.notify(0, builder.build());
        } else {


            intent = new Intent(this, MainActivity.class);


            int notificationId = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);


            //  intent.putExtra("subtype", "44");
            //  intent.putExtra("id", "Message History");
            // intent.putExtra("type", type);


            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);


            PendingIntent pendingIntent = PendingIntent.getActivity(this, notificationId /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle()
                    .bigPicture(image);

            final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setPriority(NotificationCompat.PRIORITY_MAX) //HIGH, MAX, FULL_SCREEN and setDefaults(Notification.DEFAULT_ALL) will make it a Heads Up Display Style
                    .setDefaults(android.app.Notification.DEFAULT_LIGHTS | android.app.Notification.DEFAULT_VIBRATE) // also requires VIBRATE permission
                    .setSmallIcon(R.drawable.icon_new)
                    .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_new))
                    .setContentTitle(remoteMessage.getNotification().getTitle()).setChannelId(CHANNEL_ID)
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setAutoCancel(true)
                    .setStyle(style).setContentIntent(pendingIntent);

                        /*.setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(data.get("value")))
                        .setSound(defaultSoundUri)*/
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID,
                        name,
                        NotificationManager.IMPORTANCE_HIGH);

                notificationManager.createNotificationChannel(mChannel);
            }
            android.app.Notification notification = notificationBuilder.build();
            notificationManager.notify(notificationId, notification);


            //  LocalBroadcastManager.getInstance(this).sendBroadcast(intent);


        }

    }


}









































