package com.spstudio.rest;

import com.spstudio.entity.Bound;
import com.spstudio.entity.HtmlMarker;
import com.spstudio.entity.Marker;
import com.spstudio.entity.User;
import com.spstudio.service.SightPointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class SightPointController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    SightPointService sightPointService;

    @RequestMapping(value = "/sightIcon", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<HtmlMarker>> sightQuery(@RequestParam double east, @RequestParam double north, @RequestParam double west, @RequestParam double south) {
        log.info("=> prepare to query markers");
        Bound bound = new Bound(south, west, north, east);
        List<HtmlMarker> listMarker = sightPointService.queryMarker(bound);
        if (listMarker == null || listMarker.size() == 0){
            return new ResponseEntity<List<HtmlMarker>>(new ArrayList<>(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<HtmlMarker>>(listMarker, HttpStatus.OK);
    }

    @RequestMapping(value = "/sightIcon/{markerId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteSightIcon(@PathVariable("markerId") int markerId) {
        log.info("=> prepare to delete marker: " + markerId);
        sightPointService.deleteMarker(markerId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/sightIcon", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Marker> createSightIcon(
            @RequestParam("uploadfile") MultipartFile file,
            @RequestParam("visitType") String visitType,
            @RequestParam("zoomLevel") String zoomLevel,
            @RequestParam("position") String position,
            @RequestParam("title") String title,
            @RequestParam("subtitle") String subtitle,
            UriComponentsBuilder builder) {
        log.info("=> prepare to create marker");
        File iconFile = sightPointService.saveIconFile(file);
        if (iconFile == null){
            return new ResponseEntity<Marker>(HttpStatus.BAD_REQUEST);
        }

        // save marker to rDBMS
        Marker marker = new Marker();
        User u = new User();
        u.setId(1);
        marker.setUser(u);
        marker.setVisitType(visitType == null || "".equals(visitType) ? 0 : Integer.valueOf(visitType));
        marker.setZoomLevel(zoomLevel == null || "".equals(zoomLevel) ? 3 : Integer.valueOf(zoomLevel));
        marker.setIconFilename(iconFile.getName());
        marker.setLat(Double.parseDouble(position.split(",")[0]));
        marker.setLng(Double.parseDouble(position.split(",")[1]));
        marker.setTitle(title);
        marker.setSubtitle(subtitle);
        Marker savedMarker = sightPointService.createMarker(marker);

        savedMarker.setIconFilename(sightPointService.convertIconUrl(savedMarker.getIconFilename()));

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/sightIcon/{markerId}").buildAndExpand(savedMarker.getId()).toUri());
        return new ResponseEntity<Marker>(savedMarker, headers, HttpStatus.CREATED);
    }

}