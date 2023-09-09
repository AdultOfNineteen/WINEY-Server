package com.example.wineyapi.tastingNote.controller;

import com.example.wineyapi.tastingNote.dto.TastingNoteRequest;
import com.example.wineyapi.tastingNote.dto.TastingNoteResponse;
import com.example.wineyapi.tastingNote.service.TastingNoteService;
import com.example.wineycommon.annotation.ApiErrorCodeExample;
import com.example.wineycommon.reponse.CommonResponse;
import com.example.wineydomain.tastingNote.exception.UploadTastingNoteErrorCode;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.user.exception.UserAuthErrorCode;
import com.example.wineyinfrastructure.amazonS3.exception.FileUploadException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TastingNoteController {
    private final TastingNoteService tastingNoteService;

    @GetMapping("/users/{userId}/tasting-notes/{noteId}")
    public CommonResponse<TastingNoteResponse.TastingNoteDTO> getTastingNote(@PathVariable Long userId,
                                                                             @PathVariable Long noteId) {
        return null;
    }

    @GetMapping("/users/{userId}/tasting-notes")
    public CommonResponse<TastingNoteResponse.TastingNoteListDTO> getTastingNoteList(@PathVariable Long userId) {
        return null;
    }

    @RequestMapping(value = "/users/tasting-notes", consumes = {"multipart/form-data"}, method = RequestMethod.POST)
    @Operation(summary = "03-01 테이스팅 노트 작성 API 입니다. multi-form data 형식입니다. #FRAME_노트 작성", description = "테이스팅 노트 작성 API")
    @ApiErrorCodeExample({UploadTastingNoteErrorCode.class, UserAuthErrorCode.class, FileUploadException.class})
    public CommonResponse<TastingNoteResponse.CreateTastingNoteDTO> createTastingNote(@Parameter(hidden = true) @AuthenticationPrincipal User user,
                                                                                      @Valid @ModelAttribute TastingNoteRequest.CreateTastingNoteDTO request) {
        return  CommonResponse.onSuccess(tastingNoteService.createTastingNote(user, request));
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
