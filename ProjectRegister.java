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
import android.widget.TextView;
import android.widget.Toast;

import com.example.garbage.Entidade.Project;
import com.example.garbage.Entidade.Requirement;
import com.example.garbage.dao.ProjectDao;
import com.example.garbage.db.MeuSQLite;

public class ProjectRegister extends AppCompatActivity {

    MeuSQLite gerenciadorBancoDeDados;
    SQLiteDatabase bancoDeDados;

    Boolean IS_UPDATE = false;
    Project updated_project = null;

    ProjectDao pDao = new ProjectDao();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_activity);

        //Variables
        EditText nameInput;
        Button createProject;
        Button voltar;
        final DatePicker finalDate;
        final DatePicker initialDate;

        //Values
        createProject = findViewById(R.id.createProject);
        nameInput=findViewById(R.id.nameInput);
        initialDate=(DatePicker)findViewById(R.id.startDateValue);
        finalDate=(DatePicker)findViewById(R.id.expectedDateValue);

        //Verifica se a tela sera update ou novo registro
        try {
            Intent intent = getIntent();
            int projectId = Integer.parseInt(intent.getStringExtra("id").trim());
            System.out.println("PROJECTID:" + projectId);
            updated_project = pDao.getProjectById(getApplicationContext(), projectId);

            if (updated_project != null) {
                createProject.setText("Atualizar");
                IS_UPDATE = true;

                //setStartDate
                if(updated_project.getStartDate() != null)
                {
                    System.out.println("STARTDATE: " + updated_project.getStartDate());

                    String startDate[] = updated_project.getStartDate().split("/");
                    initialDate.updateDate(
                            Integer.parseInt(startDate[2]),//year
                            Integer.parseInt(startDate[1]),//month
                            Integer.parseInt(startDate[0]));//day
                }


                String endDate[] = updated_project.getExpectDate().split("/");

                //SetName
                nameInput.setText(updated_project.getName());


                //setExpectDate

                if(updated_project.getExpectDate()!= null)
                {
                    finalDate.updateDate(
                            Integer.parseInt(endDate[2]),
                            Integer.parseInt(endDate[1]),
                            Integer.parseInt(endDate[0]));
                }

            }
        } catch (Exception e) {
            System.out.println("Não foi encontrado projeto para o id fornecido via intent:" + e.getMessage());
        }

        //BotaoVoltar
        voltar = findViewById(R.id.voltar);
        voltar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent it;
                it  = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(it );
            }
        });




        //CreateProject
        createProject.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String initialDateValue = Integer.toString(initialDate.getDayOfMonth()) +"/"+ Integer.toString(initialDate.getMonth()) +"/"+ Integer.toString(initialDate.getYear());
                String finalDateValue = Integer.toString(finalDate.getDayOfMonth()) +"/"+ Integer.toString(finalDate.getMonth()) +"/"+ Integer.toString(finalDate.getYear());
                String nameValue = nameInput.getText().toString();


                if(!IS_UPDATE)
                {
                    try {
                        Project newProject = new Project(nameValue, initialDateValue, finalDateValue);

                        pDao.insertProject(getApplicationContext(), newProject);
                        Toast.makeText( ProjectRegister.this, "Projeto " + nameInput.getText().toString() + " registrado", Toast.LENGTH_LONG).show();
                        Intent it;
                        it  = new Intent(getApplicationContext(), MainMenu.class);
                        startActivity(it );
                    }catch(Exception e)
                    {
                        Toast.makeText( ProjectRegister.this, "Erro ao cadastrar Projeto", Toast.LENGTH_LONG).show();
                    }
                }else{
                    try{
                        updated_project.setName(nameValue);
                        updated_project.setStartDate(initialDateValue);
                        updated_project.setExpectDate(finalDateValue);
                        pDao.updateProject(getApplicationContext(), updated_project);
                        Toast.makeText( ProjectRegister.this, "Projeto " + nameInput.getText().toString() + " Atualizado", Toast.LENGTH_LONG).show();
                        Intent it;
                        it  = new Intent(getApplicationContext(), MainMenu.class);
                        startActivity(it );
                    }catch (Exception e)
                    {
                        System.out.println("Erro ao atualizar projeto: " +e.getStackTrace());
                        Toast.makeText( ProjectRegister.this, "Erro ao atualizar projeto", Toast.LENGTH_LONG).show();
                    }

                    //UPDATE
                }

            }
        });
    }
}