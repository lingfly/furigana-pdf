package com.lingfly.moji;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.HttpUtil;
import com.lingfly.markji.MarkJiMain;

import java.io.IOException;
import java.util.*;

public class MojiMain {
    final static int PAGE_COUNT = 50;
    final static int PAGE_INDEX = 1;
    final static int PAGE_MAX = 1;

    public static void main(String[] args) throws IOException {
        MojiMain main = new MojiMain();
        List<String> idList = main.getFavorites();
        List<MojiData> wordDetail = main.getWordDetail(idList);
        MarkJiMain markJiMain = new MarkJiMain();
        markJiMain.getSpell(wordDetail);
        System.out.println(JSON.toJSONString(wordDetail));
        markJiMain.generateCard(wordDetail);
    }

    public List<String> getFavorites() throws IOException {
        Map<String, Object> req = new HashMap<>();
        req.put("fid", "GxwDzhduWi");
        req.put("count", PAGE_COUNT);
        req.put("sortType", 5);
        req.put("pageIndex", PAGE_INDEX);
        req.put("_SessionToken", "r:7ef646d592f88b90c11a12075e95e157");
        req.put("_ClientVersion", "js3.4.1");
        req.put("_ApplicationId", "E62VyFVLMiW7kvbtVq3p");
        req.put("g_os", "PCWeb");
        req.put("g_ver", "v4.6.12.20231012");
        req.put("_InstallationId", "64e2737c-c972-43bf-8d89-2d9f3559c824");


        Integer i = 1, totalPage = 1;
        List<String> list = new ArrayList<>();
        do {
            req.put("pageIndex", i++);
            JSONObject resp = HttpUtil.post("https://api.mojidict.com/parse/functions/folder-fetchContentWithRelatives", req, JSONObject.class, null);
            totalPage = resp.getJSONObject("result").getInteger("totalPage");

            JSONArray result = resp.getJSONObject("result").getJSONArray("result");
            for (JSONObject object : result.toJavaList(JSONObject.class)) {
                list.add(object.getString("targetId"));
            }
        } while (i <= totalPage && i <= PAGE_MAX);
        System.out.println("total page: " + totalPage);
        System.out.println("total word: " + list.size());
        return list;
    }

    public List<MojiData> getWordDetail(List<String> idList) throws IOException {
        MojiWordReq req = new MojiWordReq();
        MojiWordReq.ObjectId objectId = new MojiWordReq.ObjectId();
        req.setItemsJson(Collections.singletonList(objectId));
        req.set_ApplicationId("E62VyFVLMiW7kvbtVq3p");
        req.set_SessionToken("r:7ef646d592f88b90c11a12075e95e157");

        List<MojiData> dataList = new ArrayList<>();
        try {

            for (int i = 0; i < idList.size(); i++) {
                if (i % 10 == 0) {
                    System.out.println("query word: " + i);
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                objectId.objectId = idList.get(i);
                JSONObject resp = HttpUtil.post("https://api.mojidict.com/parse/functions/nlt-fetchManyLatestWords", req, JSONObject.class, null);
                MojiWord word = resp.getJSONObject("result").getJSONArray("result").getObject(0, MojiWord.class);

                dataList.add(new MojiData(word));
            }
        } finally {
            System.out.println("moji_word:" + JSON.toJSONString(dataList));
        }

        return dataList;
    }

}
