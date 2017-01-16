package com.spstudio.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the markers database table.
 * 
 */
@Entity
@Table(name="markers")
@NamedQuery(name="Marker.findAll", query="SELECT m FROM Marker m")
public class Marker implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private double lat;

	private double lng;

	@Column(name="visit_type")
	private int visitType;

	@Column(name="zoom_level")
	private int zoomLevel;
	
	@Column(name="icon_filename")
	private String iconFilename;
	
	@Column(name="title")
	private String title;
	
	@Column(name="subtitle")
	private String subtitle;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Marker() {
	}

	public Marker(int id, double lat, double lng, int visitType, int zoomLevel, String iconFilename, String title, String subtitle, User user) {
		this.id = id;
		this.lat = lat;
		this.lng = lng;
		this.visitType = visitType;
		this.zoomLevel = zoomLevel;
		this.iconFilename = iconFilename;
		this.title = title;
		this.subtitle = subtitle;
		this.user = user;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getLat() {
		return this.lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return this.lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public int getVisitType() {
		return this.visitType;
	}

	public void setVisitType(int visitType) {
		this.visitType = visitType;
	}

	public int getZoomLevel() {
		return this.zoomLevel;
	}

	public void setZoomLevel(int zoomLevel) {
		this.zoomLevel = zoomLevel;
	}

	public String getIconFilename() {
    return iconFilename;
  }

  public void setIconFilename(String iconFilename) {
    this.iconFilename = iconFilename;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

  public String getSubtitle() {
    return subtitle;
  }

  public void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
  }

}