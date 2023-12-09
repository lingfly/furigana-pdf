package com.lingfly.moji;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class MojiData {
    String spell;
    String accent;
    String pron;
    String type;
    String spellId;
    List<MojiWord.Subdetail> subdetails;


    public MojiData(MojiWord word) {
        this.spell = word.getWord().getSpell();
        this.accent = word.getWord().getAccent();
        this.pron = word.getWord().getPron();
        this.type = word.getDetails().get(0).getTitle();

        for (MojiWord.Subdetail subdetail : word.getSubdetails()) {
            MojiWord.Subdetail sub = new MojiWord.Subdetail();
            sub.setTitle(subdetail.getTitle());
            for (MojiWord.Example example : word.getExamples()) {
                if (example.getSubdetailsId().equals(subdetail.getObjectId())) {
                    subdetail.getExamples().add(example);
                }
            }
        }
        this.subdetails = word.getSubdetails();
    }
}
