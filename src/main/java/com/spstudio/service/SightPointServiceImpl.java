package com.spstudio.service;

import com.spstudio.entity.Bound;
import com.spstudio.entity.HtmlMarker;
import com.spstudio.entity.Marker;
import com.spstudio.repository.TravelMarkerRepository;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sp on 15/01/2017.
 */
public class SightPointServiceImpl implements SightPointService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${url.base.sightIcon}")
    private String HTTP_BASE;

    @Value("${location.fs}")
    private String LOCAL_FS;

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    TravelMarkerRepository repository;

    @Override
    public Marker createMarker(Marker marker) {
        return repository.save(marker);
    }

    @Override
    public boolean deleteMarker(int markerId) {
        return false;
    }

    @Override
    public boolean updateMarker(Marker marker) {
        return false;
    }

    @Override
    public List<HtmlMarker> queryMarker(Bound bound) {

        double min = bound.west < bound.east ? bound.west : bound.east;
        double max = bound.west < bound.east ? bound.east : bound.west;

        List<HtmlMarker> listMarker = new ArrayList<>();
        jdbc.query(
                "SELECT visit_type, zoom_level, lat, lng, icon_filename, title, subtitle, id FROM markers where user_id = ? and lat between ? and ? and lng between ? and ?",
                new Object[]{1, bound.south, bound.north, min, max},
                new ResultSetExtractor<Void>() {

                    @Override
                    public Void extractData(ResultSet rs)
                            throws SQLException, DataAccessException {
                        while (rs.next()) {
                            HtmlMarker m = new HtmlMarker();
                            m.setLat(rs.getDouble("lat"));
                            m.setLng(rs.getDouble("lng"));
                            m.setUrl(HTTP_BASE + rs.getString("icon_filename"));
                            m.setVisitType(rs.getInt("visit_type"));
                            m.setZoomLevel(rs.getInt("zoom_level"));
                            m.setTitle(rs.getString("title"));
                            m.setSubtitle(rs.getString("subtitle"));
                            m.setId(rs.getInt("id"));
                            listMarker.add(m);
                        }
                        return null;
                    }

                });
        return listMarker;
    }

    @Override
    public File saveIconFile(MultipartFile file) {

        String fileType = file.getContentType();
        if ("image/jpeg".equals(fileType) || "image/jpg".equals(fileType)){
            fileType = ".jpg";
        }else {
            fileType = ".png";
        }
        String filename = System.currentTimeMillis() + fileType;
        File actualFile = null;
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                InputStream inputStream = new ByteArrayInputStream(bytes);
                actualFile = new File(LOCAL_FS + filename);
                Thumbnails.of(inputStream).forceSize(160, 160).outputFormat("JPEG").toFile(actualFile);
                inputStream.close();
            } catch (Exception e) {
                log.error("You failed to upload " + filename + " => " + e.getMessage());
                return null;
            }
        } else {
            log.error("You failed to upload " + filename + " because the file was empty.");
            return null;
        }

        return actualFile;
    }

    @Override
    public String convertIconUrl(String iconFileName) {
        return HTTP_BASE + iconFileName;
    }
}
