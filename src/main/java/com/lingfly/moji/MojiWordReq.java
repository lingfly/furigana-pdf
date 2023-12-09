package com.lingfly.moji;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class MojiWordReq {
    private List<ObjectId> itemsJson;
    private Boolean skipAccessories = Boolean.FALSE;
    private String _ApplicationId;
    private String _SessionToken;

    @Getter
    public static class ObjectId {
        String objectId;
    }
}
