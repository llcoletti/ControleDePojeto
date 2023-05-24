package com.example.garbage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.garbage.Entidade.Project;

public class MainMenu extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        Button listarProjetos;
        Button listarRequisitos;
        Button criarProjeto;
        Button criarRequisito;
        Button criarUsuario;

        listarProjetos = findViewById(R.id.listarProjetos);
        listarRequisitos = findViewById(R.id.listarRequisitos);
        criarProjeto = findViewById(R.id.criarProjeto);
        criarRequisito = findViewById(R.id.criarRequisito);
        criarUsuario = findViewById(R.id.criarUsuario);

        listarProjetos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent it;
                it  = new Intent(getApplicationContext(), ProjectList.class);
                startActivity(it );
            }
        });

        listarRequisitos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent it;
                it  = new Intent(getApplicationContext(), RequirementList.class);
                startActivity(it );
            }
        });

        criarProjeto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent it;
                it  = new Intent(getApplicationContext(), ProjectRegister.class);
                startActivity(it );
            }
        });

        criarRequisito.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent it;
                it  = new Intent(getApplicationContext(), RequirementRegister.class);
                startActivity(it );
            }
        });

        criarUsuario.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent it;
                it  = new Intent(getApplicationContext(), UserRegister.class);
                startActivity(it );
            }
        });

    }
}
