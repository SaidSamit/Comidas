package com.comidas.app;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class AddFoodActivity extends AppCompatActivity {
    EditText nameEditText, descEditText;
    DBHelper dbHelper;
    int foodId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        nameEditText = findViewById(R.id.editTextName);
        descEditText = findViewById(R.id.editTextDescription);
        dbHelper = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            foodId = extras.getInt("id", -1);
            nameEditText.setText(extras.getString("name", ""));
            descEditText.setText(extras.getString("description", ""));
        }

        findViewById(R.id.buttonSave).setOnClickListener(v -> saveFood());
    }

    private void saveFood() {
        String name = nameEditText.getText().toString();
        String desc = descEditText.getText().toString();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FoodContract.FoodEntry.COLUMN_NAME, name);
        values.put(FoodContract.FoodEntry.COLUMN_DESCRIPTION, desc);

        if (foodId == -1) {
            db.insert(FoodContract.FoodEntry.TABLE_NAME, null, values);
        } else {
            db.update(FoodContract.FoodEntry.TABLE_NAME, values,
                    FoodContract.FoodEntry._ID + "=?",
                    new String[]{String.valueOf(foodId)});
        }

        finish();
    }
}
