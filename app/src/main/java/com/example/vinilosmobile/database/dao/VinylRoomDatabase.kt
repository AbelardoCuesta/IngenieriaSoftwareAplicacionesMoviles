package com.example.vinilosmobile.database.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.vinilosmobile.models.Album
import org.w3c.dom.Comment

@Database(entities = [Album::class, Comment::class], version = 1, exportSchema = false)
abstract class VinylRoomDatabase : RoomDatabase() {
    abstract fun albumsDao(): AlbumDao
    abstract fun collectorsDao(): CollectorsDao

    @Volatile
    private var INSTANCE: VinylRoomDatabase? = null

    fun getDatabase(context: Context): VinylRoomDatabase {
        // if the INSTANCE is not null, then return it,
        // if it is, then create the database
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                VinylRoomDatabase::class.java,
                "vinyls_database"
            ).build()
            INSTANCE = instance
            // return instance
            return instance
        }
    }

}