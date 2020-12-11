package by.dubrovskaya.definition.service.impl;

import by.dubrovskaya.definition.exception.ServiceException;
import by.dubrovskaya.definition.model.Document;
import by.dubrovskaya.definition.model.enumeration.Language;
import by.dubrovskaya.definition.service.FileService;
import by.dubrovskaya.definition.service.HtmlParser;
import by.dubrovskaya.definition.service.JsonComponent;
import by.dubrovskaya.definition.service.WordService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final HtmlParser htmlParser;
    private final JsonComponent json;
    private final WordService wordService;

    @SneakyThrows
    @Override
    public void storeFile(MultipartFile file, String language) {
        validateFile(file);
        String parsedHtmlText = htmlParser.parse(new String(file.getBytes()));
        Map<String, Integer> ngramFrequencyMap = wordService.buildNgramFrequencyMap(parsedHtmlText);
        String ngramFrequencyJson = json.toJson(ngramFrequencyMap);

        Document document = Document.builder()
                .text(parsedHtmlText)
                .language(Language.valueOf(language))
                .compiledNgrams(ngramFrequencyJson)
                .build();
//        documentRepository.save(document);
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ServiceException("Uploaded file is empty.Please try again");
        }
    }
}
