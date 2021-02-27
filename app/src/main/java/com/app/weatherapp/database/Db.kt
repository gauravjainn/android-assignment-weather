package com.app.weatherapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.app.weatherapp.responseobject.City
import com.app.weatherapp.responseobject.WeatherData
import com.app.weatherapp.util.DbConstants
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(
    entities = [WeatherData::class, City::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(WeatherConverters::class)
abstract class Db : RoomDatabase() {

    abstract fun weatherDataDao(): WeatherDataDao?
    abstract fun cityDao(): CityDao?


    companion object {
        @Volatile
        private lateinit var instance: Db
        private val NUMBER_OF_THREADS = 4
        val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)


        fun getDatabase(context: Context): Db {

            synchronized(Db::class.java) {
                if (!::instance.isInitialized) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        Db::class.java,
                        DbConstants.DB_NAME
                    ).build()
                }
            }
            return instance

        }


        fun clearDb() {
            databaseWriteExecutor.execute { instance.clearAllTables() }
        }


        var dbCallback: RoomDatabase.Callback = object : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                databaseWriteExecutor.execute { }
            }
        }
    }


}