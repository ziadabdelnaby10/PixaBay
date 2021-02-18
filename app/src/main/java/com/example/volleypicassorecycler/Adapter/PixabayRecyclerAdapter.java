package com.example.volleypicassorecycler.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.volleypicassorecycler.Data.ImagesModel;
import com.example.volleypicassorecycler.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PixabayRecyclerAdapter extends RecyclerView.Adapter<PixabayRecyclerAdapter.PixabayViewHolder> {

    public interface onItemClickListener {
        void onItemClick(int pos);
        void onItemLongClick(int pos);
    }

    private ArrayList<ImagesModel> info;


    private onItemClickListener mlistener;

    public void setOnItemClickListener(onItemClickListener listener) {
        mlistener = listener;
    }


    public void setInfoList(ArrayList<ImagesModel> info) {
        this.info = info;
    }

    @NonNull
    @Override
    public PixabayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_items, parent, false);
        return new PixabayViewHolder(view, mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull PixabayViewHolder holder, int position) {

        holder.name.setText( info.get(position).userName );

        holder.like.setText( String.valueOf(info.get(position).numLike) );

        holder.views.setText(String.valueOf(info.get(position).numView));

        holder.favour.setText(String.valueOf(info.get(position).numFav));


        if(!info.get(position).userImageUrl.isEmpty())
            Picasso.get().load(info.get(position).userImageUrl).placeholder(R.drawable.loading_foreground).fit().centerInside().into(holder.userImage);
        else
            Picasso.get().load(R.drawable.loading_foreground).fit().centerInside().into(holder.userImage);

        if(!info.get(position).resultImageUrl.isEmpty())
            Picasso.get().load(info.get(position).resultImageUrl).placeholder(R.drawable.loading_foreground).fit().centerInside().into(holder.resultImage);
        else
            Picasso.get().load(R.drawable.loading_foreground).fit().centerInside().into(holder.resultImage);
    }

    @Override
    public int getItemCount() {
        return info.size();
    }

    public static class PixabayViewHolder extends RecyclerView.ViewHolder {

        CircleImageView userImage;
        ImageView resultImage;
        TextView name , like , views , favour;

        public PixabayViewHolder(@NonNull View itemView, onItemClickListener listener) {
            super(itemView);

            userImage = itemView.findViewById(R.id.user_image);
            resultImage = itemView.findViewById(R.id.resultImage);
            name = itemView.findViewById(R.id.txt_user_name_final);
            like = itemView.findViewById(R.id.txt_like_final);
            views = itemView.findViewById(R.id.txt_view_final);
            favour = itemView.findViewById(R.id.txt_favourite_final);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener  != null)
                    {
                        if(getAdapterPosition() != RecyclerView.NO_POSITION)
                            listener.onItemClick(getAdapterPosition());
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(listener  != null)
                    {
                        if(getAdapterPosition() != RecyclerView.NO_POSITION)
                            listener.onItemLongClick(getAdapterPosition());
                    }
                    return true;
                }
            });
        }
    }
}