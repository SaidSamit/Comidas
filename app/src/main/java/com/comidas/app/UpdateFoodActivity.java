package com.comidas.app;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateFoodActivity extends AppCompatActivity {
    private EditText etId, etNombre, etDescripcion;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_food);

        etId = findViewById(R.id.etId);
        etNombre = findViewById(R.id.etNombre);
        etDescripcion = findViewById(R.id.etDescripcion);
        dbHelper = new DBHelper(this);

        findViewById(R.id.btnActualizar).setOnClickListener(v -> {
            int id = Integer.parseInt(etId.getText().toString());
            String nombre = etNombre.getText().toString();
            String descripcion = etDescripcion.getText().toString();

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(FoodContract.FoodEntry.COLUMN_NAME, nombre);
            values.put(FoodContract.FoodEntry.COLUMN_DESCRIPTION, descripcion);

            int rowsUpdated = db.update(FoodContract.FoodEntry.TABLE_NAME, values,
                    FoodContract.FoodEntry._ID + " = ?", new String[]{String.valueOf(id)});

            if (rowsUpdated > 0) {
                Toast.makeText(this, "Comida actualizada!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "No se encontró la comida.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

