package com.example.korealong.qrcodescanner;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

public class ListViewAdapter extends BaseAdapter {
    Activity activity;
    List<User> lstUser;
    LayoutInflater inflater;

    public ListViewAdapter(Activity activity, List<User> lstUser) {
        this.activity = activity;
        this.lstUser = lstUser;
    }

    @Override
    public int getCount() {
        return lstUser.size();
    }

    @Override
    public Object getItem(int position) {
        return lstUser.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater)activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemview = inflater.inflate(R.layout.listview_item,null);
        TextView txtName = (TextView)itemview.findViewById(R.id.listName);
        TextView txtNumber = (TextView)itemview.findViewById(R.id.listNumber);

        txtName.setText(lstUser.get(position).getName());
        txtNumber.setText(lstUser.get(position).getNumber());

        return itemview;
    }
}
