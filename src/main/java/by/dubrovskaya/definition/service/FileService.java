package by.dubrovskaya.definition.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
  void storeFile(MultipartFile file, String language);

  String getFromFile(String filePath);
}
