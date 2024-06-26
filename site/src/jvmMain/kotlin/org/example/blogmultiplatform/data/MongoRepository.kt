package org.example.blogmultiplatform.data

import org.example.blogmultiplatform.models.Post
import org.example.blogmultiplatform.models.User

interface MongoRepository {
    suspend fun addPost(post: Post): Boolean
    suspend fun checkUserExistence(user: User): User?
    suspend fun checkUserId(id: String): Boolean
}