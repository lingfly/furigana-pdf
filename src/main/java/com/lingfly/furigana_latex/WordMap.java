package com.lingfly.furigana_latex;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WordMap {
    private String key;

    private List<Word> words;
}
