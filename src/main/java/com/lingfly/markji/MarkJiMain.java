package com.lingfly.markji;

import com.alibaba.fastjson.JSONObject;
import com.common.HttpUtil;
import com.lingfly.moji.MojiData;
import com.lingfly.moji.MojiWord;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarkJiMain {


    public void getSpell(List<MojiData> dataList) throws IOException {
        SpellReq req = new SpellReq();
        List<SpellReq.Content> contents = new ArrayList<>();
        SpellReq.Content content = new SpellReq.Content("ja-JP");
        contents.add(content);
        req.content_slices = contents;

        Map<String, String> headers = new HashMap<>();
        headers.put("Token", "4730e3310be989ed2cffa7919f37e4d43a39e829153d07232ff291ca17b13891");

        for (MojiData data : dataList) {
            String text = (data.getPron() == null || data.getPron().isEmpty()) ? data.getSpell() : data.getPron();
            if (text == null || text.isEmpty()) {
                continue;
            }
            content.setText(text);
            JSONObject resp = HttpUtil.post("https://www.markji.com/api/v1/files/tts", req, JSONObject.class, headers);
            resp = HttpUtil.post("https://www.markji.com/api/v1/files/url", resp.getJSONObject("data"), JSONObject.class, headers);
            String spellId = resp.getJSONObject("data").getJSONObject("file").getString("id");
            data.setSpellId(spellId);
        }
    }

    public void generateCard(List<MojiData> dataList) throws FileNotFoundException {
        final String newline = "%%%";
        try (PrintStream out = new PrintStream("src/main/resources/card.txt")) {
            for (int i = 0; i < dataList.size(); i++) {
                if (i % 10 == 0) {
                    System.out.println("card: " + i);
                }
                MojiData data = dataList.get(i);
                out.printf("[T#B#%s]%n", data.getSpell());
                out.println("---");
                out.printf("%s %s [Audio#ID/%s#]%n", data.getPron(), data.getAccent(), data.getSpellId());
                out.println("---");
                out.println(data.getType());

                int index = 1;
                for (MojiWord.Subdetail subdetail : data.getSubdetails()) {
                    out.println();
                    if (data.getSubdetails().size() > 1) {
                        out.print(index++ + ". ");
                    }
                    out.printf("[T#B#%s]%n", replace(subdetail.getTitle()));
                    for (MojiWord.Example example : subdetail.getExamples()) {
                        out.println();
                        out.println(example.getTitle());
                        out.printf("[T#!90959b#%s]%n", example.getTrans());
                    }
                }
                out.print("\n" + newline + "\n\n");
            }
        }


    }

    public static String replace(String src) {
        String s = src.replaceAll("\\[", "\\\\[");
        s = s.replaceAll("\\r\\n|\\n", " ");
        return s.replaceAll("]", "\\\\]");
    }


}
