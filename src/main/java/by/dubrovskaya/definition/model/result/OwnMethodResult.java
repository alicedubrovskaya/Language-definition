package by.dubrovskaya.definition.model.result;


import by.dubrovskaya.definition.model.enumeration.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OwnMethodResult {

    private Language language;
    private Double englishWordsFrequency;
    private Double russianWordsFrequency;

}
