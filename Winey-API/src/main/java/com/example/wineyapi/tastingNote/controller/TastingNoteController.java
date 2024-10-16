package com.example.wineyapi.tastingNote.controller;

import com.example.wineyapi.tastingNote.dto.TastingNoteRequest;
import com.example.wineyapi.tastingNote.dto.TastingNoteResponse;
import com.example.wineyapi.tastingNote.service.TastingNoteService;
import com.example.wineyapi.wine.dto.WineResponse;
import com.example.wineycommon.annotation.ApiErrorCodeExample;
import com.example.wineycommon.exception.errorcode.RequestErrorCode;
import com.example.wineycommon.reponse.CommonResponse;
import com.example.wineycommon.reponse.PageResponse;
import com.example.wineydomain.tastingNote.exception.GetTastingNoteErrorCode;
import com.example.wineydomain.tastingNote.exception.UploadTastingNoteErrorCode;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.user.exception.UserAuthErrorCode;
import com.example.wineydomain.wine.entity.Country;
import com.example.wineydomain.wine.entity.WineType;
import com.example.wineyinfrastructure.amazonS3.exception.FileDeleteException;
import com.example.wineyinfrastructure.amazonS3.exception.FileUploadException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasting-notes")
@Tag(name = "04-Tasting Note📝",description = "테이스팅 노트 관련 API")
@Slf4j
public class TastingNoteController {
    private final TastingNoteService tastingNoteService;

    @Operation(summary= "04-04 테이스팅노트📝 테이스팅 노트 작성(구매의사) 유무 확인 API #FRAME 001_03_와인 취향 분석 Made By Austin",description = "내 취향 분석 API 입니다")
    @ApiErrorCodeExample(UserAuthErrorCode.class)
    @GetMapping("/check")
    public CommonResponse<TastingNoteResponse.CheckTastingNote> checkTastingNote(@Parameter(hidden = true) @AuthenticationPrincipal User user){
        return CommonResponse.onSuccess(tastingNoteService.checkTastingNote(user));
    }

    @GetMapping("/taste-analysis")
    @ApiErrorCodeExample(UserAuthErrorCode.class)
    @Operation(summary= "04-03 테이스팅노트📝 내 취향 분석 #FRAME 001_03_와인 취향 분석 Made By Austin",description = "내 취향 분석 API 입니다")
    public CommonResponse<TastingNoteResponse.TasteAnalysisDTO> tasteAnalysis(@Parameter(hidden = true) @AuthenticationPrincipal User user){
        return CommonResponse.onSuccess(tastingNoteService.tasteAnalysis(user));
    }


    @GetMapping("")
    @Operation(summary = "04-02 테이스팅 노트📝 조회 API 입니다 #FRAME_노트_리스트 조회 Made By Austin", description = "노트 리스트 조회")
    @ApiErrorCodeExample(UserAuthErrorCode.class)
    public CommonResponse<PageResponse<List<TastingNoteResponse.TastingNoteListDTO>>> getTastingNoteList(@Parameter(hidden = true) @AuthenticationPrincipal User user,
                                                                                                         @Parameter(description = "페이지", example = "0") @RequestParam(required = false, defaultValue = "0") Integer page,
                                                                                                         @Parameter(description = "페이지 사이즈", example = "10") @RequestParam(required = false, defaultValue = "10")  Integer size,
                                                                                                         @Parameter(description = "최신순 = 0, 평점순 = 1 기본 최신순 정렬입니다.", example = "0") @RequestParam(required = false, defaultValue = "0") Integer order,
                                                                                                         @Parameter(description = "생산지 국가입니다. List<String> 형식입니다.", required = false) @RequestParam(required = false) List<Country> countries,
                                                                                                         @Parameter(description = "와인 타입입니다. List<String> 형식입니다.", required = false) @RequestParam(required = false) List<WineType> wineTypes,
                                                                                                         @Parameter(description = "와인 재구매 유무 입니다.") @RequestParam(required = false) Integer buyAgain,
                                                                                                         @Parameter(description = "테이스팅 노트에서 와인 ID 로 검색할 때 사용하는 파라미터") @RequestParam(required = false) Long wineId
     ) {
        return CommonResponse.onSuccess(tastingNoteService.getTastingNoteList(user, page, size,order, countries, wineTypes, buyAgain, wineId));
    }

    @RequestMapping(value = "", consumes = {"multipart/form-data"}, method = RequestMethod.POST)
    @Operation(summary = "04-01 테이스팅 노트📝 작성 API 입니다. multi-form data 형식입니다.  02-03 와인 검색용 API 와 함께 사용. #FRAME_노트 작성 Made By Austin", description = "테이스팅 노트 작성 API")
    @ApiErrorCodeExample({UploadTastingNoteErrorCode.class, UserAuthErrorCode.class, FileUploadException.class, RequestErrorCode.class})
    public CommonResponse<TastingNoteResponse.CreateTastingNoteDTO> createTastingNote(@Parameter(hidden = true) @AuthenticationPrincipal User user,
                                                                                      @Valid @RequestPart TastingNoteRequest.CreateTastingNoteDTO request,
                                                                                      @RequestPart(required = false) List<MultipartFile> multipartFiles) {
        return CommonResponse.onSuccess(tastingNoteService.createTastingNote(user, request, multipartFiles));
    }

    @GetMapping("/{noteId}")
    @Operation(summary= "04-05 테이스팅노트📝 테이스팅 상세조회 #FRAME 001_03_테이스팅 노트 상세조회 Made By Austin ",description = "상세조회 API 입니다")
    @ApiErrorCodeExample(UserAuthErrorCode.class)
    public CommonResponse<TastingNoteResponse.TastingNoteDTO> getTastingNote(
        @AuthenticationPrincipal User user,
        @PathVariable Long noteId,
        @RequestParam(defaultValue = "false") boolean isShared
    ) {
        return CommonResponse.onSuccess(tastingNoteService.getTastingNote(user, noteId, isShared));
    }

    @GetMapping("/filter")
    @Operation(summary= "04-05 테이스팅노트📝 테이스팅 상세조회 #FRAME 002_01_노트_필터 설정 조회 Made By Austin ",description = "필터 조회 API 입니다")
    @ApiErrorCodeExample(UserAuthErrorCode.class)
    public CommonResponse<TastingNoteResponse.NoteFilterDTO> getNoteFilter(@Parameter(hidden = true) @AuthenticationPrincipal User user){
        return CommonResponse.onSuccess(tastingNoteService.getNoteFilter(user));
    }


    @RequestMapping(value = "/{noteId}", consumes = {"multipart/form-data"}, method = RequestMethod.PATCH)
    @Operation(summary= "04-06 테이스팅노트📝 테이스팅 수정 #FRAME 수정 Made By Austin ",description = "수정 API 입니다")
    public CommonResponse<TastingNoteResponse.UpdateTastingNoteDTO> updateTastingNote(
        @AuthenticationPrincipal User user,
        @PathVariable Long noteId,
        @Valid @RequestPart TastingNoteRequest.UpdateTastingNoteDTO request,
        @RequestPart(required = false) List<MultipartFile> multipartFiles) {
        tastingNoteService.updateTastingNote(user, request, multipartFiles, noteId);
        return CommonResponse.onSuccess(new TastingNoteResponse.UpdateTastingNoteDTO(noteId));
    }

    @DeleteMapping("{noteId}")
    @ApiErrorCodeExample({UserAuthErrorCode.class, GetTastingNoteErrorCode.class})
    public CommonResponse<String> deleteTastingNote(@Parameter(hidden = true) @AuthenticationPrincipal User user,
                                                                                      @PathVariable Long noteId) {
        tastingNoteService.deleteTastingNote(user, noteId);
        return CommonResponse.onSuccess("삭제 성공");
    }
}
