package com.example.garbage;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.example.garbage.Entidade.Requirement;
import com.example.garbage.dao.ImageDao;
import com.example.garbage.dao.RequirementDao;

import java.io.ByteArrayOutputStream;

public class EditRequirement extends AppCompatActivity {

    RequirementDao rDao = new RequirementDao();
    ImageDao imgDao = new ImageDao();
    TextView txtCoordenadas;

    ImageView imgFoto;

    Bitmap bitmap;

    ActivityResultLauncher<Intent> activityResultLauncher;

    int editRequirementId;



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
        registerDateValue = findViewById(R.id.registerDateValue);
        developmentTimeValue = findViewById(R.id.developmentTimeValue);
        txtCoordenadas = findViewById(R.id.txtCoordenadas);
        imgFoto = findViewById(R.id.imgFoto);
        createRequirement = findViewById(R.id.createRequirement);



        Intent intent = getIntent();
        editRequirementId = Integer.parseInt(intent.getStringExtra("id").trim());

        Requirement editRequirement = rDao.getRequirementById(getApplicationContext(), editRequirementId);

        if (editRequirement != null) {
            createRequirement.setText("Atualizar");
            String data[] = editRequirement.getRegisterDate().split("/");
            String day = data[0];
            String month = data[1];
            String yeah = data[2];

            nameInput.setText(editRequirement.getName());
            description.setText(editRequirement.getDescription());
            registerDateValue.updateDate(
                    Integer.parseInt(yeah),
                    Integer.parseInt(month),
                    Integer.parseInt(day));
            developmentTimeValue.setText(editRequirement.getDevelTime());
            importanceValueCheck(Integer.parseInt(editRequirement.getImportance()));
            dificultyValueCheck(Integer.parseInt(editRequirement.getDificulty()));
            txtCoordenadas.setText("Latitue.: " + editRequirement.getLatitude() + " Longitude.: " + editRequirement.getLongitude());


            Image imgReference = imgDao.getImageById(getApplicationContext(), editRequirement.getIdImg());

            System.out.println("IMG REFERENCE: " + imgReference);
            if(imgReference != null)
            {
                Bitmap bmp = BitmapFactory.decodeByteArray(imgReference.getImage(), 0, imgReference.getImage().length);
                imgFoto.setImageBitmap(bmp);
            }
        }


        //GPS
        MinhaLocalizacao localizacao = new MinhaLocalizacao();
        LocationManager mLocManager = (LocationManager) getSystemService(EditRequirement.this.LOCATION_SERVICE);
        LocationListener mLocListener = new MinhaLocalizacaoListener();
        txtCoordenadas = findViewById(R.id.txtCoordenadas);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EditRequirement.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(EditRequirement.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            ActivityCompat.requestPermissions(EditRequirement.this, new String[]{android.Manifest.permission.ACCESS_NETWORK_STATE}, 1);
            return;
        }
        mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocListener);

        btnGps = findViewById(R.id.btnGps);
        btnGps.setOnClickListener(view -> {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(EditRequirement.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                ActivityCompat.requestPermissions(EditRequirement.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                ActivityCompat.requestPermissions(EditRequirement.this, new String[]{android.Manifest.permission.ACCESS_NETWORK_STATE}, 1);
                return;
            }
            mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocListener);
            if (mLocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                String texto = "Latitude.: " + MinhaLocalizacaoListener.latitude + "   Longitude: " + MinhaLocalizacaoListener.longitude + "\n";
                txtCoordenadas.setText(texto);

                if (MinhaLocalizacaoListener.latitude > 0) {
                    localizacao.setLatitude(MinhaLocalizacaoListener.latitude);
                    localizacao.setLongitude(MinhaLocalizacaoListener.longitude);
                }
            } else {
                Toast.makeText(EditRequirement.this, "GPS DESABILITADO.", Toast.LENGTH_LONG).show();
            }
        });


        //CAMERA
        imgFoto = findViewById(R.id.imgFoto);
        btnCamera = findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(view -> {
            Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            activityResultLauncher.launch(intentCamera);
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
                }else {
                }
                try{
                    Requirement updatedRequirement = new Requirement( editRequirementId, nameValue, descriptionValue, registerDate, importance, dificulty, developmentTime, imgId, Double.toString(localizacao.getLatitude()), Double.toString(localizacao.getLongitude()));
                    //dbRegisterRequirement(newRequirement);
                    rDao.updateRequirement(getApplicationContext(), updatedRequirement);


                    Toast.makeText(EditRequirement.this, "Requerimento " + nameInput.getText().toString() + " registrado", Toast.LENGTH_SHORT).show();

                    Intent it;
                    it  = new Intent(getApplicationContext(), MainMenu.class);
                    startActivity(it );
                }catch(Exception e)
                {
                    System.out.println("Erro ao atualizar requerimento: " + e.getMessage());
                    Toast.makeText(EditRequirement.this, "Erro ao atualizar requerimento", Toast.LENGTH_SHORT).show();
                }

            }
        });


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
    public void importanceValueCheck(int importanceChecked){
        final CheckBox importanceOne = (CheckBox) findViewById(R.id.importanceOne);
        final CheckBox importanceTwo = (CheckBox) findViewById(R.id.importanceTwo);
        final CheckBox importanceThree = (CheckBox) findViewById(R.id.importanceThree);
        final CheckBox importanceFour = (CheckBox) findViewById(R.id.importanceFour);
        final CheckBox importanceFive = (CheckBox) findViewById(R.id.importanceFive);

        if(importanceChecked ==1)
        {
            importanceOne.setChecked(true);
        }
        if(importanceChecked ==2)
        {
            importanceTwo.setChecked(true);
        }
        if(importanceChecked ==3)
        {
            importanceThree.setChecked(true);
        }
        if(importanceChecked ==4)
        {
            importanceFour.setChecked(true);
        }
        if(importanceChecked ==5)
        {
            importanceFive.setChecked(true);
        }

    }

    public void dificultyValueCheck(int dificultyChecked){
        final CheckBox dificultOne = (CheckBox) findViewById(R.id.dificultOne);
        final CheckBox dificultTwo = (CheckBox) findViewById(R.id.dificultTwo);
        final CheckBox dificultThree = (CheckBox) findViewById(R.id.dificultThree);
        final CheckBox dificultFour = (CheckBox) findViewById(R.id.dificultFour);
        final CheckBox dificultFive = (CheckBox) findViewById(R.id.dificultFive);

        if(dificultyChecked ==1)
        {
            dificultOne.setChecked(true);
        }
        if(dificultyChecked ==2)
        {
            dificultTwo.setChecked(true);
        }
        if(dificultyChecked ==3)
        {
            dificultThree.setChecked(true);
        }
        if(dificultyChecked ==4)
        {
            dificultFour.setChecked(true);
        }
        if(dificultyChecked ==5)
        {
            dificultFive.setChecked(true);
        }

    }
}
