package com.nadia.translator.controller;

import com.nadia.translator.model.Language;
import com.nadia.translator.model.TranslateRequest;
import com.nadia.translator.model.TranslateResponse;
import com.nadia.translator.model.Word;
import com.nadia.translator.service.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.Optional;

/**
 * @author Yevhenii Filatov
 * @since 5/26/23
 */

@Endpoint
@RequiredArgsConstructor
public class TranslationSoapEndpoint {
    private static final String NAMESPACE_URI = "http://example.com/translator";
    private final TranslationService translationService;

    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "TranslateRequest")
    public TranslateResponse translate(@RequestPayload TranslateRequest request) {
        Optional<Word> translation = translationService.translate(
           new Word(request.getWord(), Language.EN),
           request.getLanguage()
        );
        return new TranslateResponse(translation.orElse(null));
    }
}
