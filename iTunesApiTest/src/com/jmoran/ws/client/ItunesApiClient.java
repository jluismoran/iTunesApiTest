/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jmoran.ws.client;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * Jersey REST client generated for REST resource:TbSecUsuarioFacadeREST
 * [pe.gob.bosques.tbsecusuario]<br>
 * USAGE:
 * <pre>
 *        UsuarioWsClient client = new UsuarioWsClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author bosques
 */
public class ItunesApiClient {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "https://itunes.apple.com";

    public ItunesApiClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("search");
    }

    public <T> T findByArtist(Class<T> responseType, String artist) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.queryParam("term", artist).request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void close() {
        client.close();
    }
    
}
