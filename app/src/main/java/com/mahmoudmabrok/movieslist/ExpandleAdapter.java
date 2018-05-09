package com.mahmoudmabrok.movieslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Mahmoud on 5/9/2018.
 */

public class ExpandleAdapter extends BaseExpandableListAdapter {

    private Context context;

    private List<String> _headers;
    private HashMap<String, List<String>> _childs;

    public ExpandleAdapter(Context context, List<String> _headers, HashMap<String, List<String>> _childs) {
        this.context = context;
        this._headers = _headers;
        this._childs = _childs;
    }


    @Override
    public int getGroupCount() {
        return _headers.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return _childs.get(_headers.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return _headers.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return _childs.get(_headers.get(groupPosition))
                .get(childPosition);

    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.group_list, null);
        }

        String headerText = _headers.get(groupPosition);
        String[] texts = headerText.split("[/*]");

        TextView tvHome = (TextView) convertView.findViewById(R.id.tvHome);
        TextView tvAway = (TextView) convertView.findViewById(R.id.tvAway);
        TextView tvResult = (TextView) convertView.findViewById(R.id.tvResult);

        tvHome.setText(texts[0]);
        tvResult.setText(texts[1]);
        tvAway.setText(texts[2]);

        return convertView;


    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item1, null);
        }

        String childText = _childs.get(_headers.get(groupPosition)).get(childPosition);
        TextView textView = (TextView) convertView.findViewById(R.id.tv_item);
        textView.setText(childText);


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

