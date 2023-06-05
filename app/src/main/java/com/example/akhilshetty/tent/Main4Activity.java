package com.example.akhilshetty.tent;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Main4Activity extends AppCompatActivity {


    Button button;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    public void Sell(View view){

        Intent intent= new Intent(getApplicationContext(),Sell2.class);
        startActivity(intent);

    }

    public void Buy(View view){

        Intent intent= new Intent(getApplicationContext(),Buy.class);
        startActivity(intent);

    }


   protected void onStart()
   {
       super.onStart();
       mAuth.addAuthStateListener(mAuthListener);
   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        TextView textView = (TextView) findViewById(R.id.textView);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/chunkfive.otf");
        textView.setTypeface(typeface);
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        Typeface typeface1 = Typeface.createFromAsset(getAssets(), "fonts/pacifico.ttf");
        textView3.setTypeface(typeface1);



        button = (Button) findViewById(R.id.logout1);
        mAuth = FirebaseAuth.getInstance();

        mAuthListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() ==  null)
                {
                    startActivity(new Intent(Main4Activity.this, MainActivity.class));
                }
            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
            }
        });
    }

}
