package ru.trinitydigital.fitnes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.trinitydigital.fitnes.data.model.MainTraining

@Dao
interface TrainingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTrainig(data: MainTraining)

    @Query("SELECT * FROM MainTraining")
    fun getTraing(): MainTraining
}