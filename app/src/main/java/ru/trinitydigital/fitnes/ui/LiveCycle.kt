package ru.trinitydigital.fitnes.ui

interface LiveCycle<V> {
    fun bind(view: V)
    fun unbind()
}