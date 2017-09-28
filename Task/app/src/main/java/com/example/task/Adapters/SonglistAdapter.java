package com.example.task.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.task.R;
import com.example.task.SongDetailActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pawan on 19/9/17.
 */

public class SonglistAdapter extends BaseAdapter {

    private final JSONArray songsList;
    private Context mContext;
    String url;

    public SonglistAdapter(Context context, JSONArray songsList){
        mContext = context;
        this.songsList = songsList;
    }

    @Override
    public int getCount() {
        return songsList.length();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ViewHolder holder;

        JSONObject currentsong = null;

        try {
            currentsong = new JSONObject(songsList.getJSONObject(position).toString());
        } catch (JSONException e) {
            Log.d("songsListAdapter","Exception in currentsong "+e.toString());
        }

        if (convertView == null){
            convertView = inflater.inflate(R.layout.songs_list,null);
            holder = new ViewHolder();

            holder.img = (ImageView)convertView.findViewById(R.id.listImage);

            holder.text_title = (TextView) convertView.findViewById(R.id.listTitle);
            holder.text_short_desc = (TextView) convertView.findViewById(R.id.listShortDescription);
            holder.text_long_desc = (TextView) convertView.findViewById(R.id.listlongDescription);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();

            try {
                url = currentsong.getString("artworkUrl100");
                Log.d("SongsListAdapter","Getting URL for image "+url);
                Picasso.with(mContext).load(url).into(holder.img);
                holder.text_title.setText((CharSequence) currentsong.getString("trackName"));
                holder.text_short_desc.setText((CharSequence) currentsong.getString("artistName"));
                holder.text_long_desc.setText((CharSequence) currentsong.getString("collectionName"));
            } catch (JSONException e) {
                Log.d("SongsListAdapter","Getting Exception while Loading Image Or Else "+e.toString());
            }
        }

        final JSONObject finalCurrentSong = currentsong;
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("SongData", String.valueOf(finalCurrentSong));
                Intent intent = new Intent(mContext, SongDetailActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private TextView text_title, text_short_desc, text_long_desc;
        private ImageView img;
    }
}
