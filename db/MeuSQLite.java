package com.example.garbage.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MeuSQLite extends SQLiteOpenHelper {
    public MeuSQLite(Context context, String database_name)
    {
        super(context, database_name, null, 14);
    }

    /* Método para criação das tabelas do banco de dados */
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("Create table projects " +
                "      (id integer primary key autoincrement, name text, startDate text, expectDate text)" );

        db.execSQL("Create table requirement_images " +
                "      (id integer primary key autoincrement, name text, image BLOB)" );

       db.execSQL("Create table requirements " +
                "      (id integer primary key autoincrement, name text, description text, registerDate text, importance text, dificulty text, developmentTime text, ref_img int , latitude text, longitude text);" );

       db.execSQL("Create table users " +
                "      (id integer primary key autoincrement, name text, cpf text, birthDate text, email text, password text, dataUsage text);" );

       System.out.println("onCreate - Database Criado");
    }

    public void onOpen(SQLiteDatabase db)
    {
        /*try{
            db.execSQL("Drop table projects");
            db.execSQL("Drop table requirement_images");
            db.execSQL("Drop table requirements");
            db.execSQL("Drop table users");
        }catch(Exception e)
        {
            System.out.println("RIP DROP");
        }finally
        {
            System.out.println("DROPADAO");
        }

        db.execSQL("Create table projects " +
                "      (id integer primary key autoincrement, name text, startDate text, expectDate text)" );

        db.execSQL("Create table requirement_images " +
                "      (id integer primary key autoincrement, name text, image BLOB)" );

        db.execSQL("Create table requirements " +
                "      (id integer primary key autoincrement, name text, description text, registerDate text, importance text, dificulty text, developmentTime text, int ref_img);" );

        db.execSQL("Create table users " +
                "      (id integer primary key autoincrement, name text, cpf text, birthDate text, email text, password text, dataUsage text);" );

        System.out.println("onOpen - Database Criado");*/
    }

    /* Método para controle de mudanças na estrutura do banco de dados */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion>oldVersion){
           try{
                db.execSQL("Drop table projects");
                db.execSQL("Drop table requirement_images");
                db.execSQL("Drop table requirements");
                db.execSQL("Drop table users");
            }catch(Exception e)
            {
                System.out.println("RIP DROP");
            }finally
            {
                System.out.println("DROPADAO");
            }

            db.execSQL("Create table projects " +
                    "      (id integer primary key autoincrement, name text, startDate text, expectDate text)" );

            db.execSQL("Create table requirement_images " +
                    "      (id integer primary key autoincrement, name text, image BLOB)" );

            db.execSQL("Create table requirements " +
                    "      (id integer primary key autoincrement, name text, description text, registerDate text, importance text, dificulty text, developmentTime text, ref_img int , latitude text, longitude text);" );

            db.execSQL("Create table users " +
                    "      (id integer primary key autoincrement, name text, cpf text, birthDate text, email text, password text, dataUsage text);" );

            System.out.println("onUpdate - Database Atualizado");

        }
    }
/*
    public SQLiteDatabase getReadableDatabase() {

        return null;
    }*/

}

