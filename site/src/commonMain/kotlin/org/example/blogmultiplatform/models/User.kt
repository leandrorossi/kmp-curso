package org.example.blogmultiplatform.models

expect class User {
    val id: String
    val username: String
    val password: String
}

expect class UserDTO {
    val id: String
    val username: String
}
