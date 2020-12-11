package by.dubrovskaya.definition.model.result;


import by.dubrovskaya.definition.model.Document;
import by.dubrovskaya.definition.model.enumeration.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NgramMethodResult {

    private Integer rank;
    private Language language;
    private String testedDocument;
    private Document foundedDocument;

}
