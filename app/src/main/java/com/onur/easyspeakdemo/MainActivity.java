package com.onur.easyspeakdemo;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebaseDemo.Admin;
import com.firebaseDemo.Artist;
import com.firebaseDemo.Teacher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jackandphantom.blurimage.BlurImage;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button giris;
    private TextView ad,sifre;
    private ImageView background;
    private ImageView facebookIcon;
    private ImageView instagramIcon;
    private ImageView youtubeIcon;
    private ImageView linkedinIcon;
    private ImageView twitterIcon;



    // List<String> kullanicilar = new ArrayList<String>();
    DatabaseReference databaseArtists;
    DatabaseReference databaseTeacher;
    DatabaseReference databaseAdmin;


    List<Artist> artistList;
    List<Teacher> teacherList;
    List<Admin> adminList;


    @Override
    protected void onStart() {
        super.onStart();
        //Retriving data From Firebase
        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                artistList.clear();

                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren() ){
                    //Create Artist Class Object and Returning Value
                    Artist artist = artistSnapshot.getValue(Artist.class);
                    artistList.add(artist);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Student Verisi Çekilemedi.");

            }
        });

        databaseTeacher.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                teacherList.clear();

                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren() ){
                    //Create Artist Class Object and Returning Value
                    Teacher teacher = artistSnapshot.getValue(Teacher.class);
                    teacherList.add(teacher);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Student Verisi Çekilemedi.");

            }
        });
        databaseAdmin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adminList.clear();

                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren() ){
                    //Create Artist Class Object and Returning Value
                    Admin admin = artistSnapshot.getValue(Admin.class);
                    adminList.add(admin);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Student Verisi Çekilemedi.");

            }
        });


    }


    //if(kullanicilar.contains("Osman")) {
    // System.out.println("Osman bulundu");

    //final veritabani vt=new veritabani(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseArtists = FirebaseDatabase.getInstance().getReference("students");
        databaseTeacher = FirebaseDatabase.getInstance().getReference("teachers");
        databaseAdmin = FirebaseDatabase.getInstance().getReference("admin");

        artistList = new ArrayList<>();
        teacherList = new ArrayList<>();
        adminList = new ArrayList<>();



        //Intent sayfagecis= new Intent(MainActivity.this, com.user.StudentRegister.class);
        //Intent sayfagecis= new Intent(MainActivity.this, dersSecimi.class);
        background=findViewById(R.id.background);

        //startActivity(sayfagecis);
        ad=(TextView)findViewById(R.id.kul_ad);
        sifre=(TextView) findViewById(R.id.kul_sifre);
        final String[] k_adlari = new String[10];
        k_adlari[0]="nes";
        k_adlari[1]="onr";
        k_adlari[2]="meh";

        System.out.println("Hello");
        final String[] k_sifreleri = new String[10];
        k_sifreleri[0]="111";
        k_sifreleri[1]="222";
        k_sifreleri[2]="333";


        facebookIcon = findViewById(R.id.facebook);
        facebookIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("https://www.facebook.com/easyspeaktr/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        instagramIcon = findViewById(R.id.instagram);
        instagramIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("https://www.instagram.com/easyspeaktr/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        youtubeIcon = findViewById(R.id.youtube);
        youtubeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("https://www.youtube.com/channel/UCM2Q_libBGlUMPYX63v5rKA?view_as=subscriber");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        linkedinIcon = findViewById(R.id.linkedin);
        linkedinIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("https://www.linkedin.com/company/easyspeakschoolofenglish/?originalSubdomain=tr");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });
        twitterIcon = findViewById(R.id.twitter);
        twitterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("https://twitter.com/easyspeaktr");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });


        BlurImage.with(getApplicationContext()).load(R.drawable.backgorund).intensity(20).Async(true).into(background);
        giris= (Button) findViewById(R.id.giris);
        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //vt.VeriListele();
                String adi=ad.getText().toString();
                String sifresi=sifre.getText().toString();
                String kullanici;
                Intent intent=getIntent();


                final Intent showStudentMenu = new Intent(MainActivity.this, ShowStudentMenu.class);
                final Intent showTeacherMenu = new Intent(MainActivity.this, ShowTeacherMenu.class);
                final Intent showAdminMenu = new Intent(MainActivity.this, ShowAdminMenu.class);


                    String selection=intent.getStringExtra("admin");
                    if(selection.equals("ogrenci")){
                for (int i = 0 ; i <artistList.size();i++) {
                    if (adi.equals(artistList.get(i).getPhoneNumber()) && sifresi.equals("111")) {
                        //Log.d("deneme", "basarili");
                        Toast.makeText(getApplicationContext(), "Başarılı!Yönlendiriliyorsunuz...", Toast.LENGTH_SHORT).show();
                        showStudentMenu.putExtra("phoneNumber", adi);
                        showStudentMenu.putExtra("grade", artistList.get(i).getArtistGrade());
                        showStudentMenu.putExtra("name", artistList.get(i).getArtistName());
                        showStudentMenu.putExtra("studentKey", artistList.get(i).getArtistKey());


                        startActivityForResult(showStudentMenu, 1);
                    } else {
                        //Toast.makeText(getApplicationContext(), "Yanlış kullanici adi veya şifre", Toast.LENGTH_SHORT).show();
                        //startActivity(sayfagecis);
                    }
                }}

                String selection1=intent.getStringExtra("admin");
                if(selection1.equals("ogretmen")){
                for (int i = 0 ; i <teacherList.size();i++) {
                    if (adi.equals(teacherList.get(i).getPhoneNumber()) && sifresi.equals("111")) {
                        //Log.d("deneme", "basarili");
                        Toast.makeText(getApplicationContext(), "Başarılı!Yönlendiriliyorsunuz...", Toast.LENGTH_SHORT).show();
                        showTeacherMenu.putExtra("phoneNumber", adi);
                        showTeacherMenu.putExtra("name", teacherList.get(i).getName());


                        startActivityForResult(showTeacherMenu, 1);
                    } else {
                        Toast.makeText(getApplicationContext(), "Yanlış kullanici adi veya şifre", Toast.LENGTH_SHORT).show();
                        //startActivity(sayfagecis);
                    }
                }
                }

                String selection2=intent.getStringExtra("admin");
                if(selection2.equals("admin")) {
                    for (int i = 0; i < adminList.size(); i++) {
                        if (adi.equals(adminList.get(i).getPhoneNumber()) && sifresi.equals("111")) {
                            //Log.d("deneme", "basarili");
                            Toast.makeText(getApplicationContext(), "Başarılı!Yönlendiriliyorsunuz...", Toast.LENGTH_SHORT).show();
                            showAdminMenu.putExtra("phoneNumber", adi);


                            startActivityForResult(showAdminMenu, 1);
                        } else {
                            Toast.makeText(getApplicationContext(), "Yanlış kullanici adi veya şifre", Toast.LENGTH_SHORT).show();
                            //startActivity(sayfagecis);
                        }

                    }
                }


                }

//
//                if (adi.equals(k_adlari[0]) && sifresi.equals(k_sifreleri[0]) || adi.equals(k_adlari[1]) && sifresi.equals(k_sifreleri[1]) || adi.equals(k_adlari[2]) && sifresi.equals(k_sifreleri[2])) {
//                    //Log.d("deneme", "basarili");
//                    Toast.makeText(getApplicationContext(), "Başarılı!Yönlendiriliyorsunuz...", Toast.LENGTH_SHORT).show();
//                    startActivity(sayfagecis);
//                    kullanici=adi;
//                }
//                else
//                    Toast.makeText(getApplicationContext(),"Yanlış kullanici adi veya şifre", Toast.LENGTH_SHORT).show();
//                //startActivity(sayfagecis);
              });

    }


}
