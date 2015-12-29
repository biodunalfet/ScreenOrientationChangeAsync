package com.sjrnr.hamza.screenorientationchangeasync;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Hamza Fetuga on 12/28/2015.
 */
public class AsyncFragment extends Fragment {

    private TaskCallbacks tcs;
    View v;
    TextView progress;
    RelativeLayout loading;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_asyncfrag, container, false);
        Button b= (Button) v.findViewById(R.id.button);
        //setRetainInstance(true);
        progress= (TextView) v.findViewById(R.id.textView);
        loading= (RelativeLayout) v.findViewById(R.id.loading);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DoSomething().execute();
            }
        });
        return v;
    }

    private class DoSomething extends AsyncTask<Void, Integer, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (tcs!=null){
                tcs.onPreExecute(loading, true);

            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; !isCancelled() && i < 100; i++) {
                SystemClock.sleep(100);
                publishProgress(i);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... percent) {
            //String p = percent[0] + " %";
            //progress.setText(p);
            if (tcs!=null) {
                tcs.onProgressUpdate(loading, progress, percent[0]);


            }
            super.onProgressUpdate(percent);
        }

        @Override
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            loading.setVisibility(View.GONE);
            if (tcs!=null){
                tcs.onPostExecute(loading, false);
            }

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            if (tcs!=null){
                tcs.onCancelled();
            }
        }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        tcs= (TaskCallbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        tcs = null;
    }

    interface TaskCallbacks{
        void onPreExecute(RelativeLayout r, boolean l);
        void onProgressUpdate(RelativeLayout r, TextView t, int percent);
        void onCancelled();
        void onPostExecute(RelativeLayout r, boolean l);
    }

}
