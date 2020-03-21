package com.example.projet_laurin_marc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.projet_laurin_marc.R;
import com.example.projet_laurin_marc.database.entity.Person;

import java.util.List;

public class PersonListAdapter extends BaseAdapter {

    private List<Person> listData;
    private LayoutInflater layoutInflater;
    public PersonListAdapter(Context aContext, List<Person> listData) {
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
            v = layoutInflater.inflate(R.layout.list_row_ppl, null);
            holder = new ViewHolder();
            holder.uAHV = (TextView) v.findViewById(R.id.ahv);
            holder.uFirstname = (TextView) v.findViewById(R.id.firstname);
            holder.uLastname = (TextView) v.findViewById(R.id.lastname);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        /*holder.uAHV.setText("999-999-999");
        holder.uFirstname.setText("Laurin");
        holder.uLastname.setText("Weber");
        //System.out.println(listData.get(position).getAhv());*/

        holder.uAHV.setText(listData.get(position).getAhv());
        holder.uFirstname.setText(listData.get(position).getFirstname());
        holder.uLastname.setText(listData.get(position).getLastname());
        return v;
    }
    static class ViewHolder {
        TextView uAHV;
        TextView uFirstname;
        TextView uLastname;
    }
}

