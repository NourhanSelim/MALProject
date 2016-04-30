package com.nourhanselimapps.malproject.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ListAdapter extends BaseAdapter{

	private Context context;
	private ListAdaptorListener listAdaptorListener;
	private int layout;
	private int count;

	public ListAdapter(Context context, int layout, int count, ListAdaptorListener listAdaptorListener) {
		this.context=context;
		this.layout=layout;
		this.count=count;
		this.listAdaptorListener = listAdaptorListener;
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
	    if (convertView == null) {
	        convertView = LayoutInflater.from(context).inflate(layout, null);
	    }
	    listAdaptorListener.fillData(position, convertView);
	    
	    return convertView;
	}
	
	public interface ListAdaptorListener {
		void fillData(int position, View view);
	}
}
