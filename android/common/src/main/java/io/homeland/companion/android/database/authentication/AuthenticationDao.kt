package io.homeland.companion.android.database.authentication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AuthenticationDao {

    @Insert
    fun insert(authentication: Authentication)

    @Update
    fun update(authentication: Authentication)

    @Query("SELECT * from Authentication_List WHERE Host = :key")
    fun get(key: String): Authentication?
}
