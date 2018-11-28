package com.hamsempire.octopusbettingtips;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdmobLoad extends Fragment {

    View view;
    TextView textView;
    Button button;
    RewardedVideoAd mRewardedVideoAd;

    /* @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
         // Inflate the layout for this fragment
         return inflater.inflate(R.layout.fragment_admob_load, container, false);*/
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admob_load, container, false);
        textView = view.findViewById(R.id.textView);
        button = view.findViewById(R.id.btnAds);

        //MobileAds.initialize(getActivity().getApplicationContext(), "ca-app-pub-3940256099942544/5224354917");
       /* MobileAds.initialize(getActivity().getApplicationContext(),
                getString(R.string.admob_app_id));

        // Get reference to singleton RewardedVideoAd object
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(getActivity().getApplicationContext());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do something
                // Load a reward based video ad
                mRewardedVideoAd.loadAd(getString(R.string.admob_app_id), new AdRequest.Builder().build());
            }
        });
*/

        return view;
    }

    @Override
    public void onResume() {
        mRewardedVideoAd.resume(getActivity().getApplicationContext());
        super.onResume();

    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(getActivity().getApplicationContext());
        super.onPause();

    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(getActivity().getApplicationContext());
        super.onDestroy();

    }



}
