/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivans.smooch.belajar.service;

import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.smooch.client.ApiClient;
import io.smooch.client.ApiException;
import io.smooch.client.Configuration;
import io.smooch.client.api.AppApi;
import io.smooch.client.api.AppUserApi;
import io.smooch.client.api.ConversationApi;
import io.smooch.client.auth.ApiKeyAuth;
import io.smooch.client.model.AppCreate;
import io.smooch.client.model.AppResponse;
import io.smooch.client.model.AppUserPreCreate;
import io.smooch.client.model.AppUserResponse;
import io.smooch.client.model.GetMessagesResponse;
import io.smooch.client.model.JwtResponse;
import io.smooch.client.model.MessagePost;
import io.smooch.client.model.MessageResponse;
import java.io.UnsupportedEncodingException;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author ivans
 */
@Service
public class SmoochServiceApi {

    @Value("${smooch.key_id}")
    private String keyId;

    @Value("${smooch.key_secret}")
    private String keySecret;

    private ApiClient defaultClient;

//    idUser1 : 19b526749546455cab00fb0b663a9366
//    idUser2 : 007a86882b6e46af95de069d3c6579fa
    private Logger LOGGER = LoggerFactory.getLogger(SmoochServiceApi.class);

    @PostConstruct
    public void init() throws UnsupportedEncodingException {
        String jwtKey = Jwts.builder()
                .claim("scope", "app")
                .setHeaderParam(JwsHeader.KEY_ID, keyId)
                .signWith(SignatureAlgorithm.HS256, keySecret.getBytes("UTF-8"))
                .compact();

        this.defaultClient = Configuration.getDefaultApiClient();
        ApiKeyAuth jwt = (ApiKeyAuth) defaultClient.getAuthentication("jwt");
        jwt.setApiKey(jwtKey);
        jwt.setApiKeyPrefix("Bearer");
    }

    public AppResponse getApp() {
        AppApi apiInstance = new AppApi(this.defaultClient);
        String appId = "5baded6160716300218ed012"; // String | Identifies the app.
        try {
            AppResponse result = apiInstance.getApp(appId);
            System.out.println(result);
            return result;
        } catch (ApiException e) {
            System.err.println("Exception when calling AppApi#getApp");
            e.printStackTrace();
        }
        return null;
    }

    public AppUserResponse createUser(AppUserPreCreate body) {
        AppUserApi apiInstance = new AppUserApi(this.defaultClient);
        String appId = "5baded6160716300218ed012"; // String | Identifies the app.
        try {
            AppUserResponse result = apiInstance.preCreateAppUser(appId, body);
            System.out.println(result);
            return result;
        } catch (ApiException e) {
            System.err.println("Exception when calling AppUserApi#preCreateAppUser");
            e.printStackTrace();
        }
        return null;
    }

    public MessageResponse sendMessage() {
        ConversationApi apiInstance = new ConversationApi();
        String appId = "5baded6160716300218ed012"; // String | Identifies the app.
        String userId = "19b526749546455cab00fb0b663a9366"; // String | Identifies the user. Can be either the smoochId or the userId.
        MessagePost messagePostBody = new MessagePost(); // MessagePost | Body for a postMessage request. Additional arguments are necessary based on message type ([text](https://docs.smooch.io/rest#text-message), [image](https://docs.smooch.io/rest#image-message), [carousel](https://docs.smooch.io/rest#carousel-message), [list](https://docs.smooch.io/rest#list-message)) 
        messagePostBody.setType("text");
        messagePostBody.setText("Test 123");
        messagePostBody.setRole("appUser");
        try {
            MessageResponse result = apiInstance.postMessage(appId, userId, messagePostBody);
            System.out.println(result);
            return result;
        } catch (ApiException e) {
            System.err.println("Exception when calling ConversationApi#postMessage");
            e.printStackTrace();
        }
        return null;
    }

    public GetMessagesResponse getMessage() {
        ConversationApi apiInstance = new ConversationApi();
        String appId = "5baded6160716300218ed012"; // String | Identifies the app.
        String userId = "19b526749546455cab00fb0b663a9366"; // String | Identifies the user. Can be either the smoochId or the userId.
        String before = "1538488558"; // String | Timestamp of message. The API will return 100 messages before the specified timestamp (excluding any messages with the provided timestamp).
        String after = "1538376958"; // String | Timestamp of message. The API will return 100 messages after the specified timestamp (excluding any messages with the provided timestamp).
        try {
            GetMessagesResponse result = apiInstance.getMessages(appId, userId, null, null);
            System.out.println(result);
            return result;
        } catch (ApiException e) {
            System.err.println("Exception when calling ConversationApi#getMessages");
            e.printStackTrace();
        }
        return null;
    }
}
