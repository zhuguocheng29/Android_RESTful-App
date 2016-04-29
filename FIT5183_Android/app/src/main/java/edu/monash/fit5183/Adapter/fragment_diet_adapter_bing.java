package edu.monash.fit5183.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import edu.monash.fit5183.R;

/**
 * Created by Think on 4/22/2016.
 */
public class fragment_diet_adapter_bing extends BaseAdapter {
    private Context context;
    private List<HashMap<String,String>> measure;

    @Override
    public int getCount() {
        return measure.size();
    }

    public fragment_diet_adapter_bing(Context context, List<HashMap<String, String>> measure) {
        this.context = context;
        this.measure = measure;
        // System.out.println("88888");
    }

    @Override
    public Object getItem(int i) {
        return measure.get(i);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        if (view == null) {
            //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LayoutInflater inflater = (LayoutInflater)LayoutInflater.from(context);
            view = inflater.inflate(R.layout.fragment_diet_bing_listview, null); // List layout here
        }
        LayoutInflater inflater = (LayoutInflater)LayoutInflater.from(context);
        view = inflater.inflate(R.layout.fragment_diet_bing_listview, null);
        // List layout here
        TextView fragment_diet_bingTitle = (TextView) view.findViewById(R.id.fragment_diet_bingTitle);
        TextView fragment_diet_bingDescription = (TextView) view.findViewById(R.id.fragment_diet_bingDescription);
        //TextView fragment_diet_bingDisplayUrl = (TextView) view.findViewById(R.id.fragment_diet_bingDisplayUrl);


        //fragment_diet_ndblistview.setText("111111");
        fragment_diet_bingTitle.setText(measure.get(i).get("Title"));
        fragment_diet_bingDescription.setText(measure.get(i).get("Description"));
        //fragment_diet_bingDisplayUrl.setText(measure.get(i).get("DisplayUrl"));

        //System.out.println("66666");

        return view;
    }

}
