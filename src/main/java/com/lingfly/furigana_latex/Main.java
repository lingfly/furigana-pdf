package com.lingfly.furigana_latex;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class Main {
    private static final int grade = 2;
    public static void main(String[] args) throws FileNotFoundException {
        Main main = new Main();
        List<String> input = main.input();
        List<WordMap> words = main.getWord(input);
        StringBuilder text = main.text(words);
        main.output(text.toString());
    }

    List<String> input() throws FileNotFoundException {
        Scanner in = new Scanner(new File("src/main/resources/in.txt"));
        List<String> input = new ArrayList<>();
        StringBuilder text = new StringBuilder();
        while (in.hasNextLine()) {
            String line = in.nextLine();
            text.append(line);
            if (Constant.EOF.matcher(line).find()) {
                input.add(text.toString());
                text = new StringBuilder();
            }
        }
        return input;
    }

    void output(String text) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter("src/main/resources/out.tex");
        writer.println(text);
        writer.close();
    }

    StringBuilder text(List<WordMap> words) {
        words.sort(Comparator.comparing(WordMap::getKey));
        StringBuilder text = new StringBuilder();
        for (WordMap wordMap : words) {
            for (Word word : wordMap.getWords()) {
                if (word.getSubword() != null) {
                    for (Word sub : word.getSubword()) {
                        wordToRuby(sub, text);
                    }
                } else {
                    wordToRuby(word, text);
                }
            }
            text.append("\n\n");
        }
        return text;
    }

    void wordToRuby(Word word, StringBuilder text) {
        if (word.getFurigana() == null || word.getSurface().equals(word.getFurigana())) {
            text.append(word.getSurface());
        } else if (word.getSubword() == null) {
            text.append(Constant.RUBY);
            text.append(String.format(Constant.braces, word.getSurface()));
            text.append(String.format(Constant.braces, word.getFurigana()));
        }
    }

    List<WordMap> getWord(List<String> paragraphs) throws FileNotFoundException {
        String wordJson = "";
        Scanner in = new Scanner(new File("src/main/resources/word.json"));
        while (in.hasNext()) {
            wordJson += in.next();
        }
        if (!wordJson.isEmpty()) {
            return JSON.parseArray(wordJson, WordMap.class);
        }

        int count = 1;
        List<WordMap> words = new ArrayList<>();
        for (String paragraph : paragraphs) {
            WordMap wordMap = new WordMap();
            wordMap.setKey(String.valueOf(count++));
            wordMap.setWords(doGetWord(paragraph));
            words.add(wordMap);
        }

        PrintWriter writer = new PrintWriter("src/main/resources/word.json");
        writer.print(JSON.toJSONString(words));
        writer.close();
        return words;
    }

    List<Word> doGetWord(String text) {
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL("https://jlp.yahooapis.jp/FuriganaService/V2/furigana");
            HttpURLConnection connection = ((HttpURLConnection) url.openConnection());
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("User-Agent", "Yahoo AppID: dj00aiZpPVZKRHFzZHY4Y3RtaSZzPWNvbnN1bWVyc2VjcmV0Jng9Zjg-");
            FuriganaReq req = new FuriganaReq();
            ParamReq paramReq = new ParamReq();
            paramReq.setQ(text);
            paramReq.setGrade(grade);
            req.setParams(paramReq);
            byte[] bytes = JSON.toJSONBytes(req);
            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(bytes);
            }

            int responseCode = connection.getResponseCode();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSON.parseObject(response.toString());
        JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("word");
        return jsonArray.toJavaList(Word.class);
    }

    String format(StringBuilder text) throws FileNotFoundException {
        Scanner in = new Scanner(new File("src/main/resources/header.txt"));
        StringBuilder header = new StringBuilder();
        while (in.hasNextLine()) {
            header.append(in.nextLine()).append("\n");
        }
        in = new Scanner(new File("src/main/resources/tail.txt"));
        StringBuilder tail = new StringBuilder();
        while (in.hasNextLine()) {
            tail.append(in.nextLine()).append("\n");
        }
        text.insert(0, header);
        text.append("\n").append(tail);

        return text.toString();
    }

    void translate(String word) {

    }
}