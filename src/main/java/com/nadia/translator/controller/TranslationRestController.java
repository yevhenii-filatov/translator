package com.nadia.translator.controller;

import com.nadia.translator.model.Language;
import com.nadia.translator.model.Word;
import com.nadia.translator.service.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yevhenii Filatov
 * @since 5/26/23
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/translation")
public class TranslationRestController {
    private final TranslationService translationService;

    @GetMapping
    public ResponseEntity<Word> translate(@RequestParam String word,
                                          @RequestParam(defaultValue = "EN") Language language) {
        return ResponseEntity.of(translationService.translate(new Word(word, Language.EN), language));
    }
}
