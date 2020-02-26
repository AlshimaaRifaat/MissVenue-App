package com.ibsvalleyn.missvenue.data_room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ibsvalleyn.missvenue.models.FavoriteModel;

import java.util.List;

@Dao
public interface FavoriteDao {
    @Query("SELECT * FROM FavoriteModel")
    List<FavoriteModel> getAll();

    @Query("select * from FavoriteModel where id like :id")
    FavoriteModel fetchById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavoriteModel insert);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(FavoriteModel update);

    @Delete
    void delete(FavoriteModel delete);

    @Query("delete from FavoriteModel")
    void deleteAll();

}
