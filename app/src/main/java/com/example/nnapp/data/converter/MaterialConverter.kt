package com.example.nnapp.data.converter

import androidx.room.TypeConverter
import com.example.nnapp.data.model.Material
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Convierte una lista de Material a JSON y viceversa para poder almacenar en Room.
 */
class MaterialConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromMaterialList(materials: List<Material>): String {
        return gson.toJson(materials)
    }

    @TypeConverter
    fun toMaterialList(data: String): List<Material> {
        val listType = object : TypeToken<List<Material>>() {}.type
        return gson.fromJson(data, listType)
    }
}