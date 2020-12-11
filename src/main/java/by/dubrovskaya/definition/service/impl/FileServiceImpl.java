package by.dubrovskaya.definition.service.impl;

import by.dubrovskaya.definition.exception.ServiceException;
import by.dubrovskaya.definition.model.Document;
import by.dubrovskaya.definition.model.enumeration.Language;
import by.dubrovskaya.definition.repository.DocumentRepository;
import by.dubrovskaya.definition.service.FileService;
import by.dubrovskaya.definition.service.HtmlParser;
import by.dubrovskaya.definition.service.JsonComponent;
import by.dubrovskaya.definition.service.WordService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final HtmlParser htmlParser;
    private final JsonComponent json;
    private final WordService wordService;
    private final DocumentRepository documentRepository;

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
        documentRepository.save(document);
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ServiceException("Uploaded file is empty.Please try again");
        }
    }

    @Override
    public String getFromFile(String filePath) {
        String absoluteFilePath = new File(filePath).getAbsolutePath();
        StringBuilder result = new StringBuilder();

        try (FileReader fr = new FileReader(absoluteFilePath);
             Scanner in = new Scanner(fr);
        ) {
            in.useDelimiter("\n");
            while (in.hasNextLine()) {
                String line = in.nextLine();
                result.append(line + "\n");
            }
        } catch (IOException e) {
        }
        return result.toString();
    }
}
