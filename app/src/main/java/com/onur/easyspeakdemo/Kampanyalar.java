package com.onur.easyspeakdemo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.StudentMenu.StudentSurveyActivity;
import com.firebaseDemo.Camp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Kampanyalar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kampanyalar);

        TextView txtTur=findViewById(R.id.KampanyaTur);
        TextView txtYuzde=findViewById(R.id.yuzde);
        TextView txtKur=findViewById(R.id.kur);
        TextView txtHediyeKur=findViewById(R.id.hediyeKur);
        EditText edtMesaj=findViewById(R.id.mesaj);
        EditText edtHediye=findViewById(R.id.hediye);

        Spinner spTur = findViewById(R.id.spinner1);
        Spinner spYuzde = findViewById(R.id.spinner2);
        Spinner spKur = findViewById(R.id.spinner3);
        Spinner spHediyeKur = findViewById(R.id.spinner4);
        Button button = findViewById(R.id.yayınla);

        ArrayList<Camp> list= new ArrayList<>();

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Kampanya");

        ArrayAdapter<CharSequence> adapterTur = ArrayAdapter.createFromResource(this,
                R.array.Tur, android.R.layout.simple_spinner_item);
        adapterTur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spTur.setAdapter(adapterTur);


        edtHediye.setEnabled(false);
        spHediyeKur.setEnabled(false);
        spKur.setEnabled(false);
        spYuzde.setEnabled(false);
        txtHediyeKur.setEnabled(false);
        txtKur.setEnabled(false);
        txtYuzde.setEnabled(false);


button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        String id=databaseReference.push().getKey();
        Camp camp = null;

        String name=spTur.getSelectedItem().toString();

        if(spTur.getSelectedItem().equals("Kur")){
            camp=new Camp(id,spTur.getSelectedItem().toString(),null,spKur.getSelectedItem().toString()
                    ,spHediyeKur.getSelectedItem().toString(),null,edtMesaj.getText().toString().trim());
        }
        else if(spTur.getSelectedItem().equals("Yuzde ve Kur")){
            camp=new Camp(id,spTur.getSelectedItem().toString(),spYuzde.getSelectedItem().toString(),spKur.getSelectedItem().toString()
                    ,spHediyeKur.getSelectedItem().toString(),null,edtMesaj.getText().toString().trim());

        }
        else if(spTur.getSelectedItem().equals("Kur ve Hediye")){
            camp=new Camp(id,spTur.getSelectedItem().toString(),null,spKur.getSelectedItem().toString()
                    ,spHediyeKur.getSelectedItem().toString(),edtHediye.getText().toString().trim(),edtMesaj.getText().toString().trim());
        }
        else if(spTur.getSelectedItem().equals("Yuzde, Kur ve Hediye")){
            camp=new Camp(id,spTur.getSelectedItem().toString(),spYuzde.getSelectedItem().toString(),spKur.getSelectedItem().toString()
                    ,spHediyeKur.getSelectedItem().toString(),edtHediye.getText().toString().trim(),edtMesaj.getText().toString().trim());

        }
        else if(spTur.getSelectedItem().equals("Hediye")){
            camp=new Camp(id,spTur.getSelectedItem().toString(),null,null
                    ,null,edtHediye.getText().toString().trim(),edtMesaj.getText().toString().trim());

        }

        Toast.makeText(Kampanyalar.this, "Başarıyla Oluşturuldu..",Toast.LENGTH_LONG).show();
        databaseReference.child(id).setValue(camp);




    }
});

        spTur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position==0){
                    spKur.setEnabled(true);
                    txtKur.setEnabled(true);
                    txtHediyeKur.setEnabled(true);
                    spHediyeKur.setEnabled(true);
                    spYuzde.setVisibility(View.GONE);
                    edtHediye.setVisibility(View.GONE);
                    txtYuzde.setVisibility(View.GONE);
                    ArrayAdapter<CharSequence> adapterKur = ArrayAdapter.createFromResource(Kampanyalar.this,
                            R.array.Kur, android.R.layout.simple_spinner_item);
                    adapterKur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spKur.setAdapter(adapterKur);
                    ArrayAdapter<CharSequence> adapterHediyeKur = ArrayAdapter.createFromResource(Kampanyalar.this,
                            R.array.Kur, android.R.layout.simple_spinner_item);
                    adapterHediyeKur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spHediyeKur.setAdapter(adapterHediyeKur);



                }
                else if(position==1){
                    spKur.setEnabled(true);
                    txtKur.setEnabled(true);
                    txtHediyeKur.setEnabled(true);
                    spHediyeKur.setEnabled(true);
                    txtYuzde.setEnabled(true);
                    spYuzde.setEnabled(true);
                    spYuzde.setVisibility(View.VISIBLE);
                    txtYuzde.setVisibility(View.VISIBLE);
                    edtHediye.setVisibility(View.GONE);
                    ArrayAdapter<CharSequence> adapterKur = ArrayAdapter.createFromResource(Kampanyalar.this,
                            R.array.Kur, android.R.layout.simple_spinner_item);
                    adapterKur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spKur.setAdapter(adapterKur);

                    ArrayAdapter<CharSequence> adapterHediyeKur = ArrayAdapter.createFromResource(Kampanyalar.this,
                            R.array.Kur, android.R.layout.simple_spinner_item);
                    adapterHediyeKur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spHediyeKur.setAdapter(adapterHediyeKur);

                    ArrayAdapter<CharSequence> adapterYuzde = ArrayAdapter.createFromResource(Kampanyalar.this,
                            R.array.Yuzde, android.R.layout.simple_spinner_item);
                    adapterYuzde.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spYuzde.setAdapter(adapterYuzde);




                }
                else if(position==2){
                    spKur.setEnabled(true);
                    txtKur.setEnabled(true);
                    edtHediye.setEnabled(true);
                    spYuzde.setVisibility(View.GONE);
                    txtYuzde.setVisibility(View.GONE);
                    edtHediye.setVisibility(View.VISIBLE);
                    ArrayAdapter<CharSequence> adapterKur = ArrayAdapter.createFromResource(Kampanyalar.this,
                            R.array.Kur, android.R.layout.simple_spinner_item);
                    adapterKur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spKur.setAdapter(adapterKur);

                    ArrayAdapter<CharSequence> adapterHediyeKur = ArrayAdapter.createFromResource(Kampanyalar.this,
                            R.array.Kur, android.R.layout.simple_spinner_item);
                    adapterHediyeKur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spHediyeKur.setAdapter(adapterHediyeKur);




                }
                else if(position==3){
                    spKur.setEnabled(true);
                    txtKur.setEnabled(true);
                    txtHediyeKur.setEnabled(true);
                    spHediyeKur.setEnabled(true);
                    txtYuzde.setEnabled(true);
                    spYuzde.setEnabled(true);
                    edtHediye.setEnabled(true);
                    spYuzde.setVisibility(View.VISIBLE);
                    txtYuzde.setVisibility(View.VISIBLE);
                    edtHediye.setVisibility(View.VISIBLE);
                    ArrayAdapter<CharSequence> adapterKur = ArrayAdapter.createFromResource(Kampanyalar.this,
                            R.array.Kur, android.R.layout.simple_spinner_item);
                    adapterKur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spKur.setAdapter(adapterKur);

                    ArrayAdapter<CharSequence> adapterHediyeKur = ArrayAdapter.createFromResource(Kampanyalar.this,
                            R.array.Kur, android.R.layout.simple_spinner_item);
                    adapterHediyeKur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spHediyeKur.setAdapter(adapterHediyeKur);

                    ArrayAdapter<CharSequence> adapterYuzde = ArrayAdapter.createFromResource(Kampanyalar.this,
                            R.array.Yuzde, android.R.layout.simple_spinner_item);
                    adapterYuzde.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spYuzde.setAdapter(adapterYuzde);



                }
                else if(position==4){
                    edtHediye.setEnabled(true);
                    edtHediye.setVisibility(View.VISIBLE);
                    spYuzde.setVisibility(View.GONE);
                    txtYuzde.setVisibility(View.GONE);
                    txtKur.setVisibility(View.GONE);
                    txtYuzde.setVisibility(View.GONE);
                    spKur.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });







    }


}
