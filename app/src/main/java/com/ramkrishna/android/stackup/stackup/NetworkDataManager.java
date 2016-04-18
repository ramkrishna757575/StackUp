package com.ramkrishna.android.stackup.stackup;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ramkr on 17-Apr-16.
 *
 * Manages the Network Operations like fetching raw data and bitmaps.
 */
public class NetworkDataManager {
    private String url;
    private URL _url;
    private Context context;
    private boolean isNetworkInitComplete;
    private String rawData;
    private Bitmap bitmap;

    //Constructor to initialize variables
    NetworkDataManager(Context context) {
        this.context = context;
        isNetworkInitComplete = false;
        rawData = null;
        bitmap = null;
        _url = null;
    }

    //Sets the URL that will be used to fetch data from
    public void setUrl(String url) {
        this.url = url;
    }

    //Initializes the Newtwork and returns the status of network initialization
    public boolean initNetwork() {
        if (checkNetwork()) {
            if (constructURL()) {
                isNetworkInitComplete = true;
                return true;
            }
        }
        return false;
    }

    //Checks if Network is available or not
    private boolean checkNetwork() {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            Toast toast = Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
    }

    //Forms the URL from the string containing url
    private boolean constructURL() {
        try {
            _url = new URL(url);
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(context, "Incorrect Url", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
    }

    //Fetches string data(JSON) from the API
    public void fetchRawString() {
        String webPage = "", data = "";
        if (isNetworkInitComplete) {
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) _url.openConnection();
                InputStream is = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                while ((data = reader.readLine()) != null) {
                    webPage += data + "\n";
                }
                urlConnection.disconnect();
                is.close();
                rawData = webPage;
            } catch (IOException e) {
                e.printStackTrace();
                Toast toast = Toast.makeText(context, "Unable to fetch data", Toast.LENGTH_SHORT);
                toast.show();
                rawData = null;
            }
        }
    }

    //Fetches Bitmap form the API
    public void fetchBitmapData() {
        if (isNetworkInitComplete) {
            try {
                Bitmap tempBitmap = BitmapFactory.decodeStream((InputStream) _url.getContent());
                bitmap = tempBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                Toast toast = Toast.makeText(context, "Unable to fetch image", Toast.LENGTH_SHORT);
                toast.show();
                bitmap = null;
            }
        }
    }

    //Returns the raw string data obtained from the API
    public String getRawData() {
        return rawData;
    }

    //Returns the Bitmap obtained from the API
    public Bitmap getBitmap() {
        return bitmap;
    }
}

