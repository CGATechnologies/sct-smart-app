package app.sctp.persistence;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;

import androidx.core.content.res.ResourcesCompat;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import app.sctp.app.SctApplication;
import app.sctp.utils.IoUtils;

public final class DbUtils {
    public static boolean columnExists(SupportSQLiteDatabase database, String tableName, String columnName) {
        Cursor cursor = null;
        boolean columnExists = false;
        try {
            String query = String.format(Locale.US, "PRAGMA table_info('%s')", tableName);
            cursor = database.query(query);
            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndex("name");
                String name = cursor.getString(index);
                if (columnExists = name.equalsIgnoreCase(columnName)) {
                    break;
                }
            }
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return columnExists;
    }

    public static void addColumnIfNotExists(SupportSQLiteDatabase database, String tableName, String column, String type) {
        if (!columnExists(database, tableName, column)) {
            String query = String.format(Locale.US,
                    "ALTER TABLE %s ADD COLUMN %s %s", tableName, column, type);
            database.execSQL(query);
        }
    }

    public static String loadFromResources(Context context, String fileName) throws RuntimeException {
        String path = "migrations/" + fileName;
        AssetManager assetManager = context.getAssets();
        try (InputStream is = assetManager.open(path)) {
            return new String(IoUtils.readAll(is), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
