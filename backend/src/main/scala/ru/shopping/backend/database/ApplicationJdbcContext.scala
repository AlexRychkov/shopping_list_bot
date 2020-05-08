package ru.shopping.backend.database

import io.getquill.{PostgresJdbcContext, SnakeCase}

class ApplicationJdbcContext extends PostgresJdbcContext(SnakeCase, "db")