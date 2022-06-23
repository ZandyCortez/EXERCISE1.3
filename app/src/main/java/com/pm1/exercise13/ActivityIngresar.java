package com.pm1.exercise13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pm1.exercise13.configuracion.SQLiteConexion;
import com.pm1.exercise13.configuracion.Transacciones;

public class ActivityIngresar extends AppCompatActivity {

    EditText nombres,apellidos,edad,correo,direccion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar);

        nombres = (EditText)findViewById(R.id.txtnombre);
        apellidos = (EditText)findViewById(R.id.txtapellidos);
        edad = (EditText)findViewById(R.id.txtedad);
        correo = (EditText)findViewById(R.id.txtcorreo);
        direccion = (EditText)findViewById(R.id.txtdireccion);

        Button btnatra2 = (Button)findViewById(R.id.btnatrass2);
        btnatra2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        Button btnagregar = (Button)findViewById(R.id.btnagregar);

        btnagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nombres.getText().toString().isEmpty()){mensaje("INGRESE NOMBRE"); nombres.requestFocus();}
                else{
                    if(apellidos.getText().toString().isEmpty()){mensaje("INGRESE APELLIDO"); apellidos.requestFocus();}
                    else{
                        if(edad.getText().toString().isEmpty()){mensaje("INGRESE EDAD CORRECTA"); edad.requestFocus();}
                        else{
                            if(correo.getText().toString().isEmpty()){mensaje("INGRESE CORREO"); correo.requestFocus();}
                            else{
                                if(direccion.getText().toString().isEmpty()){mensaje("INGRESE DIRECCION"); direccion.requestFocus();}
                                else{
                                    AgregarPersonas();
                                }
                            }
                        }
                    }
                }
            }
        });

    }
    public void mensaje(String msj){
        Toast.makeText(getApplicationContext(),msj,Toast.LENGTH_LONG).show();
    }
    private void AgregarPersonas() {
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDataBase,null,1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(Transacciones.nombres,nombres.getText().toString());
        valores.put(Transacciones.apellidos,apellidos.getText().toString());
        valores.put(Transacciones.edad,edad.getText().toString());
        valores.put(Transacciones.correo,correo.getText().toString());
        valores.put(Transacciones.direccion,direccion.getText().toString());

        Long resultado = db.insert(Transacciones.tablaPersonas,Transacciones.id,valores);
        Toast.makeText(getApplicationContext(),"Registro Ingresado! COD:"+resultado.toString(),Toast.LENGTH_LONG).show();

        db.close();

        limpiarPantalla();
    }

    private void limpiarPantalla() {
        nombres.setText("");
        apellidos.setText("");
        edad.setText("");
        correo.setText("");
        direccion.setText("");
    }
}