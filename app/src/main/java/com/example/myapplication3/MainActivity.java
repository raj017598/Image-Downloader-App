package com.example.myapplication3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends Activity implements View.OnClickListener{

    private EditText imageURLEditText;
    private ImageView downloadImageView;
    private Button downloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageURLEditText = findViewById(R.id.imageURLEditText);
        downloadImageView = findViewById(R.id.downloadImageView);
        downloadButton = findViewById(R.id.downloadButton);

        downloadButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(R.id.downloadButton == id)
        {
            String imageURL = imageURLEditText.getText().toString();
            if(!imageURL.isEmpty())
            {
                new DownloadImageAsyckTask(this,downloadImageView).execute(imageURL);
            }
            else
            {
                imageURLEditText.setError("Paste URL");
                imageURLEditText.requestFocus();
            }

        }
    }
}
