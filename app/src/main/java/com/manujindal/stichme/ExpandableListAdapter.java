package com.manujindal.stichme;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by manu on 4/7/17.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter{
    private Context context;
    private List<String> listheaders;
    private HashMap<String, List<String>> listHashMap;
    private HashMap<String, List<String>> listHashMapId;
    private HashMap<String, List<String>> listHashMapName;
    private HashMap<String, List<String>> listHashMapStatus;

    public ExpandableListAdapter(Context context, List<String> listheaders, HashMap<String, List<String>> listHashMap, HashMap<String, List<String>> listHashMapId,
                                 HashMap<String, List<String>> listHashMapName, HashMap<String, List<String>> listHashMapStatus) {
        this.context = context;
        this.listheaders = listheaders;
        this.listHashMap = listHashMap;
        this.listHashMapId = listHashMapId;
        this.listHashMapName = listHashMapName;
        this.listHashMapStatus = listHashMapStatus;
    }


    @Override
    public int getGroupCount() {
        return listheaders.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listHashMap.get(listheaders.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listheaders.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listHashMap.get(listheaders.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return Long.parseLong(listHashMapId.get(listheaders.get(groupPosition)).get(childPosition));
    }

    public String getChildName(int groupPosition, int childPosition) {
        return listHashMapName.get(listheaders.get(groupPosition)).get(childPosition);
    }


    public String getChildStatus(int groupPosition, int childPosition) {
        return listHashMapStatus.get(listheaders.get(groupPosition)).get(childPosition);
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if(convertView == null)
        {
            LayoutInflater inflator = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.expandable_list_header, null);
        }
        TextView listheader1 = (TextView) convertView.findViewById(R.id.expandablelistheader);
        listheader1.setTypeface(null, Typeface.BOLD);
        listheader1.setText(headerTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);
        final long childId = (long) getChildId(groupPosition, childPosition);
        final String childName = getChildName(groupPosition, childPosition);
        final String childStatus = getChildStatus(groupPosition, childPosition);

        if(convertView == null)
        {
            LayoutInflater inflator = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.expandable_list_item, null);
        }

        TextView listitem = (TextView) convertView.findViewById(R.id.expandablelistitem);
        TextView listitemId = (TextView) convertView.findViewById(R.id.expandablelistitemId);
        TextView listitemName = (TextView) convertView.findViewById(R.id.expandablelistitemname);
        TextView listitemStatus = (TextView) convertView.findViewById(R.id.expandablelistitemstatus);

        listitem.setText(childText);
        listitemId.setText(Long.toString(childId));
        listitemName.setText(childName);
        listitemStatus.setText(childStatus);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
