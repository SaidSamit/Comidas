package com.comidas.app;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.ContentValues;
import android.content.Intent;

public class AddFoodActivity extends Activity {
    EditText etNombre, etDescripcion;
    DBHelper dbHelper;
    int foodId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        etNombre = (EditText) findViewById(R.id.editTextName);
        etDescripcion = (EditText) findViewById(R.id.editTextDescription);
        dbHelper = new DBHelper(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            foodId = intent.getIntExtra("id", -1);
            etNombre.setText(intent.getStringExtra("name"));
            etDescripcion.setText(intent.getStringExtra("description"));
        }

        findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarComida();
            }
        });
    }

    private void guardarComida() {
        String nombre = etNombre.getText().toString();
        String descripcion = etDescripcion.getText().toString();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FoodContract.FoodEntry.COLUMN_NAME, nombre);
        values.put(FoodContract.FoodEntry.COLUMN_DESCRIPTION, descripcion);

        long resultado;
        if (foodId == -1) {
            resultado = db.insert(FoodContract.FoodEntry.TABLE_NAME, null, values);
        } else {
            resultado = db.update(FoodContract.FoodEntry.TABLE_NAME, values,
                    FoodContract.FoodEntry._ID + "=?",
                    new String[]{String.valueOf(foodId)});
        }

        if (resultado != -1) {
            Toast.makeText(this, "Comida guardada!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al guardar.", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}
