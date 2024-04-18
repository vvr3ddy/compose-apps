package uk.co.vvreddy.nocjournal.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import uk.co.vvreddy.nocjournal.models.NoteModel
import java.util.concurrent.atomic.AtomicLong

class AppViewModel: ViewModel() {
    private val _notes = MutableStateFlow<List<NoteModel>>(emptyList())
    val notes = _notes.asStateFlow()

    private val nextId = AtomicLong(1)

    fun addNote(title: String, content: String){
        val newList = _notes.value.toMutableList()
        newList.add(
            NoteModel(
                id = nextId.getAndIncrement(),
                title = title,
                content = content
            )
        )
        _notes.value = newList
    }

    fun deleteNote(note: NoteModel) {
        val newList = _notes.value.toMutableList()
        if(newList.contains(note)) newList.remove(note)
        _notes.value = newList
    }
}