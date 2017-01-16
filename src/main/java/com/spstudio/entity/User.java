package com.spstudio.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="login_id")
	private String loginId;

	private String nickname;

	//bi-directional many-to-one association to Marker
	@OneToMany(mappedBy="user")
	private List<Marker> markers;

	public User() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLoginId() {
		return this.loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public List<Marker> getMarkers() {
		return this.markers;
	}

	public void setMarkers(List<Marker> markers) {
		this.markers = markers;
	}

	public Marker addMarker(Marker marker) {
		getMarkers().add(marker);
		marker.setUser(this);

		return marker;
	}

	public Marker removeMarker(Marker marker) {
		getMarkers().remove(marker);
		marker.setUser(null);

		return marker;
	}

}