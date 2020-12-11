package by.dubrovskaya.definition.model.result;

import by.dubrovskaya.definition.model.enumeration.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Pair;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AlphabeticMethodResult {

    private Language language;
    private Double languageAlphabetFrequencyRatio;


    public static AlphabeticMethodResult of(Pair<Language, Double> languageToFrequencyRatio) {
        return new AlphabeticMethodResult(languageToFrequencyRatio.getFirst(),
                languageToFrequencyRatio.getSecond());
    }
}
