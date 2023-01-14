package com.pradipto.graffiti;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pradipto.graffiti.models.PhotoModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {

    Context context;
    ArrayList<PhotoModel> wallpapersRv;

    public RvAdapter(Context context, ArrayList<PhotoModel> wallpapersRv) {
        this.context = context;
        this.wallpapersRv = wallpapersRv;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_wallpaper, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Picasso.get().load(wallpapersRv.get(position).getSrc().getPortrait()).placeholder(R.drawable.ic_placeholder).into(holder.wallpaper);
        holder.wallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, WallpaperActivity.class);
                intent.putExtra("original", wallpapersRv.get(position).getSrc().getOriginal());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wallpapersRv.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView wallpaper;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wallpaper = itemView.findViewById(R.id.idImage);
        }
    }
}
