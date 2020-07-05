package com.github.instagram4j.Instagram4J.requests.live;

import com.github.instagram4j.Instagram4J.models.IGPayload;
import com.github.instagram4j.Instagram4J.requests.IGPostRequest;
import com.github.instagram4j.Instagram4J.responses.IGResponse;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IGLiveCommentRequest extends IGPostRequest<IGResponse> {
    @NonNull
    private String broadcast_id, _message;
    
    @Override
    protected IGPayload getPayload() {
        return new IGLiveCommentPayload();
    }
    
    @Override
    public String path() {
        return "live/" + broadcast_id + "/comment/";
    }

    @Override
    public Class<IGResponse> getResponseType() {
        return IGResponse.class;
    }
    
    @Data
    public class IGLiveCommentPayload extends IGPayload {
        private String comment_text = _message;
    }
    
}
