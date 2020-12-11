package by.dubrovskaya.definition.model;

import by.dubrovskaya.definition.model.enumeration.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

@Entity
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;
    private String compiledNgrams;
    @Enumerated(EnumType.STRING)
    private Language language;

    private Instant created;

    @PrePersist
    void created() {
        this.created = Instant.now();
    }
}
