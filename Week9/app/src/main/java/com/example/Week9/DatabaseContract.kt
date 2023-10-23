package com.example.Week9

import android.provider.BaseColumns

object DatabaseContract {
    class UserEntry: BaseColumns {
        companion object {
            const val tableName = "user"
            const val Column_Name = "name"
            const val I_ID = BaseColumns._ID
        }
    }
}