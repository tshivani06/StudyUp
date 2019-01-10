
window.onload = pageLoad

function pageLoad() {
	initMap()
}

function initMap() {
	var map = new ol.Map({
		target: 'map',
		layers: [
			new ol.layer.Tile({
				source: new ol.source.OSM()
			})
		],
		view: new ol.View({
			center: ol.proj.fromLonLat([37.41, 8.82]),
			zoom: 4
		})
	});
	navigator.geolocation.getCurrentPosition(function(pos) {
		const coords = ol.proj.fromLonLat([pos.coords.longitude, pos.coords.latitude]);
		map.getView().animate({center: coords, zoom: 13});
	});
}