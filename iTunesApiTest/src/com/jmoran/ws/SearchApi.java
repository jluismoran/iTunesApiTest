/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jmoran.ws;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.ApplicationException;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jmoran.dto.SongDto;
import com.jmoran.ws.client.ItunesApiClient;

/**
 *
 * @author Jhonathan Moran
 */
@Stateless
@ApplicationException (rollback=true)
@Path("/search")
public class SearchApi extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    public SearchApi() {
        
    }
	
	// BUSCAR CANCIONES EN ITUNES
	@GET
    @Path("/byArtist/{artist}")
    @Produces({"application/json"})
    public List<SongDto> searchByArtist(@PathParam("artist") String artist) {
		List<SongDto> listSong = new ArrayList<SongDto>();
		try {
			ItunesApiClient itunesApi = new ItunesApiClient();
			String jsonData = (String) itunesApi.findByArtist(String.class, artist);
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode= mapper.readValue(jsonData, JsonNode.class);
			ArrayNode listResults = (ArrayNode) jsonNode.get("results");
			for(JsonNode resultNode : listResults) {
				String songTitle = resultNode.get("trackName").textValue();
				String album = resultNode.get("collectionName").textValue();
				String year = resultNode.get("releaseDate").textValue();
				String songArtist = resultNode.get("artistName").textValue();
				String genre = resultNode.get("primaryGenreName").textValue();
				String price = resultNode.get("trackPrice").textValue();
				SongDto songDto = new SongDto();
				songDto.setSongTitle(songTitle);
				songDto.setAlbum(album);
				songDto.setYear(year.substring(0, 4));
				songDto.setArtist(songArtist);
				songDto.setGenre(genre);
				songDto.setPrice(price!=null?Double.parseDouble(price):0);
				listSong.add(songDto);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
        return listSong;
    }
}
