package com.comidas.app;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DeleteFoodActivity extends AppCompatActivity {
    private EditText etId;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_food);

        etId = findViewById(R.id.etId);
        dbHelper = new DBHelper(this);

        findViewById(R.id.btnEliminar).setOnClickListener(v -> {
            int id = Integer.parseInt(etId.getText().toString());
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            int rowsDeleted = db.delete(FoodContract.FoodEntry.TABLE_NAME,
                    FoodContract.FoodEntry._ID + " = ?", new String[]{String.valueOf(id)});

            if (rowsDeleted > 0) {
                Toast.makeText(this, "Comida eliminada!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "No se encontró la comida.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

