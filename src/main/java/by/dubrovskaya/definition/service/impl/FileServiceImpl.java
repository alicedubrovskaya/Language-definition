package by.dubrovskaya.definition.service.impl;

import by.dubrovskaya.definition.exception.ServiceException;
import by.dubrovskaya.definition.service.FileService;
import by.dubrovskaya.definition.service.HtmlParser;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final HtmlParser htmlParser;

    @SneakyThrows
    @Override
    public void storeFile(MultipartFile file, String language) {
        validateFile(file);
        String parsedHtmlText = htmlParser.parse(new String(file.getBytes()));

    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ServiceException("Uploaded file is empty.Please try again");
        }
    }
}
