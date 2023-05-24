package com.example.garbage.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.garbage.Entidade.Project;
import com.example.garbage.Entidade.Requirement;
import com.example.garbage.db.MeuSQLite;

public class ProjectDao {

    MeuSQLite SQLiteDB;

    public Project getProjectById(Context context, int id)
    {
        SQLiteDB = new MeuSQLite(context, "aplicacaodb");
        SQLiteDatabase db = SQLiteDB.getReadableDatabase();

        String[] campos_item = {"id", "name" ,"startDate","expectDate"};
        Cursor requirements = db.query("projects", campos_item, "id=" + id, null, null, null, null );

        requirements.moveToFirst();
        for (int x=1; x <= requirements.getCount(); x++) {
            int idRequire = Integer.parseInt(requirements.getString(requirements.getColumnIndexOrThrow("id")));
            if(idRequire == id)
            {
                String name = requirements.getString(requirements.getColumnIndexOrThrow("name"));
                String description = requirements.getString(requirements.getColumnIndexOrThrow("startDate"));
                String registerDate = requirements.getString(requirements.getColumnIndexOrThrow("expectDate"));

                Project newProejct = new Project(idRequire ,name, description, registerDate );
                db.close();
                return newProejct;
            }
        }
        return null;
    }

    public boolean insertProject  (Context context, Project newProject)
    {
        //Database
        SQLiteDB = new MeuSQLite(context, "aplicacaodb");

        try{
            SQLiteDatabase db = SQLiteDB.getWritableDatabase();
            ContentValues valores = new ContentValues();
            valores.put("name",  newProject.getName().toString());
            valores.put("startDate", newProject.getStartDate().toString() );
            valores.put("expectDate", newProject.getExpectDate().toString() );

            /* insere os valores na tabela "projects" */
            long resultado = db.insert("projects", null, valores);
            db.close();
            return true;
        }catch(Exception e)
        {
            System.out.println("Erro ao inserir Projeto no banco de dados");
            return false;
        }
    }

    public boolean updateProject(Context context, Project updatedProject)
    {

        try{
            SQLiteDB = new MeuSQLite(context, "aplicacaodb");
            SQLiteDatabase db = SQLiteDB.getWritableDatabase();

            ContentValues valores = new ContentValues();
            valores.put("name",  updatedProject.getName());
            valores.put("startDate",  updatedProject.getStartDate());
            valores.put("expectDate",  updatedProject.getExpectDate());

            db.update("projects", valores, "id=" + updatedProject.getId(), null);
            return true;
        }catch(Exception e)
        {
            System.out.println("Erro ao atualizar requerimento: "+ updatedProject.getId());
            return false;
        }
    }
}
