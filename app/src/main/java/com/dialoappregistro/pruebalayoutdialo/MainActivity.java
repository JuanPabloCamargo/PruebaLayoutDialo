package com.dialoappregistro.pruebalayoutdialo;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.zxing.Result;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.zip.Inflater;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity {


    MaterialSearchView materialSearchView;
    String[] list;
    ZXingScannerView Escaner;
    String Atras;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        materialSearchView = (MaterialSearchView)findViewById(R.id.SearchView);
        materialSearchView.closeSearch();
        materialSearchView.setSuggestions(list);
        SolicitarPermisos();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Escanear();
                /*Snackbar.make(view, "Realiza lectura de codigo QR", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        list =new String[] {"Texto 1","Texto 2","Texto 3","Texto 4"};


        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //crear aca el filtro de busqueda
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //realizar cambios en tiempo real
                return false;
            }
        });

        materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                Log.d("Info","Realiza la busqueda");

            }

            @Override
            public void onSearchViewClosed() {

            }
        });


    }

    private void SolicitarPermisos() {
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M)
        {
            Log.d("INFO","API < 23");
        }
        else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(getApplicationContext(),"Permisos Activados",Toast.LENGTH_LONG).show();
        }
        else if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_DENIED)
        {
            //Toast.makeText(getApplicationContext(),"No cuenta con Permisos para la camara",Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},100);
        }


    }

/*
    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(MainActivity.this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe Aceptar los permisos para el funcionamiento de la app");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int i) {
                requestPermissions(new String[]{CAMERA_SERVICE},100);

            }
        });
        dialogo.show();
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.Busqueda);
        materialSearchView.setMenuItem(item);

        return true;

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_qr:
                Escanear();
                Log.i("ActionBar", "Codigo Qr");
                //Toast.makeText(getApplicationContext(), "Realiza lectura de QR", Toast.LENGTH_LONG).show();
                //Intent AddPhrase = new Intent(getApplicationContext(), AddPhrase.class);
                //startActivity(AddPhrase);
                return true;

            case R.id.Busqueda:
                Log.d("Boton","Busqueda");
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        if (Atras.equals(null)) {
        }
        if (Atras.equals("SI")) {
            Escaner.stopCamera();
            Toast.makeText(getApplicationContext(), "Lectura QR cancelada", Toast.LENGTH_LONG).show();
            Escaner.stopCamera();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("QR", "");
            startActivity(intent);
        }
        if (Atras.equals("NO")) {
        }
    }

    public void Escanear()
    {
        Escaner = new ZXingScannerView(this);
        Escaner.setResultHandler(new LeerCodigo());
        setContentView(Escaner);
        Atras = "SI";
        Escaner.startCamera();
    }

    public class LeerCodigo implements ZXingScannerView.ResultHandler {

        @Override
        public void handleResult(Result result) {
            String Resultado = result.getText();
            String Res = Resultado;
            Log.d("RESULT","DATA: "+Res);
            Escaner.removeAllViews();
            Escaner.stopCamera();
            Toast.makeText(getApplicationContext(),"Data :"+Res,Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

        }
    }
}
