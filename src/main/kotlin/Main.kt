package ru.netology

fun main() {
}


data class Post(
    val id: Int,
    val ownerId: Int,
    val fromId: Int,
    val createdBy: Int,
    val text: String?,
    val friendsOnly: Boolean = false,
    val likes: Likes?,
    val postType: String,
    val signerId: Int,
    val canDelete: Boolean = true,
    val attachments: Array<Attachment> = emptyArray()
)

interface Attachment {
    val typeAttachment: String
}

data class Audio(
    val id: Int,
    val ownerId: Int,
    val artist: String,
    val title: String
)

data class AudioAttachment(val audio: Audio) : Attachment {
    override val typeAttachment: String = "photo"
}

data class Video(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val duration: Int
)

data class VideoAttachment(val video: Video) : Attachment {
    override val typeAttachment: String = "video"
}

data class Sticker(
    val stickerId: Int,
    val productId: Int,
    var isAllowed: Boolean
)

data class StickerAttachment(val sticker: Sticker) : Attachment {
    override val typeAttachment: String = "sticker"
}

data class Story(
    val id: Int,
    val ownerId: Int,
    val date: Int,
    val seen: Boolean
)

data class StoryAttachment(val story: Story) : Attachment {
    override val typeAttachment: String = "story"
}

data class File(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val size: Int
)

data class FileAttachment(val file: File) : Attachment {
    override val typeAttachment: String = "file"
}


data class Likes(
    val count: Int,
    val userLikes: Boolean,
    val canLike: Boolean,
    val canPublish: Boolean
)

object WallService {
    private var posts = emptyArray<Post>()
    private var lastId = 0

    fun add(post: Post): Post {
        posts += post.copy(id = ++lastId, likes = post.likes?.copy())
        return posts.last()
    }

    fun update(newPost: Post): Boolean {
        for ((index, post) in posts.withIndex()) {
            if (post.id == newPost.id) {
                posts[index] = newPost.copy(likes = post.likes?.copy())
                return true
            }
        }
        return false
    }

    fun clear() {
        posts = emptyArray()
        lastId = 0
    }
}



