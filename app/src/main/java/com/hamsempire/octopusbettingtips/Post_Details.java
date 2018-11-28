package com.hamsempire.octopusbettingtips;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdChoicesView;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.luseen.autolinklibrary.AutoLinkMode;
import com.luseen.autolinklibrary.AutoLinkOnClickListener;
import com.luseen.autolinklibrary.AutoLinkTextView;

import java.util.ArrayList;
import java.util.List;

public class Post_Details extends AppCompatActivity {
    private static final String TAG ="FACEBOOK_ADS" ;
    private com.facebook.ads.InterstitialAd interstitialAd;
    NativeBannerAd nativeBannerAd;
    private RelativeLayout nativeBannerAdContainer;
    private LinearLayout adView;


    DatabaseReference mRef;
    String postKey;
    TextView tvTitle, tvBody, tvTime;
    ImageView imgBody;
    ProgressDialog pd;
    String selection;
    AutoLinkTextView autoLinkTextView;
    Fragment mFragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        setContentView(R.layout.activity_post_detailed);

        postKey = getIntent().getExtras().getString("postKey");
        selection=getIntent().getExtras().getString("selection");
        tvBody = (TextView) findViewById(R.id.tvBody);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTime = (TextView) findViewById(R.id.post_time);
        imgBody = (ImageView) findViewById(R.id.imgBody);
        showNatives();
        pd=new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.show();

        autoLinkTextView = (AutoLinkTextView)findViewById(R.id.autoLinkrate);
        autoLinkTextView.addAutoLinkMode(AutoLinkMode.MODE_CUSTOM);
        autoLinkTextView.setCustomRegex("\\sHere\\b");


        autoLinkTextView.setAutoLinkText("Motivate us to continue giving you free winning tips by rating us five stars : Here");

        autoLinkTextView.setAutoLinkOnClickListener(new AutoLinkOnClickListener() {
            @Override
            public void onAutoLinkTextClick(AutoLinkMode autoLinkMode, String matchedText) {
                if (autoLinkMode == AutoLinkMode.MODE_CUSTOM)
                    try {
                        Intent RateIntent =
                                new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.hamsempire.octopusbettingtips"));
                        startActivity(RateIntent);
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),"Unable to connect try again later...",
                                Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

            }
        });

        autoLinkTextView = (AutoLinkTextView)findViewById(R.id.autoLinkjoin);
        autoLinkTextView.addAutoLinkMode(AutoLinkMode.MODE_CUSTOM);
        autoLinkTextView.setCustomRegex("\\sHere\\b");


        autoLinkTextView.setAutoLinkText("Join Our Telegram channel for more winning tips : Here");

        autoLinkTextView.setAutoLinkOnClickListener(new AutoLinkOnClickListener() {
            @Override
            public void onAutoLinkTextClick(AutoLinkMode autoLinkMode, String matchedText) {
                if (autoLinkMode == AutoLinkMode.MODE_CUSTOM)


                mFragment = new Telegram_Websites();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, mFragment).commit();

                    /*try {
                        Intent JoinIntent =
                                new Intent("android.intent.action.VIEW", Uri.parse("https://t.me/OctopusBettingTips"));
                        startActivity(JoinIntent);
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),"Unable to connect try again later...",
                                Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }*/

            }
        });

        autoLinkTextView = (AutoLinkTextView)findViewById(R.id.autoLinkshare);
        autoLinkTextView.addAutoLinkMode(AutoLinkMode.MODE_CUSTOM);
        autoLinkTextView.setCustomRegex("\\sHere\\b");


        autoLinkTextView.setAutoLinkText("Get all details on how to join vip : Here");

        autoLinkTextView.setAutoLinkOnClickListener(new AutoLinkOnClickListener() {
            @Override
            public void onAutoLinkTextClick(AutoLinkMode autoLinkMode, String matchedText) {

                if (autoLinkMode == AutoLinkMode.MODE_CUSTOM)
                    mFragment = new JoinVipWeb();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, mFragment).commit();

            }
        });

        if (postKey != null) {

            mRef = FirebaseDatabase.getInstance().getReference().child("jackpot").child(selection).child(postKey);
        }
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String title = dataSnapshot.child("title").getValue().toString();
                String body = dataSnapshot.child("body").getValue().toString();
                Long time = (Long) dataSnapshot.child("time").getValue();
                if (title != null) {
                    tvTitle.setText(title.toUpperCase());
                    pd.dismiss();
                } else {
                    Toast.makeText(Post_Details.this, "Check your internet connection and try again", Toast.LENGTH_SHORT).show();
                }
                if (body != null) {
                    tvBody.setText(body);

                }
                if (time != null) {
                    setTime(time);
                }
                if (dataSnapshot.hasChild("image")){
                    String image= (String) dataSnapshot.child("image").getValue();

                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //showInterstitials();

    }

    private void showInterstitials(){
//AdSettings.addTestDevice(getString(R.string.test_id));

        interstitialAd = new com.facebook.ads.InterstitialAd(this, getString(R.string.fb_interstitials));
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!");
            }
        });

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd();
    }

    private void showNatives(){
        nativeBannerAd = new NativeBannerAd(Post_Details.this,getString(R.string.fb_banners));
        nativeBannerAd.setAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                // Native ad finished downloading all assets
                Log.e(TAG, "Native ad finished downloading all assets.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Native ad failed to load
                Log.e(TAG, "Native ad failed to load: " + adError.getErrorMessage());

            }

            @Override
            public void onAdLoaded(Ad ad) {

                // Native ad is loaded and ready to be displayed
                Log.d(TAG, "Native ad is loaded and ready to be displayed!");
                if (nativeBannerAd == null || nativeBannerAd != ad) {
                    return;
                }

                // Inflate Native Banner Ad into Container
                inflateAd(nativeBannerAd);
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Native ad clicked
                Log.d(TAG, "Native ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Native ad impression
                Log.d(TAG, "Native ad impression logged!");
            }
        });
        // load the ad
        nativeBannerAd.loadAd();

    }

    private void inflateAd(NativeBannerAd nativeBannerAd) {
        // Unregister last ad
        nativeBannerAd.unregisterView();

        // Add the Ad view into the ad container.
        nativeBannerAdContainer = findViewById(R.id.native_banner_ad_container);
        LayoutInflater inflater = LayoutInflater.from(Post_Details.this);
        // Inflate the Ad view.  The layout referenced is the one you created in the last step.
        adView = (LinearLayout) inflater.inflate(R.layout.native_banner_ad_unit, nativeBannerAdContainer, false);
        nativeBannerAdContainer.addView(adView);

        // Add the AdChoices icon
        RelativeLayout adChoicesContainer = adView.findViewById(R.id.ad_choices_container);
        AdChoicesView adChoicesView = new AdChoicesView(Post_Details.this, nativeBannerAd, true);
        adChoicesContainer.addView(adChoicesView, 0);
        // Create native UI using the ad metadata.
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        AdIconView nativeAdIconView = adView.findViewById(R.id.native_icon_view);
        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdCallToAction.setText(nativeBannerAd.getAdCallToAction());
        nativeAdCallToAction.setVisibility(
                nativeBannerAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdTitle.setText(nativeBannerAd.getAdvertiserName());
        nativeAdSocialContext.setText(nativeBannerAd.getAdSocialContext());
        sponsoredLabel.setText(nativeBannerAd.getSponsoredTranslation());

        // Register the Title and CTA button to listen for clicks.
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);
        nativeBannerAd.registerViewForInteraction(adView, nativeAdIconView, clickableViews);
    }



    @Override
    public void onBackPressed() {
        /*mInterstitialAd = createNewIntAd();
        loadIntAdd();*/
        showInterstitials();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            /*mInterstitialAd = createNewIntAd();
            loadIntAdd();*/
            showInterstitials();
            finish();
        } else if (id == R.id.about) {
           /* AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Jackpot Predictions");
            try {
                alert.setMessage("Version " + getApplication().getPackageManager().getPackageInfo(getPackageName(), 0).versionName +
                        "\n" + " Automata Software. \n" + "\n" +
                        "All rights reserved \n"
                );
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            alert.show();*/
            // Inflate the about message contents
            View messageView = getLayoutInflater().inflate(R.layout.about, null, false);

            // When linking text, force to always use default color. This works
            // around a pressed color state bug.
            TextView textView = (TextView) messageView.findViewById(R.id.about_credits);
            TextView textView1 = (TextView) messageView.findViewById(R.id.about_description);
            int defaultColor = textView.getResources().getColor(R.color.colorBlack);
            int defaultColor1 = textView1.getResources().getColor(R.color.colorBlack);
            //int defaultColor = textView.getTextColors().getDefaultColor();
            textView.setTextColor(defaultColor);
            textView1.setTextColor(defaultColor1);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setTitle(R.string.app_name);
            builder.setView(messageView);
            builder.create();
            builder.show();
        } else if (id == R.id.feedback) {
            startActivity(new Intent(Post_Details.this, Feedback.class));
        } else if (id == R.id.rate) {
            Uri uri = Uri.parse("market://details?id=" + getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(Post_Details.this, "Unable to find play store", Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.menu_share) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Win Big with the best football tips app on playstore . Download here https://play.google.com/store/apps/details?id=com.hamsempire.octopusbettingtips";
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Best Football Predictions App on Play Store");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(sharingIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void setTime(Long time) {
        TextView txtTime = (TextView) findViewById(R.id.post_time);
        //long elapsedDays=0,elapsedWeeks = 0, elapsedHours=0,elapsedMin=0;
        long elapsedTime;
        long currentTime = System.currentTimeMillis();
        int elapsed = (int) ((currentTime - time) / 1000);
        if (elapsed < 60) {
            if (elapsed < 2) {
                txtTime.setText("Just Now");
            } else {
                txtTime.setText(elapsed + " sec ago");
            }
        } else if (elapsed > 604799) {
            elapsedTime = elapsed / 604800;
            if (elapsedTime == 1) {
                txtTime.setText(elapsedTime + " week ago");
            } else {

                txtTime.setText(elapsedTime + " weeks ago");
            }
        } else if (elapsed > 86399) {
            elapsedTime = elapsed / 86400;
            if (elapsedTime == 1) {
                txtTime.setText(elapsedTime + " day ago");
            } else {
                txtTime.setText(elapsedTime + " days ago");
            }
        } else if (elapsed > 3599) {
            elapsedTime = elapsed / 3600;
            if (elapsedTime == 1) {
                txtTime.setText(elapsedTime + " hour ago");
            } else {
                txtTime.setText(elapsedTime + " hours ago");
            }
        } else if (elapsed > 59) {
            elapsedTime = elapsed / 60;
            txtTime.setText(elapsedTime + " min ago");


        }

    }
}
