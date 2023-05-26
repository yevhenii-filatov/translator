package com.nadia.translator.service.impl;

import com.nadia.translator.model.Dictionary;
import com.nadia.translator.model.Language;
import com.nadia.translator.model.Translation;
import com.nadia.translator.model.Word;
import com.nadia.translator.service.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Yevhenii Filatov
 * @since 5/26/23
 */

@Service
@RequiredArgsConstructor
public class TranslationServiceImpl implements TranslationService {
    private final Dictionary dictionary;

    @Override
    public Optional<Word> translate(Word sourceWord, Language targetLanguage) {
        List<Translation> matchingTranslations = dictionary.translations()
           .stream()
           .filter(translation -> translation.source().text().equals(sourceWord.text())
              && translation.source().language() == sourceWord.language()
              && translation.target().language() == targetLanguage)
           .toList();
        return matchingTranslations.isEmpty() ? Optional.empty() : matchingTranslations.stream().findFirst().map(Translation::target);
    }
}
