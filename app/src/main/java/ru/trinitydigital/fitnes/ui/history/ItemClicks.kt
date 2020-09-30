package ru.trinitydigital.fitnes.ui.history

import ru.trinitydigital.fitnes.data.model.MainTraining

interface ItemClicks {
    fun clickOnDelete(item: MainTraining)
}