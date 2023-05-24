package com.example.garbage.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.garbage.Entidade.Image;
import com.example.garbage.db.MeuSQLite;

public class ImageDao {

    MeuSQLite gerenciadorBancoDeDados;
    SQLiteDatabase bancoDeDados;


    public Boolean insertImage(Context context, Image newImage)
    {
        MeuSQLite SQLiteDB = new MeuSQLite(context, "aplicacaodb");
        SQLiteDatabase db = SQLiteDB.getWritableDatabase();
        ContentValues valores = new ContentValues();

        if(newImage != null)
        {
            valores.put("name", newImage.getName());
            valores.put("image", newImage.getImage());

            try{
                long resultado = db.insert("requirement_images", null, valores);
                db.close();
                return true;
            }catch(Exception e)
            {
                System.out.println("ImageDAO - Erro ao inserir imagem no banco de dados \nErro:" + e);
                db.close();
                return false;
            }
        }
        db.close();
        return false;
    }

    public int getImageId(Context context, String name)
    {
        MeuSQLite SQLiteDB = new MeuSQLite(context, "aplicacaodb");
        SQLiteDatabase db = SQLiteDB.getReadableDatabase();

        String[] campos_item = {"id", "name" ,"image"};

        try
        {
            Cursor images = db.query("requirement_images", campos_item,"name like '%" + name + "%'", null, null, null, null  );
            images.moveToFirst();
            for (int x=1; x <= images.getCount(); x++) {
                int id = Integer.parseInt(images.getString(images.getColumnIndexOrThrow("id")));
                System.out.println("Retornada imagem id: " + id);
                return id;
            }
        }catch(Exception e)
        {
            System.out.println("Erro ao buscar imagem");
        }
        return 0;
    }

    public Image getImageById(Context context, int id)
    {
        MeuSQLite SQLiteDB = new MeuSQLite(context, "aplicacaodb");
        SQLiteDatabase db = SQLiteDB.getReadableDatabase();

        String[] campos_item = {"id", "name" ,"image"};

        try
        {
            Cursor images = db.query("requirement_images", campos_item,"id="+ id, null, null, null, null  );
            images.moveToFirst();
            for (int x=1; x <= images.getCount(); x++) {

                String name = images.getString(images.getColumnIndexOrThrow("name"));
                byte[] image = images.getBlob(images.getColumnIndexOrThrow("image"));

                Image resultImage = new Image(name, image);
                System.out.println("IMAGE DAO NAME: "+ name);
                System.out.println("IMAGE DAO image: "+ image);

                db.close();
                return resultImage;
            }
        }catch(Exception e)
        {
            System.out.println("Erro ao buscar imagem: " +e.getMessage());
        }
        db.close();
        return null;
    };

}
