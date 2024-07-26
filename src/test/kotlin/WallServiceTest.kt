import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import ru.netology.*

class WallServiceTest {

    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun add() {
        val service = WallService
        val post = service.add(Post(0, 1, 1, 1, "hello", false, likes = Likes(1, false, false, false), "post", 1, false))
        val result = post.id != 0
        assertTrue(result)

    }
    @Test
    fun update() {
        val service = WallService
        val arrayAttachment = arrayOf(FileAttachment(File(2,2,"name", 128)), VideoAttachment(Video(1,2,"game", 2800)), AudioAttachment(Audio(1,1,"Qween", "Show")))
        service.add(Post(0, 1, 1, 1, "hello", false, likes = Likes(1, false, false, false), "post", 1, false, arrayAttachment))
        service.add(Post(1, 1, 1, 1, "hello world", false, likes = Likes(1, false, false, false), "post", 1, false))
        val update = Post(1, 1, 1, 1, "hello", false, likes = Likes(1, false, false, false), "post", 1, false)
        val result = service.update(update)

        assertTrue(result)
    }

    @Test
    fun updateError() {
        val service = WallService
        service.add(Post(0, 1, 1, 1, "hello", false, likes = Likes(1, false, false, false), "post", 1, false))
        service.add(Post(1, 1, 1, 1, "hello world", false, likes = Likes(1, false, false, false), "post", 1, false))
        val update = Post(100, 1, 1, 1, "hello", false, likes = Likes(1, false, false, false), "post", 1, false)
        val result = service.update(update)

        assertFalse(result)
    }
}