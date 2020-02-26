package com.ibsvalleyn.missvenue.data_room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ibsvalleyn.missvenue.models.FavoriteModel;

@Database(entities = {FavoriteModel.class}, version = 1, exportSchema = false)
//@TypeConverters(AppDatabase.class)

public abstract class AppDatabase extends RoomDatabase {
    public abstract FavoriteDao favoriteDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context ctx) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {

                INSTANCE = Room.databaseBuilder(
                        ctx.getApplicationContext(),
                        AppDatabase.class,
                        "FavoriteDB4")
                        .allowMainThreadQueries() // Does not scale very well!
                        .build();
            }
        }

        return INSTANCE;
    }


//Room.databaseBuilder(getApplicationContext(), MyDb.class, "database-name")
//            .addMigrations(MIGRATION_1_2).build();
//
//    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, "
//                    + "`name` TEXT, PRIMARY KEY(`id`))");
//        }
//    };


    void destroyInstance() {
        INSTANCE = null;
    }

}


