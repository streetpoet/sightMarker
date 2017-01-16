<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>Travel Marker</title>

		<link rel="stylesheet" href="css/bootstrap.min.css">
		<link rel="stylesheet" href="css/animated.min.css">
		<script src="js/jquery-3.1.0.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<script type="text/javascript" src="js/SpOverlay.js"></script>
		<script type="text/javascript" src="js/SpAutoCompleteInput.js"></script>
		<script type="text/javascript" src="js/SpInfoWindow.js"></script>

		<style type="text/css">
			html { height: 100% }
			body { padding-top: 60px; padding-bottom: 10px; }
			#map { height: 100%; }
			.form-control { width: 500px; margin-top: 50px; margin-right: 10px;font-family: Roboto;}
			
			.div_animation{
		        height:80px; 
		        width:80px;
		        border-radius: 40px; 
		        background: rgba(255, 0, 0, 0.9);
		
		        transform: scale(1);
		        animation: myfirst 1.8s;      
		        animation-iteration-count: infinite;
		    }
		
		    @keyframes myfirst{
		        to{
		            transform: scale(1.8);
		            background: rgba(0, 0, 0, 0);
		        }
		    }
		</style>
	</head>
	<body style="height: 100%">
		<nav class="navbar navbar-default navbar-inverse navbar-fixed-top">
			<div class="container-fluid">
				<!-- Brand and toggle get grouped for better mobile display -->
			    <div class="navbar-header">
			      <a class="navbar-brand" href="#"><span class="glyphicon glyphicon-fire" aria-hidden="true"></span></a>
			      <p class="navbar-text">An travel marker system, created by S.P.</p>
			    </div>
			</div>
		</nav>

		<!-- Map Component-->

		<div class="container-fluid" style="height: 100%">
			<div class="row" style="height: 100%">
				<div class="col-xs-12" style="height: 100%">
			  		<div id="map"></div>
				</div>
			</div>
		</div>

		<!-- Operation Panel -->

		<div style="position:absolute; right:25px; top:70px">
				    <button id="btnAddPlace" class="btn btn-primary"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> ADD PLACE</button>
				    <button id="btnL3" class="btn btn-info">L3</button>
				    <button id="btnL5" class="btn btn-info">L5(Landmass)</button>
				    <button id="btnL10" class="btn btn-info">L10(City)</button>
				    <button id="btnL15" class="btn btn-info">L15(Streets)</button>
				    <button id="btnL20" class="btn btn-info">L20(Buildings)</button>
			    	<input id="inputSearchPlace" type="text" class="form-control" placeholder="Input Search Place...">
		</div>

		<script type="text/javascript">
			var map;
			var sightOverlay;
			var markers = [];
			var overLayList = [];

			function initial() {
				map = initMap();
				map.addListener('idle', showMarkers);
				sightOverlay = initSpOverlay();
				initAutocomplete($('#inputSearchPlace')[0], map);
			}
			

			function showMarkers() {
				/* if (overLayList != null){
					overLayList.forEach(function(d){d.setMap(null);});
				} */
				var bounds = map.getBounds();
				$.ajax({
					url : "/sightIcon",
					type : 'POST',
					contentType : 'application/json',
					dataType : 'json',
					xhr : function() { // Custom XMLHttpRequest
						var myXhr = $.ajaxSettings.xhr();
						return myXhr;
					},
					// beforeSend: beforeSendHandler,
					success : function(data) {
					/* 	if (overLayList != null) {
							overLayList.forEach(function(d) {
								console.log(d.id);
							});
						} */
						var tempMap = [];
						data.forEach(function(d) { // d is a 'htmlMarker' object
							tempMap[String(d.id)] = d.id;
							if (!(String(d.id) in overLayList)){
								overLayList[String(d.id)] = addCustomOverLay(d);
							}
						});
						for (var x in overLayList){
							if (!(x in tempMap)){
								console.log(x + " should be removed.");
								overLayList[x].setMap(null);
								delete overLayList[x];
							}
						}
						console.log(overLayList.length);
					},
					data : JSON.stringify(bounds.toJSON()),
					cache : false
				});
			}

			function initMap() {
				return new google.maps.Map(document.getElementById('map'), {
					center : {
						lat : 20,
						lng : 0
					},
					zoom : 3,
					mapTypeId : google.maps.MapTypeId.ROADMAP,
					scaleControl : true
				});
			}

			function addCustomOverLay(htmlMarker) {
				var overlay = new sightOverlay(new google.maps.LatLng(
						htmlMarker.lat, htmlMarker.lng), htmlMarker.url, map,
						htmlMarker.visitType);
				google.maps.event.addListener(overlay, 'click',
						function(event) {
							getInfoWindow(htmlMarker, {
								lat : htmlMarker.lat,
								lng : htmlMarker.lng
							}).open(map);
						});
				overlay.show();
				return overlay;
			}

			function clearTargets() {
				markers.forEach(function(marker) {
					marker.setMap(null);
				});
			}

			function submitUploadForm(object) {
				var form = $(object).parent();
				var file = form.find('#uploadfile').get(0).files[0];
				var visitType = form.find('#visit_type').prop('checked') ? 1
						: 0;
				var zoomLevel = form.find('#zoom_level').val();
				var position = form.find('#position').val();
				var title = form.find("#title").val();
				var subtitle = form.find('#subtitle').val();

				var formData = new FormData();
				formData.append('uploadfile', file);
				formData.append('visitType', visitType);
				formData.append('zoomLevel', zoomLevel);
				formData.append('position', position);
				formData.append('title', title);
				formData.append('subtitle', subtitle);

				$.ajax({
					url : "/iconUpload",
					type : 'POST',
					xhr : function() { // Custom XMLHttpRequest
						var myXhr = $.ajaxSettings.xhr();
						return myXhr;
					},
					// beforeSend: beforeSendHandler,
					success : function(data) {
						$('#inputSearchPlace').hide();
						clearTargets();
						showMarkers();
					},
					// Form data
					data : formData,
					//Options to tell jQuery not to process data or worry about content-type.
					cache : false,
					contentType : false,
					processData : false
				});
			}
		</script>

    	<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBFn-4Mc9B2EOciU-SGS-_k_8sWi_r5v9E&libraries=places&callback=initial" async defer></script>
        
    	<script type="text/javascript">
    		$(document).ready(function(){
    			$('#inputSearchPlace').hide();
    			$("#btnAddPlace").on("click", function(){
    				$('#inputSearchPlace').val('');
					$('#inputSearchPlace').show();
					$('#inputSearchPlace').focus();
    				});
    			
    			$('#btnL3').on('click', function(){map.setZoom(3);});
    			$('#btnL5').on('click', function(){map.setZoom(5);});
    			$('#btnL10').on('click', function(){map.setZoom(9);});
    			$('#btnL15').on('click', function(){map.setZoom(12);});
    			$('#btnL20').on('click', function(){map.setZoom(20);});
    		});
    	</script>

	</body>
</html>