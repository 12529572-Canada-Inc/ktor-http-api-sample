package com.jetbrains.handson.httpapi

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    // Install ContentNegotiation plugin for JSON
    install(ContentNegotiation) {
        json()
    }
}
