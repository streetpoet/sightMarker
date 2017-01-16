package com.spstudio.entity;

public class HtmlMarker {
  private int id;
  private double lat;
  private double lng;
  private String url;
  private int visitType;
  private int zoomLevel;
  private String title;
  private String subtitle;
  
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public double getLat() {
    return lat;
  }
  public void setLat(double lat) {
    this.lat = lat;
  }
  public double getLng() {
    return lng;
  }
  public void setLng(double lng) {
    this.lng = lng;
  }
  public String getUrl() {
    return url;
  }
  public void setUrl(String url) {
    this.url = url;
  }
  public int getVisitType() {
    return visitType;
  }
  public void setVisitType(int visitType) {
    this.visitType = visitType;
  }
  public int getZoomLevel() {
    return zoomLevel;
  }
  public void setZoomLevel(int zoomLevel) {
    this.zoomLevel = zoomLevel;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getSubtitle() {
    return subtitle;
  }
  public void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
  }
}
