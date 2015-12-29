package com.sjrnr.hamza.screenorientationchangeasync;

import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AsyncFragment.TaskCallbacks {

    public static String asyncFrag= "ASYNC_FRAG";
    AsyncFragment asyncFragment;
    boolean loadingState;

    //TextView progress;
    //RelativeLayout loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager= getSupportFragmentManager();
        asyncFragment= (AsyncFragment) fragmentManager.findFragmentByTag(asyncFrag);


        if (asyncFragment == null){
            asyncFragment = new AsyncFragment();
            fragmentManager.beginTransaction().replace(R.id.for_frag,asyncFragment, asyncFrag).commit();
        }



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putBoolean("loading", loadingState);

    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onPreExecute(RelativeLayout r, boolean l) {
        //loadingState= true;
        Log.d("app", "OnPreExecute");
        if (!l){
            r.setVisibility(View.GONE);
        }
        else{
            r.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onProgressUpdate(RelativeLayout r, TextView t, int percent) {

        //During orientation changes, the activity is recreated an this makes the loading screen lose it's state
        //this block here helps ensure the loading screen is visible if the background task is incomplete.
        if (percent<100){
            if (r.getVisibility() != View.VISIBLE){r.setVisibility(View.VISIBLE);}
        }
        String p= percent + " %";
        t.setText(p);

    }

    @Override
    public void onCancelled() {
        Log.d("app", "OnCancelled");
    }

    @Override
    public void onPostExecute(RelativeLayout r, boolean l) {
        Log.d("app", "OnPostExecute");
        //loadingState= false;
        if (!l){
            r.setVisibility(View.GONE);
        }
        else{
            r.setVisibility(View.VISIBLE);
        }
    }
}
