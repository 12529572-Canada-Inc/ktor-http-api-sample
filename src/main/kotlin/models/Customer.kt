package models

import kotlinx.serialization.Serializable

// In-memory storage
val customerStorage = mutableListOf<Customer>()

@Serializable
data class Customer(val id: String, val firstName: String, val lastName: String, val email: String)
