package uk.co.vvreddy.week01.models

data class TodoItem(
    val id: Long,
    val taskName: String,
    var taskCompleted: Boolean = false
)
