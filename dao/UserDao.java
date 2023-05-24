package com.example.garbage.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.garbage.Entidade.Project;
import com.example.garbage.Entidade.User;
import com.example.garbage.db.MeuSQLite;

public class UserDao {

    MeuSQLite SQLiteDB;

    public boolean insertUser  (Context context, User userProject)
    {
        //Database
        SQLiteDB = new MeuSQLite(context, "aplicacaodb");

        try{

            System.out.println("UserDAO - InsertUser called");
            SQLiteDatabase db = SQLiteDB.getWritableDatabase();
            ContentValues valores = new ContentValues();
            valores.put("name",  userProject.getName().toString());
            valores.put("cpf", userProject.getCpf().toString() );
            valores.put("birthDate", userProject.getBirthDate().toString() );
            valores.put("email", userProject.getEmailValue().toString() );
            valores.put("password", userProject.getPassword().toString() );
            valores.put("dataUsage", userProject.getDataUsage().toString() );

            /* insere os valores na tabela "usuarios" */
            long resultado = db.insert("users", null, valores);
            db.close();
            System.out.println("Usuario inserido");

            return true;
        }catch(Exception e)
        {
            System.out.println("Erro ao inserir Usuario no banco de dados");
            return false;
        }
    }

    public User getLoginUser(Context context, String email, String password)
    {
        try{
            SQLiteDB = new MeuSQLite(context, "aplicacaodb");
            SQLiteDatabase db = SQLiteDB.getReadableDatabase();

            String[] campos_item = {"id", "name", "cpf", "birthDate" ,"email","password", "dataUsage"};

            Cursor usuarios = db.query("users", campos_item, "email='"+email+"'", null, null, null, null );

            usuarios.moveToFirst();
            for (int x=1; x <= usuarios.getCount(); x++) {

                if (usuarios.getString(usuarios.getColumnIndexOrThrow("password")).equals(password)) {
                    User LoginUser = new User(
                            usuarios.getString(usuarios.getColumnIndexOrThrow("id")),
                            usuarios.getString(usuarios.getColumnIndexOrThrow("name")),
                            usuarios.getString(usuarios.getColumnIndexOrThrow("cpf")),
                            usuarios.getString(usuarios.getColumnIndexOrThrow("birthDate")),
                            usuarios.getString(usuarios.getColumnIndexOrThrow("email")),
                            usuarios.getString(usuarios.getColumnIndexOrThrow("password")),
                            usuarios.getString(usuarios.getColumnIndexOrThrow("dataUsage"))
                    );
                    db.close();
                    return LoginUser;
                }
                usuarios.moveToNext();
            }
        }catch (Exception e)
        {
            System.out.println("Erro ao buscar usuario: " + e.getMessage());
        }
        return null;
    }

    public Boolean getLoginByEmail(Context context, String email)
    {
        try{
            SQLiteDB = new MeuSQLite(context, "aplicacaodb");
            SQLiteDatabase db = SQLiteDB.getReadableDatabase();

            String[] campos_item = {"id", "name", "cpf", "birthDate" ,"email","password", "dataUsage"};
            Cursor usuarios = db.query("users", campos_item, "email='"+email+"'", null, null, null, null );

            boolean alreadyRegistered;
            if(usuarios.getCount()>0)
            {
                alreadyRegistered = true;
            }else
            {
                alreadyRegistered = false;
            }

            db.close();
            return alreadyRegistered;

        }catch (Exception e)
        {
            System.out.println("Erro ao registrar novo usuario: " + e.getMessage());
        }
        return null;
    }
}
