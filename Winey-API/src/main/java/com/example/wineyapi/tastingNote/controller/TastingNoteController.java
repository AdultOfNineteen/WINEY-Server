package com.example.wineyapi.tastingNote.controller;

import com.example.wineyapi.tastingNote.dto.TastingNoteRequest;
import com.example.wineyapi.tastingNote.dto.TastingNoteResponse;
import com.example.wineycommon.reponse.CommonResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class TastingNoteController {

    @GetMapping("/users/{userId}/tasting-notes/{noteId}")
    public CommonResponse<TastingNoteResponse.TastingNoteDTO> getTastingNote(@PathVariable Long userId,
                                                                             @PathVariable Long noteId) {
        return null;
    }

    @GetMapping("/users/{userId}/tasting-notes")
    public CommonResponse<TastingNoteResponse.TastingNoteListDTO> getTastingNoteList(@PathVariable Long userId) {
        return null;
    }

    @PostMapping("/users/{userId}/tasting-notes")
    public CommonResponse<TastingNoteResponse.CreateTastingNoteDTO> createTastingNote(@PathVariable Long userId,
                                                                                      @RequestBody TastingNoteRequest.CreateTastingNoteDTO request) {
        return null;
    }

    @PatchMapping("/users/{userId}/tasting-notes/{noteId}")
    public CommonResponse<TastingNoteResponse.UpdateTastingNoteDTO> updateTastingNote(@PathVariable Long userId,
                                                                                      @PathVariable Long noteId,
                                                                                      @RequestBody TastingNoteRequest.UpdateTastingNoteDTO request) {
        return null;
    }

    @DeleteMapping("/users/{userId}/tasting-notes/{noteId}")
    public CommonResponse<TastingNoteResponse.DeleteTastingNoteDTO> deleteTastingNote(@PathVariable Long userId,
                                                                                      @PathVariable Long noteId) {
        return null;
    }
}
