package com.hamsempire.octopusbettingtips;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class JoinVip extends Fragment {

    View view;
    TextView txtHeader;
    TextView txtBody;

    /*public JoinVip() {
        // Required empty public constructor
    }
*/

    /* @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
         // Inflate the layout for this fragment
         return inflater.inflate(R.layout.fragment_join_vip, container, false);
     }*/
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_join_vip, container, false);
        txtHeader = (TextView) view.findViewById(R.id.header);
        txtBody = (TextView) view.findViewById(R.id.txtBodv);

        return view;

    }

}
