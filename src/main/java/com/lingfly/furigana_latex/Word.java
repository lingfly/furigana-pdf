package com.lingfly.furigana_latex;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Word {

    private String furigana;
    private String surface;
    private String roman;
    private List<Word> subword;

}
