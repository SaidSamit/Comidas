package com.comidas.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    ListView listView;
    ArrayList<Food> foodList;
    ArrayAdapter<Food> adapter;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        dbHelper = new DBHelper(this);
        cargarComidas();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Food comida = foodList.get(position);
            Intent intent = new Intent(MainActivity.this, AddFoodActivity.class);
            intent.putExtra("id", comida.getId());
            intent.putExtra("name", comida.getName());
            intent.putExtra("description", comida.getDescription());
            startActivity(intent);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Food comida = foodList.get(position);
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Eliminar comida")
                    .setMessage("¿Deseas eliminar esta comida?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.delete(FoodContract.FoodEntry.TABLE_NAME,
                                FoodContract.FoodEntry._ID + "=?",
                                new String[]{String.valueOf(comida.getId())});
                        cargarComidas();
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });
    }

    private void cargarComidas() {
        foodList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(FoodContract.FoodEntry.TABLE_NAME,
                null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(FoodContract.FoodEntry._ID));
            String nombre = cursor.getString(cursor.getColumnIndexOrThrow(FoodContract.FoodEntry.COLUMN_NAME));
            String descripcion = cursor.getString(cursor.getColumnIndexOrThrow(FoodContract.FoodEntry.COLUMN_DESCRIPTION));
            foodList.add(new Food(id, nombre, descripcion));
        }
        cursor.close();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foodList);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarComidas();
    }

    public void onAddFoodClick(View view) {
        startActivity(new Intent(this, AddFoodActivity.class));
    }
}
