package com.mahmoudmabrok.movieslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mahmoud on 5/9/2018.
 */

public class ExpandleAdapter extends BaseExpandableListAdapter {

    private Context context;

    private List<HashMap<String, String>> _headers;
    private HashMap<HashMap<String, String>, String> _childs;


    public ExpandleAdapter(Context context, List<HashMap<String, String>> _headers, HashMap<HashMap<String, String>, String> _childs) {
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
        return _childs.get(_headers.get(groupPosition)).length() > 0 ? 1 : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return _headers.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return _childs.get(_headers.get(groupPosition));
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

        Map<String, String> mdata = _headers.get(groupPosition);


        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvCount = (TextView) convertView.findViewById(R.id.tvCount);
        TextView tvYear = (TextView) convertView.findViewById(R.id.tvYear);
        TextView tvRate = (TextView) convertView.findViewById(R.id.tvRate);

        tvTitle.setText(mdata.get("title"));
        tvCount.setText(mdata.get("vote_count"));
        tvYear.setText(mdata.get("release_date"));
        tvRate.setText(mdata.get("vote_average"));


        return convertView;


    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }

        String childText = _childs.get(_headers.get(groupPosition));
        TextView textView = (TextView) convertView.findViewById(R.id.tvOverview);
        textView.setText(childText);


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

