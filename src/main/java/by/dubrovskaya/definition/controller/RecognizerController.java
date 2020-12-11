package by.dubrovskaya.definition.controller;

import by.dubrovskaya.definition.model.result.AlphabeticMethodResult;
import by.dubrovskaya.definition.model.result.NgramMethodResult;
import by.dubrovskaya.definition.service.RecognizerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/recognition")
@RequiredArgsConstructor
public class RecognizerController {

    private final RecognizerService recognizerService;

    @GetMapping("/ngram")
    public NgramMethodResult recognizeByNgramMethod(@RequestParam("file") MultipartFile file) {
        return recognizerService.recognizeByNgramMethod(file);
    }

    @GetMapping("/alphabet")
    public AlphabeticMethodResult recognizeByAlphabeticMethod(@RequestParam("file") MultipartFile file) {
        return recognizerService.recognizeByAlphabetMethod(file);
    }

}
