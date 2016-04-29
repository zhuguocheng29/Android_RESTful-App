package edu.monash.fit5183.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.monash.fit5183.R;

/**
 * Created by Think on 4/20/2016.
 */
public class fragment_diet_adapter extends BaseAdapter {
    private Context context;
    private List<HashMap<String,String>> measure;

    @Override
    public int getCount() {
        return measure.size();
    }

    public fragment_diet_adapter(Context context, List<HashMap<String, String>> measure) {
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
            view = inflater.inflate(R.layout.fragment_diet_nbd_listview, null); // List layout here
        }
        LayoutInflater inflater = (LayoutInflater)LayoutInflater.from(context);
        view = inflater.inflate(R.layout.fragment_diet_nbd_listview, null); // List layout here
        TextView fragment_diet_ndblistview = (TextView) view.findViewById(R.id.fragment_diet_ndbname);
        TextView fragment_diet_ndblabel = (TextView) view.findViewById(R.id.fragment_diet_ndblabel);
        TextView fragment_diet_ndbeqv = (TextView) view.findViewById(R.id.fragment_diet_ndbeqv);
        TextView fragment_diet_ndbqty = (TextView) view.findViewById(R.id.fragment_diet_ndbqty);
        TextView fragment_diet_ndbvalue = (TextView) view.findViewById(R.id.fragment_diet_ndbvalue);

        //fragment_diet_ndblistview.setText("111111");
        fragment_diet_ndblistview.setText("nutrient name : " + measure.get(i).get("name"));
        fragment_diet_ndblabel.setText("label :" + measure.get(i).get("label"));
        fragment_diet_ndbeqv.setText("eqv :" + measure.get(i).get("eqv"));
        fragment_diet_ndbqty.setText("qty :" + measure.get(i).get("qty"));
        fragment_diet_ndbvalue.setText("value :" + measure.get(i).get("value"));

      /*  System.out.println("66666");
        System.out.println("111 " + measure.get(i).get("name"));
        System.out.println("222 " + measure.get(i).get("label"));
        System.out.println("333 " +  measure.get(i).get("eqv"));
        System.out.println("444 " + measure.get(i).get("qty"));
        System.out.println("555" +  measure.get(i).get("value"));
*/
        return view;
    }
}
