package com.lingfly.lanbaoshu;

import com.alibaba.fastjson.JSONObject;
import com.common.HttpUtil;
import com.lingfly.markji.SpellReq;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class Markdown2Markji {
    final static String newline = "%%%";
    final static String answerLine = "---";
    final static String title = "## ";
    final static String section = "### ";
    final static String n = "\n";
    final static String bold = "[T#B#%s]";
    final static String example = "* ";

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(new File("D:\\Note\\Japanese\\蓝宝书\\N5\\6.第六单元-并列助词、提示助词.md"));
        StringBuilder sb = new StringBuilder();
        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (line.startsWith("# ")) {
                System.out.println(line);
                sb.append("### ").append(line.substring(2)).append(n);
                continue;
            }
            if (line.startsWith(title)) {
                System.out.println(line);
                sb.append(newline).append(n);
                sb.append(line.substring(title.length())).append(n);

                sb.append(answerLine).append(n);
                continue;
            }

            if (line.startsWith(section)) {
                sb.append(String.format(bold, line.substring(section.length()))).append(n);
                continue;
            }

            if (line.startsWith(example)) {
                String[] exampleAndTrans = line.substring(example.length()).split("/");


                SpellReq req = new SpellReq();
                List<SpellReq.Content> contents = new ArrayList<>();
                SpellReq.Content content = new SpellReq.Content("ja-JP");
                contents.add(content);
                req.setContent_slices(contents);
                content.setText(exampleAndTrans[0]);

                Map<String, String> headers = new HashMap<>();
                headers.put("Token", "4730e3310be989ed2cffa7919f37e4d43a39e829153d07232ff291ca17b13891");
                JSONObject resp = HttpUtil.post("https://www.markji.com/api/v1/files/tts", req, JSONObject.class, headers);
                resp = HttpUtil.post("https://www.markji.com/api/v1/files/url", resp.getJSONObject("data"), JSONObject.class, headers);
                String spellId = resp.getJSONObject("data").getJSONObject("file").getString("id");

                sb.append(String.format("[Audio#ID/%s#%s]", spellId, exampleAndTrans[0]));

                //翻译字体为淡灰色
                sb.append(String.format("[T#!90959b#/%s]", exampleAndTrans[1]));
                sb.append(n);
                continue;
            }

            if (!line.isBlank()) {
                sb.append(line).append(n);
            }
            if (line.isBlank()) {
                sb.append(n);
            }

        }
        try (PrintStream out = new PrintStream("out.txt")) {
            out.println(sb);
        }

    }
}
