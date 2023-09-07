package com.example.wineyapi.tastingNote.controller;

import com.example.wineyapi.tastingNote.dto.TastingNoteRequest;
import com.example.wineyapi.tastingNote.dto.TastingNoteResponse;
import com.example.wineycommon.reponse.CommonResponse;
import com.example.wineydomain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @Deprecated
    @RequestMapping(value = "/users/tasting-notes", consumes = {"multipart/form-data"}, method = RequestMethod.POST)
    @Operation(summary = "테이스팅 노트 작성 API 입니다. multi-form data 형식입니다.", description = "테이스팅 노트 작성 API")
    public CommonResponse<TastingNoteResponse.CreateTastingNoteDTO> createTastingNote(@Parameter(hidden = true) @AuthenticationPrincipal User user,
                                                                                      @ModelAttribute TastingNoteRequest.CreateTastingNoteDTO request) {
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
