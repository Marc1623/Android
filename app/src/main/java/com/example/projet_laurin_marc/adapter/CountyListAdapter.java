package com.example.projet_laurin_marc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.projet_laurin_marc.R;
import com.example.projet_laurin_marc.static_database.County;

import java.util.List;

public class CountyListAdapter extends BaseAdapter {

    private List<County> listData;
    private LayoutInflater layoutInflater;
    public CountyListAdapter(Context aContext, List<County> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }
    @Override
    public int getCount() {
        return listData.size();
    }
    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View v, ViewGroup vg) {
        ViewHolder holder;
        if (v == null) {
            v = layoutInflater.inflate(R.layout.list_row, null);
            holder = new ViewHolder();
            holder.uName = (TextView) v.findViewById(R.id.name);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.uName.setText(listData.get(position).getCounty());
        return v;
    }
    static class ViewHolder {
        TextView uName;
    }
}

