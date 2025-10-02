package com.comidas.app;

public final class FoodContract {
    private FoodContract() {}

    public static class FoodEntry {
        public static final String TABLE_NAME = "comidas";
        public static final String _ID = "_id";
        public static final String COLUMN_NAME = "nombre";
        public static final String COLUMN_DESCRIPTION = "descripcion";
    }
}
