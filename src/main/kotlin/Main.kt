import Post as Post

data class Post(
    val id: Int,
    var ownerId: Int,        //Идентификатор владельца стены, на которой размещена запись
    val fromId: Int,        //Идентификатор автора записи (от чьего имени опубликована запись)
    val date: String = "23/01/2023",        //Время публикации записи в формате
    val text: String = "text",      //Текст записи.
    val friendsOnly: Boolean = false,    //если запись была создана с опцией «Только для друзей»
    val canPin: Boolean = false,    //Информация о том, может ли текущий пользователь закрепить запись
    val canDelete: Boolean = false, //Информация о том, может ли текущий пользователь удалить запись
    val canEdit: Boolean = false, //Информация о том, может ли текущий пользователь редактировать запись
    val isFavorite: Boolean = true, //true, если объект добавлен в закладки у текущего пользователя.
    val likes: Lakes,
    val repost: Post?  // проверка является данный пост репостом
)

class PostNotFoundException(message: String) : RuntimeException(message)
object WallService {
    var posts = emptyArray<Post>()
    var comments = emptyArray<Comment>()
    fun createComment(postId: Int, comment: Comment): Comment {
        for ((index, post) in posts.withIndex()) {
            if (postId == post.id) {
                comments += comment
                return comments.last()
            }
        }
        return throw PostNotFoundException("Нет поста в id $postId")
    }

    var lastId = 0
    fun add(post: Post): Post {
        val newIdPost = post.copy(id = lastId + 1)
        repost(newIdPost)
        posts += newIdPost
        lastId++
        return posts.last()
    }

    fun update(post: Post): Boolean {
        for ((index, id) in posts.withIndex()) {
            if (posts[index].id == post.id) {
                val newPost = posts[index].copy(
                    ownerId = post.ownerId,
                    text = post.text,
                    friendsOnly = post.friendsOnly,
                    canPin = post.canPin,
                    canDelete = post.canDelete,
                    canEdit = post.canEdit,
                    isFavorite = post.isFavorite,
                    likes = post.likes
                )
                posts[index] = newPost
                return true
            }
        }
        return false
    }

    fun repost(post: Post): Any {
        if (post.repost == null) {
            return post
        } else {
            var count = post.likes.count
            val newPost = post.copy((count + 100).also { post.likes.count = it })
            return newPost
        }
    }

    fun printPost(id: Int) {
        for ((index, newIdPost) in posts.withIndex()) {
            if (newIdPost.id == id) {
                println(
                    """
      № ${newIdPost.id}
      номер стены ${newIdPost.ownerId}, автор записи ${newIdPost.fromId}
      дата записи: ${newIdPost.date}
     "${newIdPost.text}"
      только для друзей:${newIdPost.friendsOnly}
      закрепить:${newIdPost.canPin}, удалить: ${newIdPost.canDelete}, редактировать: ${newIdPost.canEdit}
      добавить в закладку: ${newIdPost.isFavorite} 
     количество лайков: ${newIdPost.likes.count}""".trimIndent()
                )
            }
        }
    }
}

fun main() {
    NotesPost.addNotes("Красивые кошки", "Очень красивые кошки")
    NotesPost.addNotes("Лысые кошки", "Несчастные лысые кошки")
    NotesPost.addNotes("Черные кошки", "Очень черные кошки")
    NotesPost.getNotes()
//    NotesPost.addComment(3,1,"черные кошки всем нравятся")
//    NotesPost.addComment(1, 1, "Всем нравится")
//    NotesPost.addComment(1, 2, "Красивые....")
//    NotesPost.addComment(2, 1, "Бедолаги")
//    NotesPost.addComment(2, 2, "Все равно нравятся")
//    NotesPost.deleteComment(2, 2)
//    NotesPost.deleteComment(5, 1)
    NotesPost.deleteNotes(1)
    NotesPost.editNotes(1, "Кошки")
    NotesPost.editNotes(2, "лысые кошки - это круто!!")
//    NotesPost.editComment(5, 1, "text")
//    NotesPost.editComment(2, 2, "текст")
    NotesPost.getNotes()
    NotesPost.getByIdNotes(2)
//    NotesPost.getComment(2, 1)
//    NotesPost.restoreComment(2, 2)
//    NotesPost.getComment(2, 2)

}


