package ru.liga.oldrussiantinderbot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.liga.oldrussiantinderbot.utils.Translator;

@SpringBootTest
class OldRussianTinderBotApplicationTests {

    @Test
    void testTranslatorNames() {
        String s = new Translator().translateInOldLanguage("Агафья");
        String translate = new Translator().translateInOldLanguage("Артем");
        String translateLast = new Translator().translateInOldLanguage("Елизавета");
        Assertions.assertEquals("Агаѳья",s);
        Assertions.assertEquals("Артемъ",translate);
        Assertions.assertEquals("Елизавета",translateLast);
    }
    @Test
    void test_Translate_Another_String(){
        String s = new Translator().translateInOldLanguage("Молодой старый сильный слабый открытый зактрытый перекрытый тестовый");
        Assertions.assertEquals("Молодой старый сильный слабый открытый зактрытый перекрытый тестовый",s);
        String translateLast = new Translator().translateInOldLanguage("мяч уж замуж невтерпеж бег беглец обед сыроежка");
        Assertions.assertEquals("мячъ ужъ замужъ невтерпежъ бегъ беглецъ обедъ сыроѣжка",translateLast);

    }
    @Test
    void translate_Fita_To_Old_Language(){
        String translate = new Translator().translateInOldLanguage("Афанасий, Афина, Феофан, Фоминична, Фома, Федя, Федор");
        Assertions.assertEquals("Афанасiй, Аѳина, Ѳеофанъ, Ѳоминична, Ѳома, Ѳедя, Федоръ",translate);
    }

    @Test
    void translate_yat_to_old_language(){
        String translate = new Translator().translateInOldLanguage("беда, бедный, победить, убеждение");
        Assertions.assertEquals("бѣда, бѣдный, победитьъ, убежденiе",translate);
    }

}
