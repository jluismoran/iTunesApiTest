/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jmoran.ws.client;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

public class ItunesApiClient {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "https://itunes.apple.com";

    public ItunesApiClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("search");
    }

    // consuming the iTunes Api to search by artist
    public <T> T findByArtist(Class<T> responseType, String artist) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.queryParam("term", artist).request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void close() {
        client.close();
    }
    
}
