package com.example.korealong.qrcodescanner.Sqlite;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.korealong.qrcodescanner.R;

import java.util.ArrayList;

public class AdapterCard extends BaseAdapter {
    Context context;
    ArrayList<UserCard> listUserCard;

    public AdapterCard(Activity context, ArrayList<UserCard> listUserCard) {
        this.context = context;
        this.listUserCard = listUserCard;
    }

    @Override
    public int getCount() {
        return listUserCard.size();
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.listview_row,null);
        ImageView imgAVT = row.findViewById(R.id.imgAVT);
        //TextView txtId = row.findViewById(R.id.txtId);
        TextView txtNameCard = row.findViewById(R.id.txtNameCard);
        TextView txtNameStore = row.findViewById(R.id.txtNameStore);
//        Button btnEdit = row.findViewById(R.id.btnEdit);
//        Button btnDel = row.findViewById(R.id.btnDel);

        final UserCard userCard = listUserCard.get(position);
        //txtId.setText(userCard.id +"");
        txtNameCard.setText(userCard.nameCard);
        txtNameStore.setText(userCard.nameStore);

        Bitmap bmAVT = BitmapFactory.decodeByteArray(userCard.image,0,userCard.image.length);
        imgAVT.setImageBitmap(bmAVT);

        return row;
    }
}
