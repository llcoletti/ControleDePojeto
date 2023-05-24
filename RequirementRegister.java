package com.example.garbage;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.garbage.Entidade.Image;
import com.example.garbage.Entidade.MinhaLocalizacao;
import com.example.garbage.Entidade.Project;
import com.example.garbage.Entidade.Requirement;
import com.example.garbage.dao.ImageDao;
import com.example.garbage.db.MeuSQLite;

import java.io.ByteArrayOutputStream;

public class RequirementRegister extends AppCompatActivity {
    MeuSQLite gerenciadorBancoDeDados;
    SQLiteDatabase bancoDeDados;

    ActivityResultLauncher<Intent> activityResultLauncher;
    ImageView imgFoto;

    Bitmap bitmap;
    TextView txtCoordenadas;

    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requirement_activity);

        Button createRequirement, btnCamera, btnGps;
        EditText nameInput;
        EditText description;
        EditText developmentTimeValue;
        final DatePicker registerDateValue;


        nameInput = findViewById(R.id.nameInput);
        description = findViewById(R.id.descriptionLabelValue);
        registerDateValue =  findViewById(R.id.registerDateValue);
        developmentTimeValue = findViewById(R.id.developmentTimeValue);

        //GPS

        MinhaLocalizacao localizacao = new MinhaLocalizacao();
        LocationManager mLocManager  = (LocationManager) getSystemService(RequirementRegister.this.LOCATION_SERVICE);
        LocationListener mLocListener = new MinhaLocalizacaoListener();
        txtCoordenadas = findViewById(R.id.txtCoordenadas);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)   != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RequirementRegister.this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(RequirementRegister.this, new String[] {android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            ActivityCompat.requestPermissions(RequirementRegister.this, new String[] {android.Manifest.permission.ACCESS_NETWORK_STATE}, 1);
            return;
        }
        mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocListener);

        btnGps = findViewById(R.id.btnGps);
        btnGps.setOnClickListener(view -> {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)   != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(RequirementRegister.this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                ActivityCompat.requestPermissions(RequirementRegister.this, new String[] {android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                ActivityCompat.requestPermissions(RequirementRegister.this, new String[] {android.Manifest.permission.ACCESS_NETWORK_STATE}, 1);
                return;
            }
            mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocListener);
            if (mLocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                String texto = "Latitude.: " + MinhaLocalizacaoListener.latitude + "   Longitude: " + MinhaLocalizacaoListener.longitude + "\n";
                txtCoordenadas.setText(texto);

                if(MinhaLocalizacaoListener.latitude > 0) {
                    localizacao.setLatitude(MinhaLocalizacaoListener.latitude);
                    localizacao.setLongitude(MinhaLocalizacaoListener.longitude);
                }
            } else {
                Toast.makeText(RequirementRegister.this, "GPS DESABILITADO.", Toast.LENGTH_LONG).show();
            }
        });

        //CAMERA
        imgFoto = findViewById(R.id.imgFoto);
        btnCamera = findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            activityResultLauncher.launch(intent);
        });

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Bundle bundle = result.getData().getExtras();
                bitmap = (Bitmap) bundle.get("data");
                imgFoto.setImageBitmap(bitmap);
            }
        });


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


        createRequirement = findViewById(R.id.createRequirement);
        createRequirement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameValue = nameInput.getText().toString();
                String descriptionValue = description.getText().toString();
                String registerDate = Integer.toString(registerDateValue.getDayOfMonth()) +"/"+ Integer.toString(registerDateValue.getMonth()) +"/"+ Integer.toString(registerDateValue.getYear());
                String importance = importanceValue();
                String dificulty = dificultyValue();
                String developmentTime = developmentTimeValue.getText().toString();
                int imgId = 0;

                //save image
                if(bitmap != null)
                {
                    try{
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] dadosImagem = stream.toByteArray();

                        Image newImage = new Image();
                        newImage.setName(nameValue+"-"+registerDate);
                        newImage.setImage(dadosImagem);

                        ImageDao imgDao = new ImageDao();
                        imgDao.insertImage(getApplicationContext(), newImage);

                        imgId = imgDao.getImageId(getApplicationContext(), newImage.getName());

                    }catch(Exception e)
                    {
                        System.out.println("RequirementeRegister - Erro ao converter e enviar img para ImageDAO");
                    }
                }
                try{
                    Requirement newRequirement = new Requirement(nameValue, descriptionValue, registerDate, importance, dificulty, developmentTime, imgId, Double.toString(localizacao.getLatitude()), Double.toString(localizacao.getLongitude()));
                    dbRegisterRequirement(newRequirement);
                    Toast.makeText(RequirementRegister.this, "Requerimento " + nameInput.getText().toString() + " registrado", Toast.LENGTH_SHORT).show();

                    Intent it;
                    it  = new Intent(getApplicationContext(), MainMenu.class);
                    startActivity(it );
                }catch(Exception e)
                {
                    Toast.makeText(RequirementRegister.this, "Erro ao criar novo requerimento", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public void dbRegisterRequirement(Requirement newRequirement){
        //Database
        gerenciadorBancoDeDados = new MeuSQLite(this, "aplicacaodb");
        bancoDeDados = gerenciadorBancoDeDados.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("name",  newRequirement.getName());
        valores.put("description",  newRequirement.getDescription());
        valores.put("registerDate",  newRequirement.getRegisterDate());
        valores.put("importance",  newRequirement.getImportance());
        valores.put("dificulty",  newRequirement.getDificulty());
        valores.put("developmentTime",  newRequirement.getDevelTime());
        valores.put("ref_img",  newRequirement.getIdImg());
        valores.put("latitude",  newRequirement.getLatitude());
        valores.put("longitude",  newRequirement.getLongitude());

        /* insere os valores na tabela "requirements" */
        long resultado = bancoDeDados.insert("requirements", null, valores);
        Log.i("INSERT REQUIREMENT", Long.toString(resultado));
        bancoDeDados.close();

    }
    public String importanceValue(){
        final CheckBox importanceOne = (CheckBox) findViewById(R.id.importanceOne);
        final CheckBox importanceTwo = (CheckBox) findViewById(R.id.importanceTwo);
        final CheckBox importanceThree = (CheckBox) findViewById(R.id.importanceThree);
        final CheckBox importanceFour = (CheckBox) findViewById(R.id.importanceFour);
        final CheckBox importanceFive = (CheckBox) findViewById(R.id.importanceFive);

        if(importanceOne.isChecked())
        {
            return "1";
        }
        if(importanceTwo.isChecked())
        {
            return "2";
        }
        if(importanceThree.isChecked())
        {
            return "3";
        }
        if(importanceFour.isChecked())
        {
            return "4";
        }
        if(importanceFive.isChecked())
        {
            return "5";
        }
        return "0";
    }

    public String dificultyValue(){
        final CheckBox dificultOne = (CheckBox) findViewById(R.id.dificultOne);
        final CheckBox dificultTwo = (CheckBox) findViewById(R.id.dificultTwo);
        final CheckBox dificultThree = (CheckBox) findViewById(R.id.dificultThree);
        final CheckBox dificultFour = (CheckBox) findViewById(R.id.dificultFour);
        final CheckBox dificultFive = (CheckBox) findViewById(R.id.dificultFive);

        if(dificultOne.isChecked())
        {
            return "1";
        }
        if(dificultTwo.isChecked())
        {
            return "2";
        }
        if(dificultThree.isChecked())
        {
            return "3";
        }
        if(dificultFour.isChecked())
        {
            return "4";
        }
        if(dificultFive.isChecked())
        {
            return "5";
        }
        return "0";
    }

}
