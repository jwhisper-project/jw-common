package io.github.artshp.jwhisper.common.protocol;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

/**
 * Interface for network messages. All message types should implement it.
 * <p>
 * <b>Note</b>: when adding new message type, do not forget to add it to {@code JsonSubTypes} list
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RegisterRequest.class, name = "REGISTER"),
        @JsonSubTypes.Type(value = LoginRequest.class, name = "LOGIN"),
        @JsonSubTypes.Type(value = LogoutRequest.class, name = "LOGOUT"),
        @JsonSubTypes.Type(value = UserPublicKeyRequest.class, name = "USER_PUBLIC_KEY_REQUEST"),
        @JsonSubTypes.Type(value = UserPublicKeyResponse.class, name = "USER_PUBLIC_KEY_RESPONSE"),
        @JsonSubTypes.Type(value = EncryptedMessage.class, name = "MESSAGE"),
        @JsonSubTypes.Type(value = StatusResponse.class, name = "STATUS"),
})
public sealed interface WhisperMessage extends Serializable permits
        RegisterRequest, LoginRequest, LogoutRequest,
        UserPublicKeyRequest, UserPublicKeyResponse, EncryptedMessage,
        StatusResponse {
}
