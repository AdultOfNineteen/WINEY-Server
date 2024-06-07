package com.example.wineyapi.tastingNote.service;

import com.example.wineyapi.tastingNote.dto.TastingNoteRequest;
import com.example.wineyapi.tastingNote.dto.TastingNoteResponse;
import com.example.wineycommon.reponse.PageResponse;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.wine.entity.Country;
import com.example.wineydomain.wine.entity.WineType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TastingNoteService {
    TastingNoteResponse.CreateTastingNoteDTO createTastingNote(User user, TastingNoteRequest.CreateTastingNoteDTO request, List<MultipartFile> multipartFiles);

    PageResponse<List<TastingNoteResponse.TastingNoteListDTO>> getTastingNoteList(User user, Integer page, Integer size, Integer order, List<Country> countries, List<WineType> wineTypes, Integer isBuyAgain);

    TastingNoteResponse.TasteAnalysisDTO tasteAnalysis(User user);

    TastingNoteResponse.CheckTastingNote checkTastingNote(User user);

    TastingNoteResponse.TastingNoteDTO getTastingNote(User user, Long noteId);

    TastingNoteResponse.NoteFilterDTO getNoteFilter(User user);
  
    void deleteTastingNote(User user, Long noteId);

    void updateTastingNote(User user, TastingNoteRequest.UpdateTastingNoteDTO request, List<MultipartFile> multipartFiles,
        Long noteId);
}
