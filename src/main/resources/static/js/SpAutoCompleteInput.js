function initAutocomplete(input, map) {
	
	var searchBox = new google.maps.places.SearchBox(input);
	map.controls[google.maps.ControlPosition.RIGHT_TOP].push(input);

	map.addListener('bounds_changed', function() {
		searchBox.setBounds(map.getBounds());
	});

	searchBox
			.addListener(
					'places_changed',
					function() {
						var places = searchBox.getPlaces();
						if (places.length == 0) {
							return;
						}
						$(input).hide();
						clearTargets();
						markers = [];

						var bounds = new google.maps.LatLngBounds();
						places
								.forEach(function(place) {
									var icon = {
										url : 'img/target1.png',
										size : new google.maps.Size(80, 80),
										origin : new google.maps.Point(0, 0),
										anchor : new google.maps.Point(40, 40),
										scaledSize : new google.maps.Size(80,
												80)
									};

									var target = new google.maps.Marker({
										map : map,
										icon : icon,
										title : place.name,
										animation : google.maps.Animation.DROP,
										position : place.geometry.location
									});
									target
											.addListener(
													'click',
													function() {

														var contentString = '<div id="content">'
																+ '<div id="siteNotice">'
																+ '</div>'
																+ '<h1 id="firstHeading" class="firstHeading">'
																+ this.title
																+ '</h1>'
																+ '<div id="bodyContent">'
																+ '<p class="text-right"><span class="label label-primary">GPS: '
																+ this.position.lat().toFixed(6) + ', ' + this.position.lng().toFixed(6)
																+ '</span></p>'
																+ '<form action="/sightIcon" enctype="multipart/form-data" method="post">'
																+ '<p><label for="subtitle">Sub Title</label><input type="text" id="subtitle" name="subtitle" class="form-control" style="margin-top:0px;width:400px;" /></p>'
																+ '<p><label for="uploadfile">Sight Photo</label><input id="uploadfile" name="uploadfile" type="file" /></p>'
																+ '<p><label><input id="visit_type" type="checkbox" value="1"> Visited</label>&nbsp;&nbsp;&nbsp; Zoom Level: <input id="zoom_level" type="number" placeholder="Zoom Level(1-20)"> (default: 3)</p>'
																+ '<button id="btnUpload" type="button" class="btn btn-success" onclick="javascript:submitUploadForm(this);"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Add To My Favour</button>'
																+ '<input id="title" type="hidden" name="title" value="' + this.title +'" />'
																+ '<input id="position" type="hidden" name="position" value="'
																+ this.position.lat()
																+ ','
																+ this.position.lng()
																+ '"></input></form>'
																+ '</div>'
																+ '</div>';

														var infowindow = new google.maps.InfoWindow(
																{
																	content : contentString
																});

														infowindow.open(map, this);

													});

									markers.push(target);

									if (place.geometry.viewport) {
										bounds.union(place.geometry.viewport);
									} else {
										bounds.extend(place.geometry.location);
									}
								});
						map.fitBounds(bounds);
					});
}