package by.dubrovskaya.definition.service;

import by.dubrovskaya.definition.model.OwnMethodResult;
import org.springframework.web.multipart.MultipartFile;

public interface OwnRecognizerService {
    OwnMethodResult recognize(MultipartFile file);
}
