package com.example.heat_beat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterAct extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword,inputLayoutcpass;
    Button buttonReg;
    TextInputEditText inputName, inputEmail, inputPassword,cpassword;
    RadioGroup gender;
    RadioButton malerdio,femaleradio;
    String g=null;
    RadioButton radioButton;
    UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        inputLayoutName =  findViewById(R.id.name_edittext);
        inputLayoutPassword = findViewById(R.id.password_edittext);
        inputLayoutEmail =  findViewById(R.id.email_edittext);
        inputLayoutcpass =  findViewById(R.id.confirm_passedit);
        malerdio = findViewById(R.id.male_radio);
        femaleradio = findViewById(R.id.female_radio);
        buttonReg = (Button) findViewById(R.id.regbtn);

        inputName = findViewById(R.id.name);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.pass);
        cpassword = findViewById(R.id.cpass);

        sharedPreferences = getApplicationContext().getSharedPreferences("Register_User", 0);
        editor = sharedPreferences.edit();

        gender = (RadioGroup) findViewById(R.id.gendergroup);

//        g= (String) radioButton.getText();
//        Toast.makeText(this, ""+g, Toast.LENGTH_SHORT).show();

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString();
                String email = inputEmail.getText().toString();
                String pass = inputPassword.getText().toString();
                String cpass = cpassword.getText().toString();
               gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                   @Override
                   public void onCheckedChanged(RadioGroup group, int checkedId) {
                       switch (checkedId){
                           case R.id.male_radio:
                               g="male";
                               break;
                           case R.id.female_radio:
                               g= "female";
                               break;
                       }
                   }
               });

                if(name.length()<=0){
                    Toast.makeText(RegisterAct.this, "Enter name", Toast.LENGTH_SHORT).show();
                }
                else if( email.length()<=0){
                    Toast.makeText(RegisterAct.this, "Enter email", Toast.LENGTH_SHORT).show();
                }
                else if( pass.length()<=0){
                    Toast.makeText(RegisterAct.this, "Enter password", Toast.LENGTH_SHORT).show();
                }
                else if(!pass.equals(cpass)){
                    Toast.makeText(RegisterAct.this, "Password dont match", Toast.LENGTH_SHORT).show();
                }
                else if(!(malerdio.isChecked() || femaleradio.isChecked())){
                    Toast.makeText(RegisterAct.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
                }
                else{
                    // as now we have information in string. Lets stored them with the help of editor
                    editor.putString("Name", name);
                    editor.putString("Email",email);
                    editor.putString("txtPassword",pass);
                    editor.putString("Gender",g);
                    editor.commit();

                    Toast.makeText(RegisterAct.this, "Registration Successfully done", Toast.LENGTH_SHORT).show();
                    Intent ob = new Intent(RegisterAct.this, LoginAct.class);
                    startActivity(ob);
                    finish();

                }   // commit the values

            }

        });
    }


}