package com.example.vinilosmobile

import android.app.Application
import com.example.vinilosmobile.database.dao.VinylRoomDatabase

class VinylsApplication: Application()  {
    val database by lazy { VinylRoomDatabase.getDatabase(this)}
}