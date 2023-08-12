// This file is auto-generated, don't edit it. Thanks.
package com.lingfly.furigana_latex;

import com.alibaba.fastjson.JSON;
import com.aliyun.alimt20181012.models.TranslateGeneralRequest;
import com.aliyun.alimt20181012.models.TranslateGeneralResponse;
import com.aliyun.teaopenapi.models.Config;


public class Sample {

    public static com.aliyun.alimt20181012.Client createClient(String accessKeyId, String accessKeySecret, String regionId) throws Exception {
        Config config = new Config();
        config.accessKeyId = accessKeyId;
        config.accessKeySecret = accessKeySecret;
        config.endpoint = "mt.cn-hangzhou.aliyuncs.com";
        return new com.aliyun.alimt20181012.Client(config);
    }

    public static void main(String[] args_) throws Exception {
        java.util.List<String> args = java.util.Arrays.asList(args_);
        com.aliyun.alimt20181012.Client client = Sample.createClient("LTAI5tKnDhnPDzLZ8zYy3B5y", "LZEK6Gjl8zJXGN6CGkOggDflfXTUuI", null);
        TranslateGeneralRequest request = new TranslateGeneralRequest()
                .setFormatType("text")
                .setSourceLanguage("zh")
                .setTargetLanguage("en")
                .setSourceText("汉语");
        TranslateGeneralResponse response = client.translateGeneral(request);
        System.out.println(JSON.toJSONString(response));
    }
}
