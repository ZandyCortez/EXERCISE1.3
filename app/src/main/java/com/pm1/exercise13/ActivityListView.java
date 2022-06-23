package com.pm1.exercise13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.pm1.exercise13.R;

import java.util.ArrayList;

import com.pm1.exercise13.configuracion.Personas;
import com.pm1.exercise13.configuracion.SQLiteConexion;
import com.pm1.exercise13.configuracion.Transacciones;

public class ActivityListView extends AppCompatActivity {

    SQLiteConexion conexion;
    ListView lista;
    ArrayList<Personas>listac;
    ArrayList<String> ArregloContactos;
    EditText id,nombre,telefono,nota;
    Spinner pais;
    Context contexto;
    Integer Sleccionad=-1;
    Integer count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        conexion=new SQLiteConexion(this, Transacciones.NameDataBase, null, 1);
        lista=(ListView) findViewById(R.id.lista);

        contexto = this;

        ObtenerListaContactos();

        ArrayAdapter adp = new ArrayAdapter( this, android.R.layout.simple_list_item_checked, ArregloContactos);
        lista.setAdapter(adp);

        //Area de Botones
        Button btneliminar = (Button) findViewById (R .id.btneliminar);
        Button btnatras= (Button) findViewById(R.id.btnatras);

        id = (EditText) findViewById (R.id.txtid);
//        pais = (Spinner) findViewById (R.id.combopais);
//        nombre = (EditText) findViewById (R.id.txtnombre);
//        telefono = (EditText) findViewById (R.id.txttel);
//        nota = (EditText) findViewById (R.id.txtnota);

        id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adp.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Button btninsertar = (Button)findViewById(R.id.btninsertar);
        btninsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityIngresar.class);
                startActivity(intent);
            }
        });

        Button btnactualizar = (Button) findViewById (R.id.btnactualizar);
        btnactualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Actualizar();
                if(Sleccionad==-1){
                    Toast.makeText(contexto,"Seleccione una Persona primero!", Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(getApplicationContext(), ActivityActualizar.class);
                    intent.putExtra("id",listac.get(Sleccionad).getId().toString());
                    intent.putExtra("nombre",listac.get(Sleccionad).getNombres());
                    intent.putExtra("ape",listac.get(Sleccionad).getApellidos());
                    intent.putExtra("edad",listac.get(Sleccionad).getEdad().toString());
                    intent.putExtra("correo",listac.get(Sleccionad).getCorreo());
                    intent.putExtra("dir",listac.get(Sleccionad).getDireccion());
                    startActivity(intent);
                }
            }
        });

        btnatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            CheckedTextView textview = (CheckedTextView)view;
            textview.setChecked(!textview.isChecked());
            count++;
            if(count==2 && Sleccionad==i){
                String s = lista.getItemAtPosition(i).toString();
                btnactualizar.callOnClick();
                count=0;
            }
            if(count>=2){count=0;}
            Sleccionad = i;
//            id.setText(i+" "+count.toString());
//            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        }
        });
        btneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Sleccionad==-1){
                    Toast.makeText(contexto,"Seleccione una Persona primero!", Toast.LENGTH_LONG).show();
                }else{
                    Eliminar();
                }
            }

            private void Eliminar()
            {
                SQLiteDatabase db = conexion.getWritableDatabase ();

                String [] params = {listac.get(Sleccionad).getId().toString()};
                String wherecond = Transacciones.id + "=?";
                db.delete (Transacciones.tablaPersonas, wherecond, params) ;
                Toast.makeText (getApplicationContext (), "Persona eliminada", Toast.LENGTH_LONG) .show ();

            }
        });
    }


//    private void Actualizar()
//    {
//        SQLiteDatabase db = conexion.getWritableDatabase ();
//
//        String [] params = {id.getText (). toString ()};
//        ContentValues valores = new ContentValues();
//
//        valores.put (Transacciones.id, id.getAdapter (). toString ( ));
//        valores.put (Transacciones.nombres, nombre.getAdapter (). toString ( ));
//        valores.put (Transacciones.nombre, nombre.getText (). toString ());
//        valores.put (Transacciones.telefono, telefono.getText (). toString ());
//        valores.put (Transacciones.nota, nota .getText (). toString ());
//
//        db.update (Transacciones.tablaPersonas, valores, Transacciones.id + "=?", params);
//        Toast.makeText (getApplicationContext (), "Contacto actualizado", Toast.LENGTH_LONG).show();
//
//    }

    private void ObtenerListaContactos()
    {
        SQLiteDatabase db= conexion.getReadableDatabase();
        Personas lista_contacto = null;
        listac = new ArrayList<Personas>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Transacciones.tablaPersonas, null);


        while(cursor.moveToNext())
        {
            lista_contacto=new Personas();
            lista_contacto.setId(cursor.getInt(0));
            lista_contacto.setNombres(cursor.getString(1));
            lista_contacto.setApellidos(cursor.getString(2));
            lista_contacto.setEdad(cursor.getInt(3));
            lista_contacto.setCorreo(cursor.getString(4));
            lista_contacto.setDireccion(cursor.getString(5));
            listac.add(lista_contacto);
        }

        cursor.close();
        fillllam();
    }

    private void fillllam()
    {
        {
            ArregloContactos=new ArrayList<String>();

            for (int i=0; i< listac.size(); i++)
            {
                ArregloContactos.add(listac.get(i).getId() + " | "
                        +listac.get(i).getNombres()+" | "
                        +listac.get(i).getApellidos()+" | "
                        +listac.get(i).getEdad().toString()+" | "
                        +listac.get(i).getDireccion());
            }
        }
    }
}