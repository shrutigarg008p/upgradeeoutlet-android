package com.eoutlet.Eoutlet.utility;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Build;

import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.TextView;


import androidx.annotation.NonNull;

import com.eoutlet.Eoutlet.others.MySharedPreferenceClass;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    public static int count = 0;

    public static TextView view_final;

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }

        return isValid;
    }


    public static boolean isEmailValidContact(TextView textView, String error) {
        boolean isValid = true;
        String email = textView.getText().toString();

        /*if (email.length() <= 0) {
            textView.setError("Please enter Email Id!");
            return isValid;
        }*/


        if (email.length() > 0) {

            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            CharSequence inputStr = email;
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches()) {
                isValid = true;
            } else {
                textView.setError(error);
                view_final = textView;
                isValid = false;
            }
        }


        return isValid;
    }

    public static boolean isPasswordMatched(TextView textViewPassword, TextView textViewConfirmPassword, String error) {

        String password = textViewPassword.getText().toString();
        String confirmPassword = textViewConfirmPassword.getText().toString();
        boolean isValid = password.equals(confirmPassword);

        if (!isValid) {

            textViewConfirmPassword.setError(error);
            view_final =  textViewConfirmPassword;
        }

        return isValid;
    }

    public static boolean isPasswordLengthCorrect(TextView textViewPassword, String error) {

        String password = textViewPassword.getText().toString();
        boolean isValid = password != null && password.length() >= Constants.PASSWORD_MINIMUM_LENGTH;

        if (!isValid) {
            view_final = textViewPassword;
            textViewPassword.setError(error);
        }

        return isValid;
    }
    public static void preventTwoClick(final View view){
        view.setEnabled(false);
        view.postDelayed(new Runnable() {
            public void run() {
                view.setEnabled(true);
            }
        }, 4000);
    }

    public static boolean isEmailValid(TextView textView) {
        boolean isValid = true;
        String email = textView.getText().toString();

        /*if (email.length() <= 0) {
            textView.setError("Please enter Email Id!");
            return isValid;
        }*/


        if (email.length() > 0) {

            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            CharSequence inputStr = email;
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches()) {
                isValid = true;
            } else

            {

                textView.setError("الرجاء إدخال بريد إلكتروني صحيح");


                view_final = textView;
                isValid = false;
            }
        }


        return isValid;
    }

    public static boolean isEmailValid2(TextView textView) {
        boolean isValid = true;

        String email = textView.getText().toString();
        if (email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            CharSequence inputStr = email;

            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches()) {
                isValid = true;
            } else {
                textView.setError("الرجاء إدخال بريد إلكتروني صحيح");
                isValid = false;
            }
        }
        return isValid;
    }

    public static boolean checkTextViewValidation(TextView txtView, String error) {
        if (txtView.length() <= 0) {
            txtView.setError(error);

            view_final = txtView;
            return false;
        }

        return true;
    }

    public static boolean checkTextViewlengthValidation(TextView txtView, String error,int lenght) {
        if (txtView.length() <= 0) {
            txtView.setError(error);

            view_final = txtView;
            return false;
        }

        return true;
    }





    public static boolean checkTextViewCardVakidation(TextView txtView, String error) {
        if (txtView.length() <= 0) {
            txtView.setError(error);

            view_final = txtView;
            return false;
        }

        return true;
    }



    public static boolean checkMobileValidation(TextView txtView,int validlength, String error) {
        if (txtView.length() > validlength || txtView.length() < validlength) {
            txtView.setError(error);
            view_final = txtView;
            return false;
        }

        return true;
    }

    public static boolean checkCountryValidation(TextView tv, int id, String error) {
        if (id <= 0) {
            tv.setError(error);
            view_final = tv;
            return false;
        }

        return true;
    }


  /*  public static int containsCountryId(List<Country> list, int id) {
        for (int i = 0; i < list.size(); i++) {
            Country object = list.get(i);
            if (object.id == id) {
                return i;
            }
        }
        return 0;
    }*/

   /* public static int containsStateId(List<State> list, int id) {
        for (int i = 0; i < list.size(); i++) {
            State object = list.get(i);
            if (object.id == id) {
                return i;
            }
        }
        return 0;
    }

    public static int containsCityId(List<City> list, int id) {
        for (int i = 0; i < list.size(); i++) {
            City object = list.get(i);
            if (object.id == id) {
                return i;
            }
        }
        return 0;
    }

    public static int containsDoctorRegistrationId(List<MasterMci> list, int id) {
        for (int i = 0; i < list.size(); i++) {
            MasterMci object = list.get(i);
            if (object.id == id) {
                return i;
            }
        }
        return 0;
    }*/

    public static File saveImage(String path, Bitmap bitmap) {


        File tmpFile = new File(path);
        int file_size = Integer.parseInt(String.valueOf(tmpFile.length() / 1024));
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(tmpFile);

            if (fos != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        tmpFile = new File(path);
        file_size = Integer.parseInt(String.valueOf(tmpFile.length() / 1024));

        return tmpFile;
    }

    public static File downSizeImage(Bitmap bm, String path, Bitmap.CompressFormat compressFormat, int quality) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // downsizing image as it throws OutOfMemory Exception for larger
        // images
        File tmpFile = new File(path);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(tmpFile);

            if (fos != null) {

                bm.compress(compressFormat, quality, fos);
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int file_size = Integer.parseInt(String.valueOf(tmpFile.length() / 1024));
        return tmpFile;
    }



    /**
     * CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT
     */
    public static boolean checkConnection(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }



    /**
     * CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT
     */


    //This method will check whether the app is in background or not
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    @NonNull
    public static String getMimeType(@NonNull File file) {
        String type = null;
        final String url = file.toString();
        final String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.toLowerCase());
        }
        if (type == null) {
            type = "*/*"; // fallback type. You might set it to */*
        }
        return type;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}