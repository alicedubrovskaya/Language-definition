package by.dubrovskaya.definition.service;

import by.dubrovskaya.definition.model.result.NgramMethodRecognitionResult;
import org.springframework.web.multipart.MultipartFile;

public interface RecognizerService {
    NgramMethodRecognitionResult recognizeByNgramMethod(MultipartFile file);
}
