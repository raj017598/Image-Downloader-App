package com.example.myapplication3;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.PrecomputedText;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadImageAsyckTask extends AsyncTask<String,Void, Bitmap> {

    private final static String TAG = MainActivity.class.getName();
    private ImageView imageView;
    private ProgressDialog progressDialog;
    DownloadImageAsyckTask(Context context,ImageView  imageView)
    {
        this.imageView = imageView;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Downloading...");
        progressDialog.setMessage("Downloading Image...Please Wait");
        progressDialog.setIndeterminate(true);
    };
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        if(progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }
        return downloadFromWebServer(strings[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        imageView.setImageBitmap(bitmap);
    }

    private Bitmap downloadFromWebServer(String imageURL) {

        try {
            URL imageWebURL = new URL(imageURL);
            HttpURLConnection connection = (HttpURLConnection)imageWebURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.setReadTimeout(15000);
            connection.setDoInput(true);
            connection.connect();

            int responseCode = connection.getResponseCode();
            if(HttpURLConnection.HTTP_OK == responseCode)
            {
                InputStream inputStream = connection.getInputStream();
                if(null != inputStream)
                {
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                    return bitmap;
                }
            }
        }
        catch (MalformedURLException e)
        {
            Log.e(TAG,"Error: Incorrect URL"+e.getLocalizedMessage());
        }
        catch (IOException ex)
        {
            Log.e(TAG,"Error: Error To Open"+ex.getLocalizedMessage());
        }

        return  null;
    }
}
