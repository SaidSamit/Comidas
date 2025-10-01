package com.comidas.app;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Food> foodList;
    ArrayAdapter<Food> adapter;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        dbHelper = new DBHelper(this);
        loadFoods();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Food selected = foodList.get(position);
            Intent intent = new Intent(MainActivity.this, AddFoodActivity.class);
            intent.putExtra("id", selected.getId());
            intent.putExtra("name", selected.getName());
            intent.putExtra("description", selected.getDescription());
            startActivity(intent);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Food selected = foodList.get(position);
            new AlertDialog.Builder(this)
                    .setTitle("Eliminar comida")
                    .setMessage("¿Deseas eliminar esta comida?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.delete(FoodContract.FoodEntry.TABLE_NAME,
                                FoodContract.FoodEntry._ID + "=?",
                                new String[]{String.valueOf(selected.getId())});
                        loadFoods();
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });
    }

    private void loadFoods() {
        foodList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(FoodContract.FoodEntry.TABLE_NAME,
                null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(FoodContract.FoodEntry._ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(FoodContract.FoodEntry.COLUMN_NAME));
            String desc = cursor.getString(cursor.getColumnIndexOrThrow(FoodContract.FoodEntry.COLUMN_DESCRIPTION));
            foodList.add(new Food(id, name, desc));
        }
        cursor.close();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foodList);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFoods();
    }

    public void onAddFoodClick(View view) {
        startActivity(new Intent(this, AddFoodActivity.class));
    }
}
