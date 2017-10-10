package com.manujindal.stichme;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by manu on 30/6/17.
 */


public class ListViewAdapterCascadedThree extends BaseAdapter
{
    Activity context;
    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> description = new ArrayList<>();
    ArrayList<String> description1 = new ArrayList<>();

    public ListViewAdapterCascadedThree(Activity context, ArrayList<String> title, ArrayList<String> description, ArrayList<String> description1) {
        super();
        this.context = context;
        this.title = title;
        this.description = description;
        this.description1 = description1;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return title.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    private class ViewHolder {
        TextView txtViewTitle;
        TextView txtViewDescription;
        TextView txtViewDescription1;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        // TODO Auto-generated method stub
        ViewHolder holder;
        LayoutInflater inflater =  context.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.item_three_details_cascaded, null);
            holder = new ViewHolder();
            holder.txtViewTitle = (TextView) convertView.findViewById(R.id.ItemType);
            holder.txtViewDescription = (TextView) convertView.findViewById(R.id.ItemOutput);
            holder.txtViewDescription1 = (TextView) convertView.findViewById(R.id.ItemOutput1);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtViewTitle.setText(title.get(position));
        holder.txtViewDescription.setText(description.get(position));
        holder.txtViewDescription1.setText(description1.get(position));

        return convertView;
    }

}