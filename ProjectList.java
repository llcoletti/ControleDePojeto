package com.example.garbage;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.garbage.db.MeuSQLite;

import java.sql.Array;
import java.util.ArrayList;


public class ProjectList  extends AppCompatActivity {

    MeuSQLite gerenciadorBancoDeDados;
    SQLiteDatabase bancoDeDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_list_activity);

        ListView listView =  findViewById(R.id.listView);
        gerenciadorBancoDeDados = new MeuSQLite(this, "aplicacaodb");


        if(gerenciadorBancoDeDados == null)
        {
            System.out.println("gerenciadorBancoDeDados é nulo");
        }else{

                bancoDeDados = gerenciadorBancoDeDados.getReadableDatabase();
                if(bancoDeDados == null)
                {
                    System.out.println("bancoDeDados Null");
                }else{
                    String[] campos_item = {"id", "name" ,"startDate", "expectDate"};
                    Cursor lista = bancoDeDados.query("projects", campos_item, null, null, null, null, null );

                    ArrayList<String> valores = new ArrayList<String>();

                    lista.moveToFirst();
                    for (int x=1; x <= lista.getCount(); x++)
                    {
                        String id = lista.getString(lista.getColumnIndexOrThrow("id"));
                        String nome = lista.getString(lista.getColumnIndexOrThrow("name"));
                        String startDate = lista.getString(lista.getColumnIndexOrThrow("startDate"));
                        String expectDate = lista.getString(lista.getColumnIndexOrThrow("expectDate"));
                        valores.add("Código: "+ id+ "     \nNome do projeto           : " +nome + "\nData de início                : " + startDate + "\nData término previsto  : " + expectDate +"\n");
                        lista.moveToNext();
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, valores);
                    listView.setAdapter(adapter);

                    bancoDeDados.close();
                }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Recupera o item que foi clicado
                Object item = parent.getItemAtPosition(position);

                System.out.println(item);

                Intent it;
                it  = new Intent(getApplicationContext(), ProjectRegister.class);
                it.putExtra("id", item.toString().substring(8,12));
                startActivity(it );

            }
        });
    }
}
