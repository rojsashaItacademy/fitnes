package ru.trinitydigital.fitnes.data.local

import androidx.lifecycle.LiveData
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
    fun getTraing(): List<MainTraining>

    @Query("SELECT * FROM MainTraining")
    fun getTraingLiveData(): LiveData<List<MainTraining>>

    @Query("DELETE FROM MainTraining WHERE id =:id")
    fun deleteTraing(id: Int)
}