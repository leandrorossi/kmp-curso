package org.example.blogmultiplatform.api

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.setBodyText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.blogmultiplatform.data.MongoDb
import org.example.blogmultiplatform.models.Post
import org.litote.kmongo.id.ObjectIdGenerator

@Api(routeOverride = "addpost")
suspend fun addPost(context: ApiContext) {
    try {
        val post = context.req.body?.decodeToString()?.let { Json.decodeFromString<Post>(it) }
        val newPost = post?.copy(id = ObjectIdGenerator.newObjectId<String>().id.toHexString())
        context.res.setBodyText(
            newPost?.let {
                context.data.getValue<MongoDb>().addPost(it).toString()
            } ?: false.toString()
        )
    } catch (e: Exception) {
        context.res.setBodyText(Json.encodeToString(e.message))
    }
}