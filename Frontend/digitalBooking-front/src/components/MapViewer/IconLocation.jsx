import React from 'react'
import L from 'leaflet'

export const IconLocation = L.icon({
    iconUrl: require('../../assets/find.png'),
    iconRetinaUrl: require('../../assets/find.png'),
    iconAnchor: [2, 2],
    shadowUrl: null,
    shadowSize: null,
    shadowAnchor: null,
    iconSize: [35, 35],
    className: 'leaflet-venue-icon'
});

