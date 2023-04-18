package com.github.ronlievens.spring.todo.model.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Locale;

@Getter
@RequiredArgsConstructor
public enum LanguageType {
    EN("en", "EN"),
    NL("nl", "NL"),
    DE("de", "DE");

    private final String language;
    private final String country;


    public Locale getLocale() {
        return new Locale(language, country);
    }

    public static LanguageType of(final Locale locale) {
        if (locale == null) {
            return null;
        }

        return Arrays.stream(values())
            .filter(value -> value.getLanguage().equals(locale.getLanguage()))
            .findFirst()
            .orElse(null);
    }
}
