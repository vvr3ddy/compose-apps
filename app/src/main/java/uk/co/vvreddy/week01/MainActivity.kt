package uk.co.vvreddy.week01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import uk.co.vvreddy.week01.models.TodoItem
import uk.co.vvreddy.week01.ui.theme.Week01Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Week01Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TodoApp()
                }
            }
        }
    }
}


@Composable
fun TodoList(todos: List<TodoItem>, onAddTodo: (String) -> Unit, onToggleTodo: (TodoItem) -> Unit, onRemoveTodo: (TodoItem) -> Unit) {
    Column {
        TodoInput(onAddTodo)
        LazyColumn {
            items(items = todos, key = { it.id }) { todo ->
                TodoItemComposable(todo, onToggleTodo, onRemoveTodo)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoApp(viewModel: TodoAppViewModel = viewModel()) {
    val todos by viewModel.todos.collectAsState()

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Todo List") }) },
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TodoList(
                todos = todos,
                onAddTodo = viewModel::addTodo,
                onToggleTodo = viewModel::toggleTodo,
                onRemoveTodo = viewModel::removeTodo
            )
        }
    }
}


@Composable
fun TodoItemComposable(todo: TodoItem, onToggle: (TodoItem) -> Unit, onRemove: (TodoItem) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()

    ) {
        Checkbox(
            checked = todo.taskCompleted,
            onCheckedChange = { onToggle(todo) }
        )
        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
        Text(
            text = todo.taskName,
            style =
            if (todo.taskCompleted)
                MaterialTheme.typography.bodyLarge
                    .copy(textDecoration = TextDecoration.LineThrough)
            else
                MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
        IconButton(onClick = { onRemove(todo) }) {
            Icon(Icons.Default.Delete, contentDescription = "Delete")
        }
    }
}

@Composable
fun TodoInput(onAddTodo: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(1f)
    ){
        OutlinedTextField(
            value = text,
            modifier = Modifier.fillMaxWidth(1f),
            onValueChange = { text = it },
            label = { Text("Add Todo") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                if (text.isNotBlank()) {
                    onAddTodo(text)
                    text = ""
                }
            })
        )
        Spacer(modifier = Modifier.padding(vertical = 4.dp))
        Box(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth(1f)
                .background(MaterialTheme.colorScheme.primary)
        )
    }

}
