package com.example.korealong.qrcodescanner.Adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.korealong.qrcodescanner.Detail;
import com.example.korealong.qrcodescanner.ItemClickListener;
import com.example.korealong.qrcodescanner.R;
import com.example.korealong.qrcodescanner.Sqlite.UserCard;

import java.util.List;

class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

    public TextView txtNameCard, txtNameStore;
    public ImageView imgView;
    private ItemClickListener itemClickListener;

    public SearchViewHolder(View itemView) {
        super(itemView);
        txtNameCard = itemView.findViewById(R.id.txtNameCard);
        txtNameStore = itemView.findViewById(R.id.txtNameStore);
        imgView = itemView.findViewById(R.id.imgAVT);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(),true);
        return false;
    }
}
public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder>{
    private Context context;
    private List<UserCard> userCard;
    public SearchAdapter(Context context,List<UserCard> cards){
        this.context = context;
        this.userCard = cards;
    }
    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.listview_row,parent,false);
        return new SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.txtNameCard.setText(userCard.get(position).getNameCard());
        holder.txtNameStore.setText(userCard.get(position).getNameStore());

        byte[] id = userCard.get(position).getImage();
        holder.imgView.setImageBitmap(BitmapFactory.decodeByteArray(id,0,id.length));


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick == false)
                {
                    Intent intent = new Intent(context,Detail.class);
                    intent.putExtra("ID",userCard.get(position).getId());
                    context.startActivity(intent);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return userCard.size();
    }
}
