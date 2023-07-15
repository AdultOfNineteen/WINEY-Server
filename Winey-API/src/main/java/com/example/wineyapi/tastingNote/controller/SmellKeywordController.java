package com.example.wineyapi.tastingNote.controller;

import com.example.wineyapi.tastingNote.dto.SmellKeywordResponse;
import com.example.wineycommon.reponse.CommonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmellKeywordController {

    @GetMapping("/tasting-notes/smell-keywords")
    public CommonResponse<SmellKeywordResponse.SmellKeywordListDTO> getSmellKeywordList() {
        return null;
    }
}
