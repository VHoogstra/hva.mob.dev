package com.example.hvalevel4_task2.database


import androidx.room.*
import com.example.hvalevel4_task2.model.Games
import androidx.lifecycle.LiveData


@Dao
interface GamesDao {

    @Query("SELECT * FROM games_table order by date DESC")
    suspend fun getAllGames(): List<Games>

    @Insert
    suspend fun insertGames(product: Games)

    @Delete
    suspend fun deleteGames(product: Games)

    @Query("DELETE FROM games_table")
    suspend fun deleteAllGames()

    @Query("SELECT COUNT(*) FROM games_table WHERE status=:status")
    fun getCountStatus(status:Int): Int

}
