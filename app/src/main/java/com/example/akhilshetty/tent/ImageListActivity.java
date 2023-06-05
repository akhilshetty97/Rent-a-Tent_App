package com.example.akhilshetty.tent;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ImageListActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    private List<ImageUpload> imgList;
    private ListView lv;
    private ImageListAdapter adapter;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);
        imgList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listViewImage);
        //Show progress dialog during list image loading
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait loading list image...");
        progressDialog.show();


        mDatabaseRef = FirebaseDatabase.getInstance().getReference(Sell2.FB_DATABASE_PATH);

        mDatabaseRef.getRoot().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                Toast.makeText(ImageListActivity.this, "Hellp", Toast.LENGTH_SHORT).show();

                //Fetch image data from firebase database
                 for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //ImageUpload class require default constructor


                    ImageUpload img = snapshot.getValue(ImageUpload.class);
                   imgList.add(img);
               }


                //Init adapter
                adapter = new ImageListAdapter(ImageListActivity.this, R.layout.image_item, imgList);
                //Set adapter for listview
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();
            }
        });

    }

}
