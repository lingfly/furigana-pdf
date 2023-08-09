package com.lingfly.furigana_latex;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FuriganaReq {
    private String id = "1234-1";
    private String jsonrpc = "2.0";
    private String method = "jlp.furiganaservice.furigana";
    private ParamReq params;

}
