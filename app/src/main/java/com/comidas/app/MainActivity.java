package com.comidas.app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private ListView lvComidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvComidas = findViewById(R.id.lvComidas);
        dbHelper = new DBHelper(this);

        Button btnAgregar = findViewById(R.id.btnAgregar);
        Button btnActualizar = findViewById(R.id.btnActualizar);
        Button btnEliminar = findViewById(R.id.btnEliminar);

        btnAgregar.setOnClickListener(v ->
                startActivity(new Intent(this, AddFoodActivity.class)));

        btnActualizar.setOnClickListener(v ->
                startActivity(new Intent(this, UpdateFoodActivity.class)));

        btnEliminar.setOnClickListener(v ->
                startActivity(new Intent(this, DeleteFoodActivity.class)));

        loadComidas();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadComidas(); // Recarga la lista al volver de otra actividad
    }

    private void loadComidas() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(FoodContract.FoodEntry.TABLE_NAME,
                null, null, null, null, null, null);

        ArrayList<String> comidas = new ArrayList<>();
        while (cursor.moveToNext()) {
            String nombre = cursor.getString(cursor.getColumnIndexOrThrow(FoodContract.FoodEntry.COLUMN_NAME));
            String descripcion = cursor.getString(cursor.getColumnIndexOrThrow(FoodContract.FoodEntry.COLUMN_DESCRIPTION));
            comidas.add(nombre + ": " + descripcion);
        }
        cursor.close();

        lvComidas.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, comidas));
    }
}
