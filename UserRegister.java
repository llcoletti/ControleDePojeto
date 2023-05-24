package com.example.garbage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.garbage.Entidade.Project;
import com.example.garbage.Entidade.User;
import com.example.garbage.dao.UserDao;
import com.example.garbage.db.MeuSQLite;

public class UserRegister extends AppCompatActivity {

    MeuSQLite gerenciadorBancoDeDados;
    SQLiteDatabase bancoDeDados;

    UserDao uDao = new UserDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);

        //Variables
        EditText nameValue;
        EditText cpfValue;
        final DatePicker birthDateValue;
        EditText emaiLValue;
        EditText passwordValue;
        EditText confirmPasswordValue;

        RadioGroup dataUsageSelected;
        int getCheckedButtonId;
        RadioButton getCheckRadioButton;
        CharSequence dataUsageSelectedValue;

        Button createUser;

        //Values
        nameValue = findViewById(R.id.nameValue);
        cpfValue = findViewById(R.id.cpfValue);
        birthDateValue =  findViewById(R.id.birthDateValue);
        emaiLValue = findViewById(R.id.emaiLValue);
        passwordValue = findViewById(R.id.passwordValue);
        confirmPasswordValue = findViewById(R.id.confirmPasswordValue);

        dataUsageSelected = (RadioGroup) findViewById(R.id.dataUsage);
        getCheckedButtonId =dataUsageSelected.getCheckedRadioButtonId();
        getCheckRadioButton = (RadioButton) findViewById(getCheckedButtonId);
        dataUsageSelectedValue = getCheckRadioButton.getText();

        //BotaoVoltar
        Button voltar;
        voltar = findViewById(R.id.voltar);
        voltar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent it;
                it  = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(it );
            }
        });


        createUser = findViewById(R.id.createUser);
        createUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String name = nameValue.getText().toString();
                String cpf  = cpfValue.getText().toString();
                String date = Integer.toString(birthDateValue.getDayOfMonth()) +"/"+ Integer.toString(birthDateValue.getMonth()) +"/"+ Integer.toString(birthDateValue.getYear());
                String email = emaiLValue.getText().toString();
                String password = passwordValue.getText().toString();
                String data = dataUsageSelectedValue.toString();

                try {
                    User newUser = new User(name, cpf, date, email, password, data );

                    System.out.println("UserRegister - email:" + email + "  password:" + password);

                    if(!uDao.getLoginByEmail(getApplicationContext(), email)) {
                        uDao.insertUser(getApplicationContext(), newUser);
                        Intent it;
                        it  = new Intent(getApplicationContext(), MainMenu.class);
                        startActivity(it );

                        Toast.makeText( UserRegister.this, "Usuario " + name + " registrado", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText( UserRegister.this, "Email ja cadastrado", Toast.LENGTH_LONG).show();
                    }


                }catch(Exception e)
                {
                    Toast.makeText( UserRegister.this, "Erro ao cadastrar Usuario", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}