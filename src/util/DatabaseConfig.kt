package com.lc.server.util

object DatabaseConfig {

    object Localhost {
        private const val databaseName: String = "language_center"
        const val username: String = "root"
        const val password: String = "abc456"
        const val jdbcUrl: String = "jdbc:mysql://192.168.43.22:3306/$databaseName"
    }

    object Heroku {
        private const val databaseName: String = "heroku_534f71118c68fc7"
        const val username: String = "b8bd55c495ad1d"
        const val password: String = "c1b8ffea"
        const val jdbcUrl: String =
            "jdbc:mysql://$username:$password@eu-cdbr-west-03.cleardb.net/$databaseName?reconnect=true"
    }

}
