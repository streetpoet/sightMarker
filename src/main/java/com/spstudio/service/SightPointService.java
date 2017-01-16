package com.spstudio.service;

import com.spstudio.entity.Bound;
import com.spstudio.entity.HtmlMarker;
import com.spstudio.entity.Marker;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * Created by sp on 15/01/2017.
 */
@Service
public interface SightPointService {

    public Marker createMarker(Marker marker);
    public boolean deleteMarker(int markerId);
    public boolean updateMarker(Marker marker);
    public List<HtmlMarker> queryMarker(Bound bound);
    public File saveIconFile(MultipartFile file);
    public String convertIconUrl(String iconFileName);
}
