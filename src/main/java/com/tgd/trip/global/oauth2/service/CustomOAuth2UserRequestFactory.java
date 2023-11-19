//package com.tgd.trip.global.oauth2.service;
//
//import org.springframework.security.oauth2.client.registration.ClientRegistration;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
//import org.springframework.security.oauth2.core.OAuth2AccessToken;
//import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
//import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CustomOAuth2UserRequestFactory implements OAuth2UserRequestFactory {
//
//    private final ClientRegistrationRepository clientRegistrationRepository;
//    private final OAuth2UserRequestRepository userRequestRepository;
//
//    public CustomOAuth2UserRequestFactory(ClientRegistrationRepository clientRegistrationRepository,
//                                          OAuth2UserRequestRepository userRequestRepository) {
//        this.clientRegistrationRepository = clientRegistrationRepository;
//        this.userRequestRepository = userRequestRepository;
//    }
//
//    @Override
//    public OAuth2UserRequest createOAuth2UserRequest(ClientRegistration clientRegistration, OAuth2AccessToken accessToken) {
//        AuthenticationMethod authenticationMethod = AuthenticationMethod.CLIENT_SECRET_BASIC;
//        if (clientRegistration.getClientAuthenticationMethod() == ClientAuthenticationMethod.NONE) {
//            authenticationMethod = AuthenticationMethod.NONE;
//        }
//
//        OAuth2UserRequest.Builder builder = OAuth2UserRequest.withClientRegistration(clientRegistration)
//                .accessToken(accessToken)
//                .authenticationMethod(authenticationMethod);
//
//        OAuth2UserRequest userRequest = builder.build();
//        this.userRequestRepository.saveUserRequest(userRequest, null);
//
//        return userRequest;
//    }
//
//    @Override
//    public OAuth2UserRequest createOAuth2UserRequest(String registrationId, OAuth2AccessToken accessToken) {
//        ClientRegistration clientRegistration = this.clientRegistrationRepository.findByRegistrationId(registrationId);
//        if (clientRegistration == null) {
//            throw new IllegalArgumentException("Invalid Client Registration with Id: " + registrationId);
//        }
//
//        return createOAuth2UserRequest(clientRegistration, accessToken);
//    }
//
//    @Override
//    public OAuth2UserRequest createOAuth2UserRequest(OAuth2AuthorizationRequest authorizationRequest) {
//        String registrationId = authorizationRequest.getAttribute(OAuth2ParameterNames.REGISTRATION_ID);
//        if (registrationId == null) {
//            throw new IllegalArgumentException("Missing Client Registration Id");
//        }
//
//        ClientRegistration clientRegistration = this.clientRegistrationRepository.findByRegistrationId(registrationId);
//        if (clientRegistration == null) {
//            throw new IllegalArgumentException("Invalid Client Registration with Id: " + registrationId);
//        }
//
//        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
//                "your_access_token_here", null, null);
//        return createOAuth2UserRequest(clientRegistration, accessToken);
//    }
//}
