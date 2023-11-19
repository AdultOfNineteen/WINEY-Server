package com.example.wineyapi.wineGrade.service;

import com.example.wineyapi.wineGrade.dto.WineGradeResponse;
import com.example.wineydomain.tastingNote.repository.TastingNoteRepository;
import com.example.wineydomain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WineGradeServiceImpl implements WineGradeService {

    private final UserRepository userRepository;
    private final TastingNoteRepository tastingNoteRepository;

    @Override
    public WineGradeResponse.GetWineGradeDTO getWineGrade(Long userId) {
        return null;
    }
}
