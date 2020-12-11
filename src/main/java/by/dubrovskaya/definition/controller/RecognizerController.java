package by.dubrovskaya.definition.controller;

import by.dubrovskaya.definition.model.result.NgramMethodRecognitionResult;
import by.dubrovskaya.definition.service.RecognizerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/language/recognition")
@RequiredArgsConstructor
public class RecognizerController {

    private final RecognizerService recognizerService;

    @GetMapping("/ngram")
    public NgramMethodRecognitionResult recognizeByNgramMethod(@RequestParam("file") MultipartFile file) {
        return recognizerService.recognizeByNgramMethod(file);
    }
}
