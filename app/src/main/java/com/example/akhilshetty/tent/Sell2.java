package com.example.akhilshetty.tent;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Sell2 extends AppCompatActivity {


    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private ImageView imageView;
    private EditText txtImageName;
    private Uri imgUri;
    private FirebaseAuth firebaseAuth;
    String Used_id;

    public static final String FB_STORAGE_PATH="image/";
    public static final String FB_DATABASE_PATH="image";
    public static final int REQUEST_CODE=1234;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell2);

        imageView=(ImageView) findViewById(R.id.imageView);
        txtImageName=(EditText)findViewById(R.id.txtImageName);

        firebaseAuth = FirebaseAuth.getInstance();

        mStorageRef =FirebaseStorage.getInstance().getReference();
        mDatabaseRef=FirebaseDatabase.getInstance().getReference();

        imageView=(ImageView) findViewById(R.id.imageView);
        txtImageName=(EditText)findViewById(R.id.txtImageName);

        firebaseAuth = FirebaseAuth.getInstance();
        Used_id = firebaseAuth.getCurrentUser().getUid();
    }


    public void btnBrowse_Click(View v)
    {

        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select image"),REQUEST_CODE);

    }



    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            imgUri = data.getData();

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                imageView.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getImageExt(Uri uri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @SuppressWarnings("VisibleForTests")
    public void btnUpload_Click(View v){
        if(imgUri!=null){

            final ProgressDialog dialog=new ProgressDialog(this);
            dialog.setTitle("Uploading Image");
            dialog.show();

            StorageReference ref= mStorageRef.child(FB_STORAGE_PATH + System.currentTimeMillis() +"."+getImageExt(imgUri));

            ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Image Uploaded",Toast.LENGTH_SHORT).show();
                    ImageUpload imageUpload=new ImageUpload(txtImageName.getText().toString(),taskSnapshot.getDownloadUrl().toString());

                    String uploadId=mDatabaseRef.push().getKey();
                    mDatabaseRef.child(uploadId).setValue(imageUpload);

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                      dialog.dismiss();
                      Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                  }
              })


                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                       double progress =(100 * taskSnapshot.getBytesTransferred())/ taskSnapshot.getTotalByteCount();
                       dialog.setMessage("Uploaded" + (int)progress+"%");
                   }
               });
        }

        else{
            Toast.makeText(getApplicationContext(),"Please select image",Toast.LENGTH_SHORT).show();
        }

    }

    public void btnShowListImage_Click(View v)
    {

        Intent i=new Intent(Sell2.this,ImageListActivity.class);
        startActivity(i);
    }


}
