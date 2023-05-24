package com.example.garbage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.garbage.Entidade.Requirement;
import com.example.garbage.dao.RequirementDao;

import java.util.ArrayList;

public class RequirementList extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requirement_list_activity);

        ListView listView =  findViewById(R.id.listView);
        RequirementDao rDao = new RequirementDao();
        ArrayList<String> valores = new ArrayList<>();

        ArrayList<Requirement> requirementList = rDao.getRequirements(getApplicationContext());
        System.out.println("TAMANHO DA LISTA:? " + requirementList.size());
        for(int i = 0; i < requirementList.size(); i++)
        {
            valores.add(
                "Código: " + requirementList.get(i).getId()+"     " +
                "\nRequerimento: " + requirementList.get(i).getName() +
                "\nDescription: " + requirementList.get(i).getDescription() +
                "\nRegisterDate: " + requirementList.get(i).getRegisterDate() +
                "\nImportance: " + requirementList.get(i).getImportance() +
                "\nDificulty: " + requirementList.get(i).getDificulty() +
                "\nDevelopment Time: " + requirementList.get(i).getDevelTime() +
                "\nImg: " + requirementList.get(i).getIdImg() +
                "\nLocation: "+ requirementList.get(i).getLatitude() + " " + requirementList.get(i).getLongitude()+"\n"
            );
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, valores);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Recupera o item que foi clicado
                Object item = parent.getItemAtPosition(position);

                Intent it;
                it  = new Intent(getApplicationContext(), EditRequirement.class);
                it.putExtra("id", item.toString().substring(8,12));
                startActivity(it );

            }
        });
    }
}
