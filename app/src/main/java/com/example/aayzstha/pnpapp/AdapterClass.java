package com.example.aayzstha.pnpapp;

/**
 * Created by Aayz Stha on 11/9/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterClass extends BaseAdapter {
    private LayoutInflater inflate1;
    private ArrayList<ProductModule> list = new ArrayList<>();

    public AdapterClass(Context context, ArrayList<ProductModule> list1) {
        Context c = context;
        this.list = list1;
        inflate1 = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup Parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = inflate1.inflate(R.layout.spec_address, null);
            holder.CUSTID = (TextView) convertView.findViewById(R.id.idd);
            holder.CUSTTNAME = (TextView) convertView.findViewById(R.id.name);
            holder.CUSTPHONE= (TextView) convertView.findViewById(R.id.phone_no);
            holder.STREET= (TextView) convertView.findViewById(R.id.street);
            holder.CITY= (TextView) convertView.findViewById(R.id.city_name);
            holder.DIST= (TextView) convertView.findViewById(R.id.district_name);
            holder.ZONE= (TextView) convertView.findViewById(R.id.zone_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.CUSTID.setText(String.valueOf(list.get(position).getPid()));
        holder.CUSTTNAME.setText(list.get(position).getPname());
        holder.CUSTPHONE.setText(list.get(position).getPcontact());
        holder.STREET.setText(list.get(position).getPstreet());
        holder.CITY.setText(list.get(position).getPcity());
        holder.DIST.setText(list.get(position).getPdistrict());
        holder.ZONE.setText(list.get(position).getPzone());

        return convertView;
    }

    public static class ViewHolder {
        TextView CUSTID, CUSTTNAME, CUSTPHONE, STREET, CITY, DIST, ZONE;
    }
}
