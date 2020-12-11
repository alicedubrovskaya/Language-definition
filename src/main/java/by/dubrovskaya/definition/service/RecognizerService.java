package by.dubrovskaya.definition.service;

import by.dubrovskaya.definition.model.result.AlphabeticMethodResult;
import by.dubrovskaya.definition.model.result.NgramMethodResult;
import org.springframework.web.multipart.MultipartFile;

public interface RecognizerService {
    NgramMethodResult recognizeByNgramMethod(MultipartFile file);

    AlphabeticMethodResult recognizeByAlphabetMethod(MultipartFile file);
}
