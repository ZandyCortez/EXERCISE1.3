package com.pm1.exercise13;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pm1.exercise13.configuracion.SQLiteConexion;
import com.pm1.exercise13.configuracion.Transacciones;

public class ActivityActualizar extends AppCompatActivity {

    private String id;
    private String nombre;
    private String apellido;
    private String edad;
    private String correo;
    private String direccion;

    Context c;

    SQLiteConexion conexion;
    EditText txtid,txtnombre,txtapellido,txtedad,txtcorreo,txtdir;
    int countcarga = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);

        conexion = new SQLiteConexion(this, Transacciones.NameDataBase, null, 1);
        Button btnactualizar = (Button) findViewById (R.id.btnupdate);
        Button btnatrass= (Button) findViewById(R.id.btnatrass);

        c=this;

        Bundle extras = getIntent().getExtras();
        id = extras.getString("id");
        nombre = extras.getString("nombre");
        apellido = extras.getString("ape");
        edad = extras.getString("edad");
        correo = extras.getString("correo");
        direccion = extras.getString("dir");

        txtid = (EditText) findViewById (R.id.txtID);
        txtnombre = (EditText) findViewById (R.id.txtname);
        txtapellido = (EditText) findViewById (R.id.txtape);
        txtedad = (EditText) findViewById (R.id.txttele);
        txtcorreo = (EditText) findViewById (R.id.txtemail);
        txtdir = (EditText) findViewById (R.id.txtnotas);

        txtid.setText(id);
        txtnombre.setText(nombre);
        txtapellido.setText(apellido);
        txtedad.setText(edad.toString());
        txtcorreo.setText(correo);
        txtdir.setText(direccion);

        btnatrass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityListView.class);
                startActivity(intent);
            }
        });

        btnactualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Actualizar();
            }
        });

    }
    public void mensaje(String msj){
        Toast.makeText(getApplicationContext(), msj, Toast.LENGTH_LONG).show();
    }
    private void Actualizar()
    {
        if(!txtnombre.getText().toString().isEmpty()){
            if(!txtapellido.getText().toString().isEmpty()){
                if(!txtedad.getText().toString().isEmpty()){
                    if(!txtcorreo.getText().toString().isEmpty()){
                        if(!txtdir.getText().toString().isEmpty()){
                            SQLiteDatabase db = conexion.getWritableDatabase ();
                            String [] params = {txtid.getText (). toString ()};
                            ContentValues valores = new ContentValues();

                            valores.put (Transacciones.nombres, txtnombre.getText (). toString ());
                            valores.put (Transacciones.apellidos, txtapellido.getText (). toString ());
                            valores.put (Transacciones.edad, txtedad.getText (). toString ());
                            valores.put (Transacciones.correo, txtcorreo.getText (). toString ());
                            valores.put (Transacciones.direccion, txtdir.getText (). toString ());

                            db.update (Transacciones.tablaPersonas, valores, Transacciones.id + "=?", params);
                            Toast.makeText (getApplicationContext (), "PERSONA actualizado", Toast.LENGTH_LONG).show();
                        }else{
                            txtdir.requestFocus();
                            mensaje("Debe ingresar la direccion! \n PERSONA NO GUARDADO");
                        }
                    }else{
                        txtcorreo.requestFocus();
                        mensaje("Debe ingresar el correo! \n PERSONA NO GUARDADO");
                    }
                }else{
                    txtedad.requestFocus();
                    mensaje("Debe ingresar la Edad! \n PERSONA NO GUARDADO");
                }
            }else{
                txtapellido.requestFocus();
                mensaje("Debe ingresar el apellido! \n PERSONA NO GUARDADO");
            }
        }else{
            txtnombre.requestFocus();
            mensaje("Debe ingresar el nombre completo! \n PERSONA NO GUARDADO");
        }
    }
}