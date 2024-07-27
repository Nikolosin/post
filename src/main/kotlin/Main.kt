package ru.netology

fun main() {
    val com: Comment = Comment(1, 1, "sad", 1)
    val arrayComments = emptyArray<Comment>()
    val arrayAttachment = arrayOf(
        FileAttachment(File(2, 2, "name", 128)),
        VideoAttachment(Video(1, 2, "game", 2800)),
        AudioAttachment(Audio(1, 1, "Qween", "Show"))
    )
    val post: Post =
        Post(2, 1, 1, 1, "hello", false, likes = Likes(1, false, false, false), "post", 1, false, arrayComments, arrayAttachment)
    val post2: Post =
        Post(2, 1, 1, 1, "hello", false, likes = Likes(1, false, false, false), "post", 1, false, arrayComments, arrayAttachment)
    val service = WallService
    println(service.add(post))
    println(service.add(post2))
    println(service.createComment(2, com))
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
    val comments: Array<Comment> = emptyArray(),
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

data class Comment(
    val id: Int,
    val ownerId: Int,
    val text: String,
    val data: Int
)

class PostNotFoundException(mes: String) : RuntimeException(mes)

object WallService {
    var posts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()
    private var lastId = 0
    private var lastIdComment = 0

    fun add(post: Post): Post {
        posts += post.copy(id = ++lastId, likes = post.likes?.copy())
        return posts.last()
    }

    fun createComment(postId: Int, comment: Comment): Comment {
        var match = false
        for ((index, post) in posts.withIndex()) {
            if (post.id == postId) {
                match = true
                comments += comment.copy(id =++lastIdComment)
                posts[index] = post.copy(comments = comments)
                break
            }
        }
        if (match) {
            return comments.last()
        } else throw PostNotFoundException("no post with id=$postId")
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



