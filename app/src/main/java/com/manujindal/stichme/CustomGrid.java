package com.manujindal.stichme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by manu on 29/6/17.
 */

public class CustomGrid extends BaseAdapter {
    private Context mContext;
    private final String[] ItemType1;
    private final String[] ItemOutput1;

    public CustomGrid(Context c,String[] ItemType1,String[] ItemOutput1 ) {
        mContext = c;
        this.ItemType1 = ItemType1;
        this.ItemOutput1 = ItemOutput1;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return ItemType1.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.item_details_grid, null);
            TextView ItemType = (TextView) grid.findViewById(R.id.ItemType);
            TextView ItemOutput = (TextView) grid.findViewById(R.id.ItemOutput);
            ItemType.setText(ItemType1[position]);
            ItemOutput.setText(ItemOutput1[position]);

        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}
