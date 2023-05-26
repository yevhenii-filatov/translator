package com.nadia.translator.configuration;

import com.nadia.translator.model.Dictionary;
import com.nadia.translator.model.Language;
import com.nadia.translator.model.Translation;
import com.nadia.translator.model.Word;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

/**
 * @author Yevhenii Filatov
 * @since 5/26/23
 */

@Configuration
public class DictionaryInitializer {
    @Bean
    public Dictionary dictionary() {
        Dictionary dictionary = new Dictionary();
        for (Language language : Language.values()) {
            ResourceBundle languageBundle = ResourceBundle.getBundle("dictionary", language.getLocale());
            for (String key : languageBundle.keySet()) {
                Word source = new Word(key, Language.EN);
                Word target = new Word(languageBundle.getString(key), language);
                Translation translation = new Translation(source, target);
                dictionary.addTranslation(translation);
            }
        }
        return dictionary;
    }
}
