import NotesPost.mapNotes

data class Notes(
    val title: String,    //Заголовок заметки
    var text: String,  //Текст заметки
    var deleteNotes: Boolean = false, // Отметка об удалении заметки
    var notesComment: Array<NotesComment> = emptyArray()
) {}

object NotesPost {
    var noteId = 1
    val mapNotes = mutableMapOf<Int, Notes>()
    fun addNotes(title: String, text: String) {
        var notes = Notes(title, text)
        mapNotes.put(noteId, notes)
        noteId++
        //  println("Добавлена новая заметка $noteId (${notes.title})")
    }

    fun addComment(noteId: Int, commentId: Int, message: String) {
        mapNotes.get(noteId)!!.notesComment += NotesComment(commentId, message)
        //   println("Создан новый комментарий к заметке $noteId,  $message")
    }

    fun deleteNotes(noteId: Int) {
        if (mapNotes.containsKey(noteId)) {
            mapNotes.get(noteId)!!.deleteNotes = true
            println("Заметка $noteId удалена")
        } else {
            println("Нет такой заметки")
        }
    }

    fun deleteComment(noteId: Int, commentId2: Int) {
        if (mapNotes.containsKey(noteId)) {
            var arrayNotes = mapNotes.filterKeys { noteId -> true }.getValue(noteId).notesComment
            for ((index, commentId) in arrayNotes.withIndex()) {
                if (arrayNotes[index].commentId == commentId2) {
                    val newComment = arrayNotes[index].copy(
                        commentId = arrayNotes[index].commentId,
                        message = arrayNotes[index].message,
                        deleteComment = true
                    )
                    arrayNotes[index] = newComment
                } else {
                    arrayNotes[index]
                }
            }
            mapNotes.get(noteId)!!.notesComment = arrayNotes
        } else {
            println("нет такой заметки")
        }
    }

    fun editNotes(noteId: Int, text2: String) {
        if (mapNotes.containsKey(noteId)) {
            if (mapNotes.get(noteId)!!.deleteNotes == true) {
                println("Заметка $noteId  была удалена")
            } else {
                mapNotes.get(noteId)!!.text = text2
            }
        } else {
            println("нет такой заметки")
        }
    }

    fun editComment(noteId: Int, commentId2: Int, message2: String) {
        if (mapNotes.containsKey(noteId)) {
            if (mapNotes.get(noteId)!!.deleteNotes == true) {
                println("Заметка $noteId  была удалена")
            } else {
                var arrayNotes = mapNotes.filterKeys { noteId -> true }.getValue(noteId).notesComment
                for ((index, commentId) in arrayNotes.withIndex()) {
                    if (arrayNotes[index].commentId == commentId2) {
                        if (arrayNotes[index].deleteComment == true) {
                            println("комментарий $commentId2 к заметки $noteId был удален")
                        } else {
                            val newComment = arrayNotes[index].copy(
                                commentId = arrayNotes[index].commentId,
                                message = message2,
                                deleteComment = arrayNotes[index].deleteComment
                            )
                            arrayNotes[index] = newComment
                        }
                    } else {
                        arrayNotes[index]
                    }
                }
                mapNotes.get(noteId)!!.notesComment = arrayNotes
            }
        } else {
            println("такой заметки $noteId нет")
        }
    }

    fun getNotes() {
        val filterMap = mapNotes.filter { (noteId, Notes) -> Notes.deleteNotes == false }
        println(filterMap)
    }

    fun getByIdNotes(noteId3: Int) {
        if (mapNotes.containsKey(noteId3)) {
            val filterMap = mapNotes.filter { (noteId, Notes) -> noteId == noteId3 && Notes.deleteNotes == false }
            println(filterMap)
        } else {
            println("такой заметки нет")
        }
    }

    fun getComment(noteId: Int, commentId2: Int) {
        if (mapNotes.containsKey(noteId)) {
            if (mapNotes.get(noteId)!!.deleteNotes == true) {
                println("Заметка $noteId  была удалена")
            } else {
                val arrayNotes = mapNotes.filterKeys { noteId -> true }.getValue(noteId).notesComment
                for ((index, commentId) in arrayNotes.withIndex()) {
                    if (arrayNotes[index].commentId == commentId2) {
                        println("Комментарий $commentId2 к заметке $noteId - ${arrayNotes[index].message}")
                    }
                }
            }
        } else {
            println("Такой заметки нет")
        }
    }

    fun restoreComment(noteId: Int, commentId2: Int) {
        if (mapNotes.containsKey(noteId)) {
            if (mapNotes.get(noteId)!!.deleteNotes == true) {
                println("Заметка $noteId  была удалена и комментарий не восстановить")
            } else {
                var arrayNotes = mapNotes.filterKeys { noteId -> true }.getValue(noteId).notesComment
                for ((index, commentId) in arrayNotes.withIndex()) {
                    if (arrayNotes[index].commentId == commentId2) {
                        val newComment = arrayNotes[index].copy(
                            commentId = arrayNotes[index].commentId,
                            message = arrayNotes[index].message,
                            deleteComment = false
                        )
                        arrayNotes[index] = newComment
                    } else {
                        arrayNotes[index]
                    }
                }
                mapNotes.get(noteId)!!.notesComment = arrayNotes
            }
        } else {
            println("нет такой заметки")
        }
    }

}


data class NotesComment(
    val commentId: Int,
    val message: String,  //Текст комментария
    val deleteComment: Boolean = false
)
