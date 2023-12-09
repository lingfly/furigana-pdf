package com.lingfly.moji;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class MojiWord {

    private Word word;
    private List<Detail> details;
    private List<Subdetail> subdetails;
    private List<Example> examples;

    @Getter
    @Setter
    public static class Word {
        private String spell;
        private String accent;
        private String pron;
    }

    @Getter
    @Setter
    public static class Detail {
        private String title;

    }

    @Getter
    @Setter
    public static class Subdetail {
        private String objectId;
        private String title;

        private List<Example> examples = new ArrayList<>();
    }

    @Getter
    @Setter
    public static class Example {
        private String subdetailsId;
        private String title;
        private String trans;
    }

}
