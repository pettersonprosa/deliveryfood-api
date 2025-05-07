package com.deliveryfood.core.security.authorizationserver;

import java.util.List;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

public interface OAuth2AuthorizationQueryService {

    List<RegisteredClient> listClientsWithConsent(String principalName);
}
