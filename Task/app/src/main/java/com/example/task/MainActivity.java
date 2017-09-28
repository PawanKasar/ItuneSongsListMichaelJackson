package com.example.task;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.task.Adapters.SonglistAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    AsyncHttpClient asyncHttpClient;
    ListView lv_songslist;
    ArrayList<String> songs = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv_songslist = (ListView)findViewById(R.id.lv_songslist);
        getAllSongs();
    }

    public void getAllSongs(){
        asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get(getApplicationContext(),"https://itunes.apple.com/search?term=Michael+jackson",new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("MainActivity", "data--> " + response);
                try {
                        final JSONArray notificationList = response.getJSONArray("results");
                        SonglistAdapter adapter = new SonglistAdapter(getApplicationContext(), notificationList);
                        lv_songslist.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("MainActivity", "body "+responseString);
            }
        });
    }
}
