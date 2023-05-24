package com.example.garbage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.garbage.Entidade.User;
import com.example.garbage.dao.UserDao;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

    UserDao uDao = new UserDao();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        TextView email= (TextView) findViewById(R.id.email);
        TextView password= (TextView) findViewById(R.id.password);
        TextView loginError= (TextView) findViewById(R.id.loginError);
        Button loginbtn = (Button) findViewById(R.id.loginbtn);
        Button createAccount = (Button) findViewById(R.id.createAccount);

        createAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent it;
                it  = new Intent(getApplicationContext(), UserRegister.class);
                startActivity(it );
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String loginEmail = email.getText().toString();
                String loginPassword = password.getText().toString();

                User LoginUser = uDao.getLoginUser(getApplicationContext(),loginEmail,loginPassword);

                if(LoginUser != null)
                {
                    Intent it;
                    it  = new Intent(getApplicationContext(), MainMenu.class);
                    startActivity(it );
                }else {
                    Toast.makeText(Login.this, "Usuario ou senha inválidos", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}

