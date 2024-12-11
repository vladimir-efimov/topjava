package ru.javawebinar.topjava.web.converter;

import org.springframework.format.Formatter;

import java.util.Locale;

/**
 * Formatter which doesn't fail for empty value, allowing validator to process entity
 * and produce validation error with corresponding message if field has @NotNull annotation.
 */
public class IntegerFormatter implements Formatter<Integer> {

    @Override
    public Integer parse(String text, Locale locale) {
        return text == "" ? null : Integer.parseInt(text);
    }

    @Override
    public String print(Integer val, Locale locale) {
        return val == null ? "" : val.toString();
    }

}
