package ru.liga.oldrussiantinderbot.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Класс для парсинга текстовых файлов c именами
 */
public class TextFileParser {

    private static final String SEPARATOR = ",";

    /**
     * @param filePath путь к файлу для парсинга
     */
    public static List<String> parse(String filePath) {
        try (InputStream in = TextFileParser.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            return reader.lines()
                    .map(x -> x.split(SEPARATOR))
                    .flatMap(Arrays::stream)
                    .map(String::trim)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

