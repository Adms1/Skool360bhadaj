package com.anandniketanbhadaj.skool360.skool360.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
}
