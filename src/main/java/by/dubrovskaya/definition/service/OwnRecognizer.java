package by.dubrovskaya.definition.service;

import by.dubrovskaya.definition.model.result.OwnMethodResult;
import org.springframework.web.multipart.MultipartFile;

public interface OwnRecognizer {
    OwnMethodResult recognize(MultipartFile file);
}
