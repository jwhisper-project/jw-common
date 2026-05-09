package io.github.artshp.jwhisper.common.protocol;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RegisterRequest.class, name = "REGISTER"),
        @JsonSubTypes.Type(value = UnregisterRequest.class, name = "UNREGISTER"),
        @JsonSubTypes.Type(value = UserPublicKeyRequest.class, name = "USER_PUBLIC_KEY_REQUEST"),
        @JsonSubTypes.Type(value = StatusResponse.class, name = "STATUS"),
})
public sealed interface WhisperMessage extends Serializable
        permits RegisterRequest, UnregisterRequest, UserPublicKeyRequest, StatusResponse {
}
