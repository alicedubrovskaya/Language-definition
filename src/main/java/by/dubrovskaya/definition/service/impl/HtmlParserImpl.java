package by.dubrovskaya.definition.service.impl;

import by.dubrovskaya.definition.service.HtmlParser;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

@Component
public class HtmlParserImpl implements HtmlParser {
    @Override
    public String parse(String text) {
        return Jsoup.parse(text).text();
    }
}
