package uk.co.vvreddy.nocjournal

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.Send
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import uk.co.vvreddy.nocjournal.models.NoteModel
import uk.co.vvreddy.nocjournal.ui.theme.NocjournalTheme
import uk.co.vvreddy.nocjournal.viewmodels.AppViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NocjournalTheme {
                enableEdgeToEdge()
                Surface(
                    modifier = Modifier
                        .safeDrawingPadding()
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NocjournalScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NocjournalScreen(viewModel: AppViewModel = viewModel()) {
    val noteList by viewModel.notes.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = stringResource(R.string.app_name),
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.headlineLarge
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.TwoTone.Send,
                            contentDescription = "Navigation Icon",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ){
            NoteList(notes = noteList, addNote = viewModel::addNote, deleteNote = viewModel::deleteNote)
        }
    }
}

@Composable
fun NoteList(
    notes: List<NoteModel>,
    addNote: (String, String) -> Unit,
    deleteNote: (NoteModel) -> Unit) {

    val noteContent: MutableState<String> = remember { mutableStateOf("") }
    val alertVisible = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            OutlinedTextField(
                value = noteContent.value,
                onValueChange = {noteContent.value = it},
                label = { Text(text = "Add New Note") }
            )
            OutlinedIconButton(
                onClick = {
                    if(noteContent.value.isNotBlank()) alertVisible.value = true
                },
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.TwoTone.Send,
                    contentDescription = "Save Note"
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                start = 12.dp,
                top = 16.dp,
                end = 12.dp,
                bottom = 16.dp
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(items = notes, key = {it.id}) {note ->
                NoteItem(note, onDelete = {deleteNote(note)})
            }
        }
    }
    if(alertVisible.value){
        AddNotePrompt(content = noteContent, alertVisible, addNote = addNote)
    }

}

@Composable
fun NoteItem(note: NoteModel, onDelete: () -> Unit) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val screenHeight = LocalConfiguration.current.screenHeightDp
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.secondary
        ),
        modifier = Modifier
            .size(width = screenWidth * 0.375f.dp, height = screenHeight * 0.20f.dp)
            .padding(vertical = 8.dp)

    ){
        Column(
            modifier = Modifier.padding(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ){
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.labelLarge
                        .also { TextStyle(color = MaterialTheme.colorScheme.inverseSurface) }

                )
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.TwoTone.Delete,
                        contentDescription = "Delete Note",
                        modifier = Modifier.size(24.dp))
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = MaterialTheme.colorScheme.onTertiary)
            )
            Spacer(modifier = Modifier.padding(vertical = 2.dp))
            Text(
                text = note.content,
            )
        }
    }
}

@Composable
fun AddNotePrompt(
    content: MutableState<String>,
    alertVisible: MutableState<Boolean> = mutableStateOf(false),
    addNote: (String, String) -> Unit
) {
    val context = LocalContext.current.applicationContext
    var title by remember { mutableStateOf("") }
    Dialog(onDismissRequest = { alertVisible.value = false }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Save Note"
                )
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Note Title") },
                    maxLines = 1
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = {
                            if(title.isNotBlank()) {
                                addNote(title, content.value)
                                content.value = ""
                                alertVisible.value = false
                            }else{
                                Toast.makeText(context, "Title Cannot be Blank", Toast.LENGTH_SHORT).show()
                            }
                        }
                    ) {
                        Text("Save Note")
                    }
                    TextButton(
                        onClick = { alertVisible.value = false }
                    ) {
                        Text("Cancel Note")
                    }
                }
            }
        }
    }
}