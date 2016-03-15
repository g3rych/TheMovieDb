package com.example.error.themoviedb.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.error.themoviedb.R;

public class RecycleViewCursorAdapter extends RecyclerView.Adapter<RecycleViewCursorAdapter.ViewHolder> {
    private Cursor mCursor;
    private Context context;



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewTrailerName;
        public ImageView imgViewTrailerPoster;
        private String ytubeUrl;
        public ViewHolder(View v) {
            super(v);
            textViewTrailerName = (TextView) v.findViewById(R.id.text_view_trailer);
            imgViewTrailerPoster = (ImageView) v.findViewById(R.id.image_view_trailer);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("ADAPTER", getLayoutPosition() + "");
                    Intent intent = new Intent(Intent.ACTION_VIEW);

                    intent.setData(Uri.parse(ytubeUrl));
                    v.getContext().startActivity(intent);
                    Toast.makeText(v.getContext(),getLayoutPosition()+"",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    public RecycleViewCursorAdapter(Context c){
        context = c;
    }

    @Override
    public int getItemCount() {
        if (mCursor != null) {
            return mCursor.getCount();
        } else
            return 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_trailer_layout,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        String url = "http://img.youtube.com/vi/" +
                mCursor.getString(10) +
                "/hqdefault.jpg";
        holder.ytubeUrl = "http://www.youtube.com/watch?v=" +
                mCursor.getString(10);
        Glide.with(context).load(url).crossFade().into(holder.imgViewTrailerPoster);

        holder.textViewTrailerName.setText(mCursor.getString(9));

    }
    public void swapCursor(Cursor c) {
        mCursor = c;
        notifyDataSetChanged();
    }


}
