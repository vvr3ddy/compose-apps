package uk.co.vvreddy.week01

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import uk.co.vvreddy.week01.models.TodoItem
import java.util.concurrent.atomic.AtomicLong

class TodoAppViewModel : ViewModel() {
    private val _todos = MutableStateFlow<List<TodoItem>>(emptyList())
    val todos = _todos.asStateFlow()
    private val nextId = AtomicLong(1)

    fun addTodo(taskName: String) {
        val newList = _todos.value.toMutableList()
        newList.add(TodoItem(nextId.getAndIncrement(), taskName))
        _todos.value = newList
    }

    fun toggleTodo(todo: TodoItem) {
        val newList = _todos.value.toMutableList()
        val index = newList.indexOf(todo)
        if (index != -1) {
            newList[index] = todo.copy(taskCompleted = !todo.taskCompleted)
        }
        _todos.value = newList
    }

    fun removeTodo(todo: TodoItem) {
        val newList = _todos.value.toMutableList()
        newList.remove(todo)
        _todos.value = newList
    }
}
