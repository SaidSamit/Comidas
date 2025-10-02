package com.comidas.app;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddFoodActivity extends AppCompatActivity {
    private EditText etNombre, etDescripcion;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        etNombre = findViewById(R.id.etNombre);
        etDescripcion = findViewById(R.id.etDescripcion);
        dbHelper = new DBHelper(this);

        findViewById(R.id.btnGuardar).setOnClickListener(v -> {
            String nombre = etNombre.getText().toString();
            String descripcion = etDescripcion.getText().toString();

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(FoodContract.FoodEntry.COLUMN_NAME, nombre);
            values.put(FoodContract.FoodEntry.COLUMN_DESCRIPTION, descripcion);

            long newRowId = db.insert(FoodContract.FoodEntry.TABLE_NAME, null, values);
            if (newRowId != -1) {
                Toast.makeText(this, "Comida guardada!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al guardar.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
