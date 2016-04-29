package edu.monash.fit5183;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Think on 4/25/2016.
 */
public class Report_fragment extends Fragment {
    View vreport;
    private TextView daliyButton;
    private TextView weeklyButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vreport = inflater.inflate(R.layout.fragment_report, container, false);

        daliyButton = (TextView)vreport.findViewById(R.id.frag_report_dailybutton);
        weeklyButton = (TextView)vreport.findViewById(R.id.frag_report_weeklybutton);

        Fragment fragment = new Report_daily_fragment();
        FragmentManager fragmentManager=getFragmentManager();
        fragmentManager.beginTransaction()
                .addToBackStack("report")
                .replace(R.id.frag_report_container, fragment)
                .commit();

        daliyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Report_daily_fragment();
                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction()
                        .addToBackStack("report")
                        .replace(R.id.frag_report_container, fragment)
                        .commit();
            }
        });

        weeklyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Report_weekly_fragment();
                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction()
                        .addToBackStack("report")
                        .replace(R.id.frag_report_container, fragment)
                        .commit();
            }
        });
        return vreport;
    }
}
