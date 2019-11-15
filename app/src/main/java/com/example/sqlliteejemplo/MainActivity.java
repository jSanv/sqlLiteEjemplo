package com.example.sqlliteejemplo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etcodigo, etnombre, etprecio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etcodigo = (EditText) findViewById(R.id.txtcodigo);
        etnombre = (EditText) findViewById(R.id.txtnombre);
        etprecio = (EditText) findViewById(R.id.txtprecio);

    }

    public void Registrar(View view) {
        ConexionsqLiteHelper admin = new ConexionsqLiteHelper(this, "bdejemplo", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String codigo = etcodigo.getText().toString();
        String nombre = etnombre.getText().toString();
        String precio = etprecio.getText().toString();


        if (!codigo.isEmpty() && !nombre.isEmpty() && !precio.isEmpty()) {
            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("nombre", nombre);
            registro.put("precio", precio);

            bd.insert("articulos", null, registro);
            bd.close();
            etcodigo.setText("");
            etnombre.setText("");
            etprecio.setText("");

            Toast.makeText(this, "Registro insertado", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this,"No dejar campos vacios", Toast.LENGTH_SHORT).show();
        }
    }

    //METODO PARA CONSULTAR

    public void Buscar(View view){
        ConexionsqLiteHelper admin = new ConexionsqLiteHelper(this, "bdejemplo", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String codigo = etcodigo.getText().toString();

        if(!codigo.isEmpty()){
            Cursor fila  = bd.rawQuery
                    ("select nombre, precio from articulos where codigo="+ codigo, null);

            if(fila.moveToFirst()){
                etnombre.setText(fila.getString(0));
                etprecio.setText(fila.getString(1));
                bd.close();
            } else {
                Toast.makeText(this,"Codigo no existe", Toast.LENGTH_SHORT).show();
                bd.close();
            }

        } else{
            Toast.makeText(this,"Debes introducir el codigo", Toast.LENGTH_SHORT).show();
        }
    }

    //METODO PARA ELIMINAR

    public void Eliminar(View view){
        ConexionsqLiteHelper admin = new ConexionsqLiteHelper(this, "bdejemplo", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String codigo = etcodigo.getText().toString();

        if(!codigo.isEmpty()){
             int cantidad =  bd.delete("articulos", "codigo= " + codigo, null);
             bd.close();
             etcodigo.setText("");
             etnombre.setText("");
             etprecio.setText("");

             if(cantidad == 1){
                 Toast.makeText(this, "Se ha eliminado correctamente", Toast.LENGTH_SHORT).show();
             } else {
                 Toast.makeText(this, "El codigo no existe", Toast.LENGTH_SHORT).show();
             }

        }else{
            Toast.makeText(this, "Introduce el codigo", Toast.LENGTH_SHORT).show();
        }
    }

    //METODO PARA ACTUALIZAR

    public void Modificar(View view) {
        ConexionsqLiteHelper admin = new ConexionsqLiteHelper(this, "bdejemplo", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String codigo = etcodigo.getText().toString();
        String nombre = etnombre.getText().toString();
        String precio = etprecio.getText().toString();

        if(!codigo.isEmpty() && !nombre.isEmpty() && !precio.isEmpty()){
            ContentValues registro = new ContentValues();

            registro.put("codigo", codigo);
            registro.put("nombre", nombre);
            registro.put("precio", precio);

            int cantidad = bd.update("articulos", registro, "codigo= 1"+ codigo, null);
            bd.close();

            if(cantidad==1){
                Toast.makeText(this,"Modificacion exitosa", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Articulo no existe", Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(this, "Debes llenar todos los campos",Toast.LENGTH_SHORT).show();
        }

    }
}
