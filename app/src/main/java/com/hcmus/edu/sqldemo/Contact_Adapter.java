package com.hcmus.edu.sqldemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Contact_Adapter extends ArrayAdapter<Contact> {
    Context context;
    int res;
    ArrayList<Contact> data;

    public Contact_Adapter(Context context, int resource, ArrayList<Contact> objects) {
        super(context, resource, objects);

        this.context = context;
        this.res = resource;
        this.data = objects;
    }

    public static class ViewHolder {
        TextView tvId, tvName, tvPhone;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contact contact = data.get(position);
        ViewHolder holder;

        if(convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(res, null);
            holder.tvId = convertView.findViewById(R.id.tvId);
            holder.tvName = convertView.findViewById(R.id.tvName);
            holder.tvPhone = convertView.findViewById(R.id.tvPhone);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvId.setText(contact.getId());
        holder.tvName.setText(contact.getName());
        holder.tvPhone.setText(contact.getPhone());

        return convertView;
    }

    @Override
    public int getCount() {
        return data.size();
    }
}
