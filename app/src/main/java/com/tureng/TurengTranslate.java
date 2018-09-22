package com.tureng;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.firebaseDemo.Artist;
import com.firebaseDemo.MyAdapter;
import com.firebaseDemo.StudentsActivity;
import com.google.firebase.database.DataSnapshot;
import com.onur.easyspeakdemo.MainActivity;
import com.onur.easyspeakdemo.R;
import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class TurengTranslate extends AppCompatActivity {


    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;


    Results results;
    Boolean isSearch;
    private SearchBox search;

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






        search = (SearchBox) findViewById(R.id.searchbox);
        search.enableVoiceRecognition(this);

        search.setMenuListener(new SearchBox.MenuListener(){

            @Override
            public void onMenuClick() {
                //Hamburger has been clicked
                Toast.makeText(TurengTranslate.this, "Menu click", Toast.LENGTH_LONG).show();
            }

        });
        search.setSearchListener(new SearchBox.SearchListener(){

            @Override
            public void onSearchOpened() {
                //Use this to tint the screen
            }

            @Override
            public void onSearchClosed() {
                //Use this to un-tint the screen
            }

            @Override
            public void onSearchTermChanged(String term) {
                //React to the search term changing
                //Called after it has updated results
            }

            @Override
            public void onSearch(String searchTerm) {
                new FetchTitle().execute(searchTerm); // açıklama çekmek için
            }

            @Override
            public void onResultClick(SearchResult result) {
                //React to a result being clicked
            }

            @Override
            public void onSearchCleared() {
                //Called when the clear button is clicked
            }

        });





    }





    public class FetchTitle extends AsyncTask<String, Void, Void> {

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
        protected Void doInBackground(String... params) {

            try{

                Tureng tureng = new Tureng();
                results = tureng.translate(params[0]); // input can be in Turkish or English


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
