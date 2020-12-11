package by.dubrovskaya.definition.model.enumeration;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Language {

    ENGLISH("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
    RUSSIAN("АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ");

    @Getter
    private String alphabet;
}