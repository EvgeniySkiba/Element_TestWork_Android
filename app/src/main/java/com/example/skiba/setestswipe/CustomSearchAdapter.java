package com.example.skiba.setestswipe;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class CustomSearchAdapter extends ArrayAdapter<String> implements Filterable {

    private List<String> allModelItemsArray;
    private List<String> filteredModelItemsArray;
    private Activity context;
    private ModelFilter filter;
    private LayoutInflater inflater;

    public CustomSearchAdapter(Activity context, ArrayList<String> list) {
        super(context, R.layout.list_item, list);
        this.context = context;
        this.allModelItemsArray = new ArrayList<String>();
        allModelItemsArray.addAll(list);
        this.filteredModelItemsArray = new ArrayList<String>();
        filteredModelItemsArray.addAll(allModelItemsArray);
        inflater = context.getLayoutInflater();
        getFilter();
    }
    @Override
    public Filter getFilter() {
        if (filter == null){
            filter  = new ModelFilter();
        }
        return filter;
    }
    static class ViewHolder {
        protected TextView text;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        String m = filteredModelItemsArray.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {

            view = inflater.inflate(R.layout.list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) view.findViewById(R.id.tvItem);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = ((ViewHolder) view.getTag());
        }
        viewHolder.text.setText(m);
        return view;
    }

    private class ModelFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            constraint = constraint.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if(constraint.toString().length() > 0)
            {
                ArrayList<String> filteredItems = new ArrayList<String>();

                for(int i = 0, l = allModelItemsArray.size(); i < l; i++)
                {
                    String m = allModelItemsArray.get(i);
                    if(m.toLowerCase().contains(constraint))
                        filteredItems.add(m);
                }
                result.count = filteredItems.size();
                result.values = filteredItems;
            }
            else
            {
                synchronized(this)
                {
                    result.values = allModelItemsArray;
                    result.count = allModelItemsArray.size();
                }
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            filteredModelItemsArray = (ArrayList<String>)results.values;
            notifyDataSetChanged();
            clear();
            for(int i = 0, l = filteredModelItemsArray.size(); i < l; i++)
                add(filteredModelItemsArray.get(i));
            notifyDataSetInvalidated();
        }
    }
}
