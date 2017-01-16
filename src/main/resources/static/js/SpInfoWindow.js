var infoWindow;
function getInfoWindow(htmlmarker, position) {
	if (infoWindow == null) {
		infoWindow = new google.maps.InfoWindow();
	}
	var text = '<h4>'+htmlmarker.title+'</h4>'
	+ '<span class="label label-primary">' + htmlmarker.subtitle + '</span>'
	+ '<form action="/deleteSight" method="post" style="margin-top: 20px">'
	+ '<p><button id="btnDelete" type="button" class="btn btn-danger" onclick="doDelete('+htmlmarker.id+');"><span class="glyphicon glyphicon-minus" aria-hidden="true"></span> Delete</button></p>'
	+ '</form>'
	infoWindow.setOptions({content: text, position: position, pixelOffset: new google.maps.Size(0, -30)});
	return infoWindow;
}

function doDelete(markerId){
	var formData = new FormData();
    formData.append('id', markerId);
    
	$.ajax({
	    url: "/api/v1/sightIcon/" + markerId,
	    type: 'DELETE',
	    xhr: function() {  // Custom XMLHttpRequest
	      var myXhr = $.ajaxSettings.xhr();
	      return myXhr;
	    },
	    // beforeSend: beforeSendHandler,
	    success: function(data) {
	    	showMarkers();
	    },
	    data: formData,
        cache: false,
        contentType: false,
        processData: false
	  });
}