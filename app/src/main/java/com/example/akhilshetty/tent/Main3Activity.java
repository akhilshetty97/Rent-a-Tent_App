package com.example.akhilshetty.tent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener {


    private EditText et_name,et_email,et_username,et_password,et_cpwd;
    private String name,email,username,password,cpassword;
    Button regbtn;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        TextView mainText3 = (TextView) findViewById(R.id.mainText3);
        Typeface typeface6 = Typeface.createFromAsset(getAssets(), "fonts/nexarust.otf");
        mainText3.setTypeface(typeface6);



        firebaseAuth=FirebaseAuth.getInstance();



        progressDialog=new ProgressDialog(this);

        //et_name = (EditText) findViewById(R.id.FullName);
        et_email = (EditText) findViewById(R.id.et_email);
        //et_username = (EditText) findViewById(R.id.Username);
        et_password = (EditText) findViewById(R.id.et_password);
        et_cpwd = (EditText) findViewById(R.id.cpwd);
        regbtn= (Button) findViewById(R.id.SignUp);
        regbtn.setOnClickListener(this);


    }
    @Override
    public void onClick(View view) {
        register();
    }

    public void register(){
        initialize();
        if(!validate()) {
            Toast.makeText(this, "SignUp has failed", Toast.LENGTH_SHORT).show();
        }
        else{
            onSignupSuccess();
        }
    }


    public void onSignupSuccess()
    {
        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            Toast.makeText(Main3Activity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else
                        {
                            Toast.makeText(Main3Activity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        //Intent intent= new Intent(getApplicationContext(),MainActivity.class);
        //startActivity(intent);
    }


    public boolean validate(){

        boolean valid= true;
        /*if(name.isEmpty()||name.length()>32){
            et_name.setError("Please Enter Valid name.Maximum limit is 32 charcters");
            valid=false;
        }*/
        if(email.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            et_email.setError("Please Enter Valid Email Address");
            valid=false;
        }
        /*if(username.isEmpty()||username.length()<5||username.length()>10){
            et_username.setError("Please Enter Valid Username. Username should be between 5 to 10 charcters");
            valid=false;
        }*/
        if(password.isEmpty()){
            et_password.setError("Please Enter password");
            valid=false;
        }
        if(password.length()<8){
            et_password.setError("Password too weak.Enter atleast 8 characters");
            valid=false;
        }
        if(cpassword.isEmpty() ){
            et_password.setError("Please re-enter password");
            valid=false;
        }
        if(!match(password,cpassword)){
            et_password.setError("Passwords do not match");
            valid=false;
        }


        return valid;
    }

    private boolean match(String password, String cpassword) {
        if(password.equals(cpassword))
            return true;
        else
            return false;


    }

    public void SignUp(View v)
    {

    }


    public void initialize(){
        //name=et_name.getText().toString().trim();
        email=et_email.getText().toString().trim();
        //username=et_username.getText().toString().trim();
        password=et_password.getText().toString().trim();
        cpassword=et_cpwd.getText().toString().trim();
    }



}
