package com.example.vinilosmobile.database.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.vinilosmobile.models.Album
import com.example.vinilosmobile.models.Collector


@Database(entities = [Album::class, Collector::class], version = 1, exportSchema = false)
abstract class VinylRoomDatabase : RoomDatabase() {
    abstract fun albumsDao(): AlbumDao
    abstract fun collectorsDao(): CollectorsDao

    companion object {
        @Volatile
        private var INSTANCE: VinylRoomDatabase? = null

        fun getDatabase(context: Context): VinylRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VinylRoomDatabase::class.java,
                    "vinyls_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}