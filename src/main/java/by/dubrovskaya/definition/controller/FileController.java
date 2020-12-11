package by.dubrovskaya.definition.controller;

import by.dubrovskaya.definition.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping
    public void uploadDocument(@RequestParam("file") MultipartFile file,
                               @RequestParam("language") String language) {
        fileService.storeFile(file,language);

    }
}
