package com.lingfly.markji;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SpellReq {
    List<Content> content_slices;

    @Setter
    @Getter
    public static class Content {
        String text;
        String locale;

        public Content(String locale) {
            this.locale = locale;
        }
    }
}
