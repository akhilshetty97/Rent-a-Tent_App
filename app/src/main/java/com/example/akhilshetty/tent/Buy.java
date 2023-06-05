package com.example.akhilshetty.tent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Buy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
    }

public void btnShowListImage_Click(View v)
{
    Intent i=new Intent(Buy.this,ImageListActivity.class);
    startActivity(i);
}

}
