import React from 'react'
import { MapContainer, Marker, Popup, TileLayer } from 'react-leaflet'
import { IconLocation } from './IconLocation'
import 'leaflet/dist/leaflet.css'
import { useState, useEffect } from 'react'


const MapView = ({products}) => {
    const [location, setLocation] = useState([]);

    const cityLocations = {
        "Buenos Aires": [-34.603722, -58.381592],
        "Cordoba": [-31.420083, -64.188776],
        "Rosario": [-32.942147, -60.639667],
        "Mendoza": [-32.889458, -68.845838],
        "San Juan": [-31.5375, -68.53639],
        "San Luis": [-33.29501, -66.33563],
        "La Rioja": [-29.41105, -66.85067]
    }

    const getLocation = (cityName) => {
        const location = cityLocations[cityName];
        return location || [];
    }

    useEffect(() => {
        if(products?.city?.name){
            const cityLocation = getLocation(products.city.name);
            setLocation(cityLocation);
        }
    },[products])
    return location.length > 0 ? (
        <MapContainer center={location} zoom={13} scrollWheelZoom={false}>
            <TileLayer
                attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            />
            <Marker position={location} icon={IconLocation}>
                <Popup>
                Tu ubicación <br /> 
                </Popup>
            </Marker>
        </MapContainer>
    ) : null;
}

export default MapView