package com.nadia.translator.model;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
   "word",
   "language"
})
@XmlRootElement(name = "TranslateRequest")
public class TranslateRequest {

    @XmlElement(required = true)
    private String word;

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    private Language language;
}
