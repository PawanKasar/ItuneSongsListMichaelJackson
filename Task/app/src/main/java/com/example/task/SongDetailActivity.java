package com.example.task;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class SongDetailActivity extends AppCompatActivity {

    ImageView iv_image;
    TextView tv_title,tv_gener,tv_release;

    Bundle bundle;
    JSONObject songDetailObj,currentSongObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_detail);
        bundle = getIntent().getExtras();
        Log.d("SongDetailActivity", "" + bundle.getString("SongData"));

        if (bundle != null){
            try{
                songDetailObj = new JSONObject(bundle.getString("SongData"));
                Log.d("SongDetailActivity","getting JSONObject in Bundle "+songDetailObj.toString());
            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        iv_image = (ImageView)findViewById(R.id.iv_detail);
        tv_title = (TextView)findViewById(R.id.tv_songdetail);
        tv_gener = (TextView)findViewById(R.id.tv_songgener);
        tv_release = (TextView)findViewById(R.id.tv_songreleaseyear);

        try{
            currentSongObj = new JSONObject(bundle.getString("SongData"));

            tv_title.setText(currentSongObj.getString("trackName"));
            tv_gener.setText(currentSongObj.getString("artistName"));
            tv_release.setText(currentSongObj.getString("releaseDate"));
            Picasso.with(getApplicationContext()).load(currentSongObj.getString("artworkUrl100")).into(iv_image);
        }catch (JSONException ex){
            ex.printStackTrace();
        }
    }
}
