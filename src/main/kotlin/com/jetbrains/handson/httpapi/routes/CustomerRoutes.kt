package com.jetbrains.handson.httpapi.routes

import com.jetbrains.handson.httpapi.models.customerStorage
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.customerRouting() {
    route("/customer") {
        get {
            // Check if Customers exist
            if (customerStorage.isNotEmpty()) {
                // Return all Customers
                call.respond(customerStorage)
            } else {
                // Return error message
                call.respondText("No customers found", status = HttpStatusCode.NotFound)
            }
        }
        get("{id}") {
            // Get ID (check if it exists in the request)
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            // Get customer (check if it exists)
            val customer =
                customerStorage.find { it.id == id } ?: return@get call.respondText(
                    "No customer with id $id",
                    status = HttpStatusCode.NotFound
                )
            call.respond(customer)
        }
        post {

        }
        delete("{id}") {

        }
    }
}