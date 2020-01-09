package com.example.hvalevel4_task2.database


import android.content.Context
import com.example.hvalevel4_task2.model.Games


class GamesRepository(context: Context) {

    private val gameDao: GamesDao

    init {
        val database = GameRoomDatabase.getDatabase(context)
        gameDao = database!!.gamesDao()
    }

    suspend fun getAllGames(): List<Games> {
        return gameDao.getAllGames()
    }

    suspend fun insertGames(product: Games) {
        gameDao.insertGames(product)
    }

    suspend fun deleteGames(product: Games) {
        gameDao.deleteGames(product)
    }

    suspend fun deleteAllGames() {
        gameDao.deleteAllGames()
    }

    fun getCountStatus(status:Int): Int {
        return gameDao.getCountStatus(status)
    }
}
