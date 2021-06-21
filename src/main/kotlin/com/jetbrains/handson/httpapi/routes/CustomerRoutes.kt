package com.jetbrains.handson.httpapi.routes

import com.jetbrains.handson.httpapi.models.Customer
import com.jetbrains.handson.httpapi.models.customerStorage
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.customerRouting() {
    route("/customer") {
        get {
            // Check if Customers exist
            if (customerStorage.isNotEmpty()) {
                // Return all Customers (serialize to JSON)
                call.respond(customerStorage)
            } else {
                // Return error message
                call.respondText("No customers found", status = HttpStatusCode.NotFound)
            }
        }

        get("{id}") {
            // Get ID (check if it exists in the request)
            // Note: this will never happen, since this route will only be called if ID is present
            // Otherwise, it will hit the first GET route
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            // Get Customer (check if it exists)
            val customer =
                customerStorage.find { it.id == id } ?: return@get call.respondText(
                    "No customer with id $id",
                    status = HttpStatusCode.NotFound
                )
            // Return Customer (serialize to JSON)
            call.respond(customer)
        }

        post {
            // Add Customer to storage (deserialize JSON to Customer object)
            val customer = call.receive<Customer>()
            customerStorage.add(customer)
            // Return success message
            call.respondText("Customer stored correctly", status = HttpStatusCode.Created)
        }

        delete("{id}") {
            // Get ID (check if it exists in the request)
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            // Delete the Customer (if it exists)
            if (customerStorage.removeIf { it.id == id }) {
                // Return success message
                call.respondText("Customer removed correctly", status = HttpStatusCode.Accepted)
            } else {
                // Return error message
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }

    }
}