package com.anandniketanbhadaj.skool360.skool360.Utility;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.view.Window;
import android.widget.Toast;

import com.anandniketanbhadaj.skool360.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

/**
 * Created by Harsh on 04-Aug-16.
 */
public class Utility {
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static SharedPreferences sharedpreferences;
    private static final int  MEGABYTE = 1024 * 1024;
    public static String parentFolderName = "Skool 360 Shilaj";
    public static String childAnnouncementFolderName = "Announcement";
    public static String childCircularFolderName = "Circular";
    public static Dialog dialog;
    public static AVLoadingIndicatorView avi;

    public static boolean isNetworkConnected(Context ctxt) {
        ConnectivityManager cm = (ConnectivityManager) ctxt
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }

    public static void ping(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void pong(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void setPref(Context context, String key, String value){
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getPref(Context context, String key){
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String value = sharedpreferences.getString(key, "");
        return value;
    }

    public static boolean isFileExists(String fileName, String moduleName){
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        if(moduleName.equalsIgnoreCase("announcement"))
            return new File(extStorageDirectory, parentFolderName+"/"+ childAnnouncementFolderName +"/"+fileName).isFile();
        else
            return new File(extStorageDirectory, parentFolderName+"/"+ childCircularFolderName +"/"+fileName).isFile();
    }

    public static File createFile(String fileName, String moduleName){
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File folder = null;

        if(moduleName.equalsIgnoreCase("announcement"))
            folder = new File(extStorageDirectory, parentFolderName+"/"+childAnnouncementFolderName);
        else
            folder = new File(extStorageDirectory, parentFolderName+"/"+childCircularFolderName);

        folder.mkdirs();

        File pdfFile = new File(folder, fileName);

        try{
            pdfFile.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        return pdfFile;
    }

    public static void downloadFile(String fileUrl, String fileName, String moduleName){
        try {

            File directoryPath = createFile(fileName, moduleName);

            fileUrl = fileUrl.replace(" ", "%20");
            URL url = new URL(fileUrl);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(directoryPath);
            int totalSize = urlConnection.getContentLength();

            byte[] buffer = new byte[MEGABYTE];
            int bufferLength = 0;
            while((bufferLength = inputStream.read(buffer)) > 0 ){
                fileOutputStream.write(buffer, 0, bufferLength);
            }
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getTodaysDate() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH) + 1;
        int dd = calendar.get(Calendar.DAY_OF_MONTH);


        String mDAY, mMONTH, mYEAR;

        mDAY = Integer.toString(dd);
        mMONTH = Integer.toString(mm);
        mYEAR = Integer.toString(yy);

        if (dd < 10) {
            mDAY = "0" + mDAY;
        }
        if (mm < 10) {
            mMONTH = "0" + mMONTH;
        }

        return mDAY + "/" + mMONTH + "/" + mYEAR;
    }

    public static void showDialog(Context context) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.progressbar_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        avi=(AVLoadingIndicatorView)dialog.findViewById(R.id.avi) ;
      avi.show();
        dialog.show();
    }

    public static void dismissDialog() {
        if (dialog != null && dialog.isShowing())
            try {
            avi.hide();
                dialog.dismiss();
            } catch (final IllegalArgumentException e) {
                // Do nothing.
            } catch (final Exception e) {
                // Do nothing.
            } finally {
                dialog = null;
            }
    }
}
