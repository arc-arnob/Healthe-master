import React, {createRef ,Component} from 'react'
import L from 'leaflet'
import base_url from "./api/bootapi";
import './App.css'
import 'leaflet/dist/leaflet.css'
import 'leaflet-geosearch/dist/geosearch.css'
import { Map, Marker, Popup, TileLayer, MapControl, withLeaflet } from 'react-leaflet'
import { GeoSearchControl, OpenStreetMapProvider } from 'leaflet-geosearch';
import LocationInfo from './components/LocationInfo'
import MapComponent from'./components/MapComponent'
import axios from 'axios'
import { ToastContainer,toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';



export default class App extends Component {
  


    render() {
      
      return(
        <div className="rowC">
          <MapComponent />
          
        </div>
      )

        
    }
}
