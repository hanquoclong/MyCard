package com.example.korealong.qrcodescanner.Adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.korealong.qrcodescanner.R;
import com.example.korealong.qrcodescanner.Sqlite.UserCard;

import java.util.List;

class SearchViewHolder extends RecyclerView.ViewHolder{

    public TextView txtNameCard, txtNameStore;

    public SearchViewHolder(View itemView) {
        super(itemView);
        txtNameCard = itemView.findViewById(R.id.txtNameCard);
        txtNameStore = itemView.findViewById(R.id.txtNameStore);
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
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
