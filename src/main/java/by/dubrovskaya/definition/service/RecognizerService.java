package by.dubrovskaya.definition.service;

import by.dubrovskaya.definition.model.result.AlphabeticMethodResult;
import by.dubrovskaya.definition.model.result.NgramMethodResult;
import by.dubrovskaya.definition.model.result.OwnMethodResult;
import org.springframework.web.multipart.MultipartFile;

public interface RecognizerService {
    NgramMethodResult recognizeByNgramMethod(MultipartFile file);

    AlphabeticMethodResult recognizeByAlphabetMethod(MultipartFile file);

    OwnMethodResult recognizeByOwnMethod(MultipartFile file);
}
