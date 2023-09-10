package com.example.wineyapi.tastingNote.controller;

import com.example.wineyapi.tastingNote.dto.TastingNoteRequest;
import com.example.wineyapi.tastingNote.dto.TastingNoteResponse;
import com.example.wineyapi.tastingNote.service.TastingNoteService;
import com.example.wineycommon.annotation.ApiErrorCodeExample;
import com.example.wineycommon.exception.errorcode.RequestErrorCode;
import com.example.wineycommon.reponse.CommonResponse;
import com.example.wineycommon.reponse.PageResponse;
import com.example.wineydomain.tastingNote.exception.UploadTastingNoteErrorCode;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.user.exception.UserAuthErrorCode;
import com.example.wineydomain.wine.entity.Country;
import com.example.wineydomain.wine.entity.WineType;
import com.example.wineyinfrastructure.amazonS3.exception.FileUploadException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasting-notes")
@Tag(name = "03-Tasting Note📝",description = "테이스팅 노트 관련 API")
@Slf4j
public class TastingNoteController {
    private final TastingNoteService tastingNoteService;

    @GetMapping("/{noteId}")
    public CommonResponse<TastingNoteResponse.TastingNoteDTO> getTastingNote(@PathVariable Long userId,
                                                                             @PathVariable Long noteId) {
        return null;
    }

    @GetMapping("")
    @Operation(summary = "03-02 테이스팅 노트📝 조회 API 입니다 #FRAME_노트_리스트 조회", description = "노트 리스트 조회")
    @ApiErrorCodeExample(UserAuthErrorCode.class)
    public CommonResponse<PageResponse<List<TastingNoteResponse.TastingNoteListDTO>>> getTastingNoteList(@Parameter(hidden = true) @AuthenticationPrincipal User user,
                                                                                                         @Parameter(description = "페이지", example = "0") @RequestParam(required = false, defaultValue = "0") Integer page,
                                                                                                         @Parameter(description = "페이지 사이즈", example = "10") @RequestParam(required = false, defaultValue = "10")  Integer size,
                                                                                                         @Parameter(description = "최신순 = 0, 평점순 = 1 기본 최신순 정렬입니다.", example = "0") @RequestParam(required = false, defaultValue = "0") Integer order,
                                                                                                         @Parameter(description = "생산지 국가입니다. List<String> 형식입니다.") @RequestParam(required = false) List<Country> countries,
                                                                                                         @Parameter(description = "와인 타입입니다. List<String> 형식입니다.") @RequestParam(required = false) List<WineType> wineTypes,
                                                                                                         @Parameter(description = "와인 재구매 유무 입니다.") @RequestParam(required = false) Integer buyAgain
     ) {
        return CommonResponse.onSuccess(tastingNoteService.getTastingNoteList(user, page, size,order, countries, wineTypes, buyAgain));
    }

    @RequestMapping(value = "", consumes = {"multipart/form-data"}, method = RequestMethod.POST)
    @Operation(summary = "03-01 테이스팅 노트📝 작성 API 입니다. multi-form data 형식입니다.  02-03 와인 검색용 API 와 함께 사용. #FRAME_노트 작성", description = "테이스팅 노트 작성 API")
    @ApiErrorCodeExample({UploadTastingNoteErrorCode.class, UserAuthErrorCode.class, FileUploadException.class, RequestErrorCode.class})
    public CommonResponse<TastingNoteResponse.CreateTastingNoteDTO> createTastingNote(@Parameter(hidden = true) @AuthenticationPrincipal User user,
                                                                                      @Valid @ModelAttribute TastingNoteRequest.CreateTastingNoteDTO request) {
        return CommonResponse.onSuccess(tastingNoteService.createTastingNote(user, request));
    }

    @PatchMapping("{noteId}")
    public CommonResponse<TastingNoteResponse.UpdateTastingNoteDTO> updateTastingNote(@PathVariable Long userId,
                                                                                      @PathVariable Long noteId,
                                                                                      @RequestBody TastingNoteRequest.UpdateTastingNoteDTO request) {
        return null;
    }

    @DeleteMapping("{noteId}")
    public CommonResponse<TastingNoteResponse.DeleteTastingNoteDTO> deleteTastingNote(@PathVariable Long userId,
                                                                                      @PathVariable Long noteId) {
        return null;
    }
}
