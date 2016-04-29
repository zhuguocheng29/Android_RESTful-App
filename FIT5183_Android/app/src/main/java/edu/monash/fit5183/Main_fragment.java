package edu.monash.fit5183;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.monash.fit5183.Model.ApplicationUser;

/**
 * Created by Think on 4/14/2016.
 */
public class Main_fragment extends Fragment {
    View vMain;
    private TextView main_name;
    private TextView main_date;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vMain = inflater.inflate(R.layout.fragment_main, container, false);

        main_name = (TextView)vMain.findViewById(R.id.mainname);
        main_date = (TextView)vMain.findViewById(R.id.maindate);

        ApplicationUser au = new ApplicationUser();
        //zhuau.getUsername();
        //System.out.println("222222" + au.getUsername());
        main_name.setText("Hello " + au.getUsername()+" :");
        //get currtent time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String date1 = sdf.format(date);
        main_date.setText("Date:  " + date1);
        return vMain;
    }



    @Override
    public void onResume() {

        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    Log.e("click", "fragment back key is clicked");
                     getActivity().getFragmentManager().popBackStack("backClick", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    //getFragmentManager().popBackStack();
                    return true;
                }
                return false;
            }
        });
    }


}
