package org.example.blogmultiplatform.util

import com.varabyte.kobweb.browser.api
import kotlinx.browser.window
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.blogmultiplatform.models.User
import org.example.blogmultiplatform.models.UserDTO

suspend fun checkUserExistence(user: User): UserDTO? {
    return try {
        val result = window.api.tryPost(
            apiPath = "usercheck",
            body = Json.encodeToString(user).encodeToByteArray()
        )
        Json.decodeFromString<UserDTO>(result.toString())
    } catch (e: Exception) {
        println(e.message)
        null
    }
}