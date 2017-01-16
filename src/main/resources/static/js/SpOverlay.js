function initSpOverlay(){
	SPOverlay.prototype = new google.maps.OverlayView();
    
	function SPOverlay(latlng, image, map, visit) {
        
          this.latlng_ = latlng;
          this.image_ = image;
          this.map_ = map;
          this.visit_ = visit;

          this.div_ = null;
          this.setMap(map);
        }

        SPOverlay.prototype.onAdd = function() {
            var div = document.createElement('div');
            div.style.borderStyle = 'none';
            div.style.borderWidth = '0px';
            div.style.position = 'absolute';
//            div.className = 'div_animation';
//            div.style.backgroundColor = '#ED4891';

            var img = document.createElement('img');
            img.src = this.image_;
            img.style.width = '100%';
            img.style.height = '100%';
            img.className = 'img-circle';
            img.style.position = 'absolute';
		    if (this.visit_ == 0) {
		    	img.style.backgroundColor = '#ED4891';
			} else if (this.visit_ == 1) {
				img.style.backgroundColor = '#336600';
			} else {
				img.style.backgroundColor = '#000000';
			}
            
            img.style.padding = '4px';
            div.appendChild(img);

            this.div_ = div;
            var self = this;
            google.maps.event.addDomListener(this.div_, 'click', function(event) {
                event.stopPropagation();
                google.maps.event.trigger(self, 'click', event);
            });

            this.getPanes().overlayMouseTarget.appendChild(div);
        };

        SPOverlay.prototype.draw = function() {
            var width = 60;
            var overlayProjection = this.getProjection();
            var divPosition = overlayProjection.fromLatLngToDivPixel(this.latlng_);

            var div = this.div_;
            div.style.left = (divPosition.x - width / 2) + 'px';
            div.style.top = (divPosition.y - width / 2) + 'px';
            div.style.width = width + 'px';
            div.style.height = width + 'px';
        };

        SPOverlay.prototype.show = function() {
            if (this.div_) {
                this.div_.style.visibility = 'visible';
            }
        };
        
        SPOverlay.prototype.onRemove = function() {
        	  this.div_.parentNode.removeChild(this.div_);
        	  this.div_ = null;
        };

        
        return SPOverlay;
}