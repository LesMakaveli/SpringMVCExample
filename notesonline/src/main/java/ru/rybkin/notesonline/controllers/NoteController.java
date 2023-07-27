package ru.rybkin.notesonline.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.rybkin.notesonline.models.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteController {
    private List<Note> notes = new ArrayList<>();

    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("notes", notes);
        return "index";
    }

    @GetMapping("/note/create")
    public String showCreateForm(Model model) {
        model.addAttribute("note", new Note());
        return "create";
    }

    @PostMapping("/note/create")
    public String createNote(@ModelAttribute Note note) {
        note.setId(System.currentTimeMillis()); // Просто временное уникальное значение для id
        notes.add(note);
        return "redirect:/";
    }

    @GetMapping("/note/{id}")
    public String viewNote(@PathVariable Long id, Model model) {
        Note note = findNoteById(id);
        if (note != null) {
            model.addAttribute("note", note);
            return "view";
        }
        return "redirect:/";
    }


    @GetMapping("/note/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Note note = findNoteById(id);
        if (note != null) {
            model.addAttribute("note", note);
            return "edit";
        }
        return "redirect:/";
    }

    @PostMapping("/note/edit")
    public String editNote(@ModelAttribute Note updatedNote) {
        Note note = findNoteById(updatedNote.getId());
        if (note != null) {
            note.setTitle(updatedNote.getTitle());
            note.setContent(updatedNote.getContent());
        }
        return "redirect:/";
    }

    @GetMapping("/note/delete/{id}")
    public String deleteNote(@PathVariable Long id) {
        Note note = findNoteById(id);
        if (note != null) {
            notes.remove(note);
        }
        return "redirect:/";
    }

    private Note findNoteById(Long id) {
        return notes.stream().filter(note -> note.getId().equals(id)).findFirst().orElse(null);
    }
}
