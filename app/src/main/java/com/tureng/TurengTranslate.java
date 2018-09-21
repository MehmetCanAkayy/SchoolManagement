package com.tureng;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebaseDemo.Artist;
import com.firebaseDemo.MyAdapter;
import com.firebaseDemo.StudentsActivity;
import com.google.firebase.database.DataSnapshot;
import com.onur.easyspeakdemo.MainActivity;
import com.onur.easyspeakdemo.R;

import java.util.ArrayList;
import java.util.List;

public class TurengTranslate extends AppCompatActivity {


    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;


    Results results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tureng_translate);
        results = new Results("Tureng");

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerVieww




        new FetchTitle().execute(); // açıklama çekmek için





    }





    public class FetchTitle extends AsyncTask<Void, Void, Void> {

        String title;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(TurengTranslate.this);
            progressDialog.setTitle("Translate");
            progressDialog.setMessage("Ceviri Yapiliyor...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();


        }

        @Override
        protected Void doInBackground(Void... params) {

            try{

                Tureng tureng = new Tureng();
                results = tureng.translate("buy"); // input can be in Turkish or English


            }catch (Exception e){

                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


                if (results.isEmpty())
                    System.out.println("No result found!");
                else
                    for (Result result : results)
                        System.out.println(result);

                TurengAdapter mAdapter= new TurengAdapter(results);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TurengTranslate.this);

                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(mAdapter);

                progressDialog.dismiss();



        }
    }
}
