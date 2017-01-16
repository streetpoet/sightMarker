package com.spstudio.rest;

import com.spstudio.entity.Marker;
import com.spstudio.entity.ProductVersion;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

/**
 * For test purpose
 */
@RestController
public class DevService {

    @RequestMapping(path = "/version", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE}, headers = {"apiVersion=v1"})
    public ProductVersion versionService(HttpEntity<Map> entity, @RequestParam Map<String, String> requestParams) {
        HttpHeaders headers = entity.getHeaders();
        System.out.println("server: header: developer -> " + headers.getFirst("developer"));
        System.out.println("server: map: developer -> " + requestParams.get("developer"));
        System.out.println("server: map: timestamp -> " + requestParams.get("timestamp"));
        return new ProductVersion(1, 2);
    }

    @RequestMapping(path = "/marker/{markerId}", method = RequestMethod.DELETE, headers = {"apiVersion=v1"})
    public ResponseEntity<Marker> deleteMarker(@PathVariable("markerId") int markId) {
        return new ResponseEntity<Marker>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(path = "/marker/{markerId}", method = RequestMethod.DELETE, headers = {"apiVersion=v2"})
    public ResponseEntity<Marker> deleteMarkerV2(@PathVariable("markerId") int markId) {
        Marker marker = new Marker();
        marker.setId(markId);
        return new ResponseEntity<Marker>(marker, HttpStatus.OK);
    }

    @RequestMapping(path = "/marker", method = RequestMethod.POST)
    public ResponseEntity<Void> createMarker(UriComponentsBuilder builder){
        long random = System.currentTimeMillis() / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/marker/{markerId}").buildAndExpand(random).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
}
