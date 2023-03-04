import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.assertNotEquals as kotlinTestAssertNotEquals

class WallServiceTest {
    @Test
    fun add() {
        val service = WallService
        service.add(Post(0, 4000, 1255, "12/01/2023", "text", likes = Lakes(100), repost = null))
        service.add(Post(0, 4022, 1215, "12/01/2023", "text", likes = Lakes(100), repost = null))
        service.add(Post(0, 4018, 1375, "12/01/2023", "text", likes = Lakes(100), repost = null))
        val result = service.posts[1].id
        kotlinTestAssertNotEquals(0, result)
    }

    @Test
    fun updateTrue() {
        val service = WallService
        service.add(Post(0, 4000, 1255, "12/01/2023", "text", likes = Lakes(100), repost = null))
        service.add(Post(0, 4022, 1215, "12/01/2023", "text", likes = Lakes(100), repost = null))
        service.add(Post(0, 4018, 1375, "12/01/2023", "text", likes = Lakes(100), repost = null))

        val update = Post(2, 4000, 1255, "22/02/2022", "text", likes = Lakes(100), repost = null)
        val result = service.update(update)

        assertTrue(result)
    }

    @Test
    fun updateFalse() {
        val service = WallService
        service.add(Post(0, 4000, 1255, "12/01/2023", "text", likes = Lakes(100), repost = null))
        service.add(Post(0, 4022, 1215, "12/01/2023", "text", likes = Lakes(100), repost = null))
        service.add(Post(0, 4018, 1375, "12/01/2023", "text", likes = Lakes(100), repost = null))

        val update = Post(200, 4000, 1255, "22/02/2022", "text", likes = Lakes(100), repost = null)
        val result = service.update(update)

        assertFalse(result)
    }

    @Test
    fun createComment() {
        val service = WallService
        val post1 = Post(0, 4000, 1255, "12/01/2023", "text", likes = Lakes(100), repost = null)
        val post2 = Post(0, 4022, 1215, "12/01/2023", "text", likes = Lakes(100), repost = null)
        val post3 = Post(0, 4018, 1375, "12/01/2023", "text", likes = Lakes(100), repost = null)
        service.add(post1)
        service.add(post2)
        service.add(post3)
        val comment = Comment(1, 2233, "25/01/2023", "Text", 1528, thread = Thread())
        val result = service.createComment(1, comment)
        assertNotNull(result)
    }

    @Test(expected = PostNotFoundException::class)
    fun shouldThrow() {
        val service = WallService
        val post1 = Post(0, 4000, 1255, "12/01/2023", "text", likes = Lakes(100), repost = null)
        val post2 = Post(0, 4022, 1215, "12/01/2023", "text", likes = Lakes(100), repost = null)
        val post3 = Post(0, 4018, 1375, "12/01/2023", "text", likes = Lakes(100), repost = null)
        service.add(post1)
        service.add(post2)
        service.add(post3)
        val comment = Comment(77, 2233, "25/01/2023", "Text", 1528, thread = Thread())
        val result = service.createComment(77, comment)
    }

    @Test
     fun addNotes(){
        val service = NotesPost
        service.addNotes("Cat","text")
        service.addNotes("Dog", "text")
         val result = service.mapNotes
        assertNotNull(result)
     }
    @Test
    fun deleteComment() {
        val service = NotesPost
        service.addNotes("Cat","text")
        service.addNotes("Dog", "text")
        service.addComment(1,1,"text")
        service.addComment(1,2,"text")
        service.deleteComment(1,1)
        val result = service.mapNotes[1]?.notesComment!![0].deleteComment
        assertTrue(result)

    }
    @Test
    fun editNotes(){
        val service = NotesPost
        service.addNotes("Cat","text")
        service.addNotes("Dog", "text")
        service.editNotes(1,"text2")
        val result = service.mapNotes[1]?.text
         assertEquals("text2",result)
     }
    @Test
    fun restoreComment() {
        val service = NotesPost
        service.addNotes("Cat","text")
        service.addNotes("Dog", "text")
        service.addComment(1,1,"text")
        service.addComment(1,2,"text")
        service.deleteComment(1,1)
        service.restoreComment(1,1)
        val result = service.mapNotes[1]?.notesComment!![0].deleteComment
        assertFalse(result)

    }


}
