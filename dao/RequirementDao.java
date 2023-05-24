package com.example.garbage.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.garbage.Entidade.Requirement;
import com.example.garbage.db.MeuSQLite;

import java.util.ArrayList;

public class RequirementDao {
    MeuSQLite SQLiteDB;

    public Requirement getRequirementById(Context context, int id)
    {
        SQLiteDB = new MeuSQLite(context, "aplicacaodb");
        SQLiteDatabase db = SQLiteDB.getReadableDatabase();

        String[] campos_item = {"id", "name" ,"description","registerDate", "importance", "dificulty", "developmentTime", "ref_img", "latitude", "longitude"};
        Cursor requirements = db.query("requirements", campos_item, "id=" + id, null, null, null, null );

        requirements.moveToFirst();
        for (int x=1; x <= requirements.getCount(); x++) {
            int idRequire = Integer.parseInt(requirements.getString(requirements.getColumnIndexOrThrow("id")));
            if(idRequire == id)
            {
                String name = requirements.getString(requirements.getColumnIndexOrThrow("name"));
                String description = requirements.getString(requirements.getColumnIndexOrThrow("description"));
                String registerDate = requirements.getString(requirements.getColumnIndexOrThrow("registerDate"));
                String importance = requirements.getString(requirements.getColumnIndexOrThrow("importance"));
                String dificulty = requirements.getString(requirements.getColumnIndexOrThrow("dificulty"));
                String develTime = requirements.getString(requirements.getColumnIndexOrThrow("developmentTime"));
                int ref_img = Integer.parseInt(requirements.getString(requirements.getColumnIndexOrThrow("ref_img")));
                String latitude = requirements.getString(requirements.getColumnIndexOrThrow("latitude"));
                String longitude = requirements.getString(requirements.getColumnIndexOrThrow("longitude"));

                Requirement newRequirement = new Requirement(idRequire ,name, description, registerDate, importance, dificulty, develTime, ref_img, latitude, longitude );
                db.close();
                return newRequirement;
            }
        }
        return null;
    }

    public boolean updateRequirement(Context context, Requirement newRequirement)
    {

        try{
            SQLiteDB = new MeuSQLite(context, "aplicacaodb");
            SQLiteDatabase db = SQLiteDB.getWritableDatabase();

            ContentValues valores = new ContentValues();
            valores.put("name",  newRequirement.getName());
            valores.put("description",  newRequirement.getDescription());
            valores.put("registerDate",  newRequirement.getRegisterDate());
            valores.put("importance",  newRequirement.getImportance());
            valores.put("dificulty",  newRequirement.getDificulty());
            valores.put("developmentTime",  newRequirement.getDevelTime());
            if(newRequirement.getIdImg() != 0)
            {
                valores.put("ref_img",  newRequirement.getIdImg());
            }
            valores.put("latitude",  newRequirement.getLatitude());
            valores.put("longitude",  newRequirement.getLongitude());

            db.update("requirements", valores, "id=" + newRequirement.getId(), null);
            return true;
        }catch(Exception e)
        {
            System.out.println("Erro ao atualizar requerimento: "+ newRequirement.getId());
            return false;
        }
    }

    public ArrayList<Requirement> getRequirements(Context context)
    {

        SQLiteDB = new MeuSQLite(context, "aplicacaodb");
        SQLiteDatabase db = SQLiteDB.getReadableDatabase();

        String[] campos_item = {"id", "name" ,"description","registerDate", "importance", "dificulty", "developmentTime", "ref_img", "latitude", "longitude"};

        Cursor requirementList = db.query("requirements", campos_item, null, null, null, null, null );

        ArrayList<Requirement> valores = new ArrayList<Requirement>();

        requirementList.moveToFirst();
        System.out.println(requirementList.getCount());
        for (int x=1; x <= requirementList.getCount(); x++)
        {
            int id = Integer.parseInt(requirementList.getString(requirementList.getColumnIndexOrThrow("id")));
            String name = requirementList.getString(requirementList.getColumnIndexOrThrow("name"));
            String description = requirementList.getString(requirementList.getColumnIndexOrThrow("description"));
            String registerDate = requirementList.getString(requirementList.getColumnIndexOrThrow("registerDate"));
            String importance = requirementList.getString(requirementList.getColumnIndexOrThrow("importance"));
            String dificulty = requirementList.getString(requirementList.getColumnIndexOrThrow("dificulty"));
            String develTime = requirementList.getString(requirementList.getColumnIndexOrThrow("developmentTime"));
            int ref_img = Integer.parseInt(requirementList.getString(requirementList.getColumnIndexOrThrow("ref_img")));
            String latitude = requirementList.getString(requirementList.getColumnIndexOrThrow("latitude"));
            String longitude = requirementList.getString(requirementList.getColumnIndexOrThrow("longitude"));

            Requirement newRequirement = new Requirement(id ,name, description, registerDate, importance, dificulty, develTime, ref_img, latitude, longitude );

            System.out.println(newRequirement.getDevelTime());
            valores.add(newRequirement);
            requirementList.moveToNext();
        }

        db.close();
        return valores;
    }
}


