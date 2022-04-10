package ru.liga.oldrussiantinderbot.utils;

import org.springframework.stereotype.Component;
import ru.liga.oldrussiantinderbot.repository.FileRepository;
import ru.liga.oldrussiantinderbot.repository.NamesRepository;
import ru.liga.oldrussiantinderbot.repository.WordsRepository;

import java.util.Arrays;
import java.util.List;
@Component
public class Translator {

    private static final List<Character> VOWELS = Arrays.asList('а', 'о', 'э', 'е',
            'и', 'ы', 'у', 'ё', 'ю', 'я', 'й');
    public static final String TEXT_DELIMITER = "[ \\n]";

    public String translateInOldLanguage(String text) {
        String[] textArray = text.split(TEXT_DELIMITER);
        StringBuilder sb = new StringBuilder();

        FileRepository namesRepository = new NamesRepository();
        FileRepository wordsRepository = new WordsRepository();
        for (String string : textArray) {
            Character lastChar = string.charAt(string.length()-1);
            if (Character.toString(lastChar).matches("\\p{Punct}")){
                string = string.substring(0, string.length() - 1);
            } else {
                lastChar = null;
            }
            string = replaceI(addHardSign(string));
            string = changeWordWithReplacedLetter('Ф','Ѳ', namesRepository, string);
            string = changeWordWithReplacedLetter('ф', 'ѳ', namesRepository, string);
            string = changeWordWithReplacedLetter('е', 'ѣ', wordsRepository, string);
            string = changeWordWithReplacedLetter('Е', 'ѣ', wordsRepository, string);
            sb.append(string);
            if (lastChar!=null) {
                sb.append(lastChar);
            }
            sb.append(" ");
        }
        return sb.toString().trim();
    }

    private String addHardSign(String string) {
        for (char letter : VOWELS) {
            if (string.endsWith(String.valueOf(letter))) {
                return string;
            }
        }
        return string  + "ъ";
    }

    private String replaceI(String string) {
        char[] charArray = string.toCharArray();
        for (int i = 0; i < string.length(); i++) {
            if (charArray[i] == 'и' && VOWELS.contains(charArray[i + 1])) {
                charArray[i] = 'i';
            }
        }
        return new String(charArray);
    }

    private String changeWordWithReplacedLetter(char modernChar, char oldChar, FileRepository repository, String string) {
        List<String> strings = repository.getWords();
        String copyString = string;
        int index = copyString.indexOf(modernChar);
        while (index != -1) {
            copyString = copyString.replace(modernChar, oldChar);
            if (strings.contains(copyString)) {
                return copyString;
            }
            index = copyString.substring(index + 1).indexOf(modernChar);
        }
        return string;
    }

}
