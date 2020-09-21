package ru.trinitydigital.fitnes.data.local

import androidx.room.Dao
import androidx.room.Insert
import ru.trinitydigital.fitnes.data.model.MainTraining

@Dao
interface TrainingDao {

    @Insert
    fun addTrainig(data: MainTraining)
}