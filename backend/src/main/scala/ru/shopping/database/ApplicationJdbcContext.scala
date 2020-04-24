package ru.shopping.database

import io.getquill.{PostgresJdbcContext, SnakeCase}

class ApplicationJdbcContext extends PostgresJdbcContext(SnakeCase, "db")