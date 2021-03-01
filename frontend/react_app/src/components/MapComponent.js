import React, {createRef ,Component} from 'react'
import L from 'leaflet'
import base_url from "./../api/bootapi";
import 'leaflet/dist/leaflet.css'
import 'leaflet-geosearch/dist/geosearch.css'
import { Map, Marker, Popup, TileLayer, MapControl, withLeaflet } from 'react-leaflet'
import { GeoSearchControl, OpenStreetMapProvider } from 'leaflet-geosearch';
import axios from 'axios'
import { ToastContainer,toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import LocationInfo from './LocationInfo'

const myIcon = L.icon({
        iconUrl:'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABkAAAApCAYAAADAk4LOAAAFgUlEQVR4Aa1XA5BjWRTN2oW17d3YaZtr2962HUzbDNpjszW24mRt28p47v7zq/bXZtrp/lWnXr337j3nPCe85NcypgSFdugCpW5YoDAMRaIMqRi6aKq5E3YqDQO3qAwjVWrD8Ncq/RBpykd8oZUb/kaJutow8r1aP9II0WmLKLIsJyv1w/kqw9Ch2MYdB++12Onxee/QMwvf4/Dk/Lfp/i4nxTXtOoQ4pW5Aj7wpici1A9erdAN2OH64x8OSP9j3Ft3b7aWkTg/Fm91siTra0f9on5sQr9INejH6CUUUpavjFNq1B+Oadhxmnfa8RfEmN8VNAsQhPqF55xHkMzz3jSmChWU6f7/XZKNH+9+hBLOHYozuKQPxyMPUKkrX/K0uWnfFaJGS1QPRtZsOPtr3NsW0uyh6NNCOkU3Yz+bXbT3I8G3xE5EXLXtCXbbqwCO9zPQYPRTZ5vIDXD7U+w7rFDEoUUf7ibHIR4y6bLVPXrz8JVZEql13trxwue/uDivd3fkWRbS6/IA2bID4uk0UpF1N8qLlbBlXs4Ee7HLTfV1j54APvODnSfOWBqtKVvjgLKzF5YdEk5ewRkGlK0i33Eofffc7HT56jD7/6U+qH3Cx7SBLNntH5YIPvODnyfIXZYRVDPqgHtLs5ABHD3YzLuespb7t79FY34DjMwrVrcTuwlT55YMPvOBnRrJ4VXTdNnYug5ucHLBjEpt30701A3Ts+HEa73u6dT3FNWwflY86eMHPk+Yu+i6pzUpRrW7SNDg5JHR4KapmM5Wv2E8Tfcb1HoqqHMHU+uWDD7zg54mz5/2BSnizi9T1Dg4QQXLToGNCkb6tb1NU+QAlGr1++eADrzhn/u8Q2YZhQVlZ5+CAOtqfbhmaUCS1ezNFVm2imDbPmPng5wmz+gwh+oHDce0eUtQ6OGDIyR0uUhUsoO3vfDmmgOezH0mZN59x7MBi++WDL1g/eEiU3avlidO671bkLfwbw5XV2P8Pzo0ydy4t2/0eu33xYSOMOD8hTf4CrBtGMSoXfPLchX+J0ruSePw3LZeK0juPJbYzrhkH0io7B3k164hiGvawhOKMLkrQLyVpZg8rHFW7E2uHOL888IBPlNZ1FPzstSJM694fWr6RwpvcJK60+0HCILTBzZLFNdtAzJaohze60T8qBzyh5ZuOg5e7uwQppofEmf2++DYvmySqGBuKaicF1blQjhuHdvCIMvp8whTTfZzI7RldpwtSzL+F1+wkdZ2TBOW2gIF88PBTzD/gpeREAMEbxnJcaJHNHrpzji0gQCS6hdkEeYt9DF/2qPcEC8RM28Hwmr3sdNyht00byAut2k3gufWNtgtOEOFGUwcXWNDbdNbpgBGxEvKkOQsxivJx33iow0Vw5S6SVTrpVq11ysA2Rp7gTfPfktc6zhtXBBC+adRLshf6sG2RfHPZ5EAc4sVZ83yCN00Fk/4kggu40ZTvIEm5g24qtU4KjBrx/BTTH8ifVASAG7gKrnWxJDcU7x8X6Ecczhm3o6YicvsLXWfh3Ch1W0k8x0nXF+0fFxgt4phz8QvypiwCCFKMqXCnqXExjq10beH+UUA7+nG6mdG/Pu0f3LgFcGrl2s0kNNjpmoJ9o4B29CMO8dMT4Q5ox8uitF6fqsrJOr8qnwNbRzv6hSnG5wP+64C7h9lp30hKNtKdWjtdkbuPA19nJ7Tz3zR/ibgARbhb4AlhavcBebmTHcFl2fvYEnW0ox9xMxKBS8btJ+KiEbq9zA4RthQXDhPa0T9TEe69gWupwc6uBUphquXgf+/FrIjweHQS4/pduMe5ERUMHUd9xv8ZR98CxkS4F2n3EUrUZ10EYNw7BWm9x1GiPssi3GgiGRDKWRYZfXlON+dfNbM+GgIwYdwAAAAASUVORK5CYII',
        iconSize:[20,25],
        iconAnchor:[12.5,41],
        popupAnchor:[0,-41]
    }
)
class SearchMap extends MapControl {

    createLeafletElement() {
      return GeoSearchControl({
        provider: new OpenStreetMapProvider(),
        style: 'bar',
        showMarker: true,
        showPopup: false,
        autoClose: true,
        retainZoomLevel: true,
        animateZoom: true,
        keepResult: false,
        searchLabel: 'search'
      });
    }
  }
class MapComponent extends Component {
    constructor(props) {
        super(props);
        this.mapRef = createRef();
      }
    state = {
        hasLocation: false,
        markers: [[51.505,-0.09]], // This is default marker for line 118 and also marker directory
        current_marker:[], // stores latest marker
        position: "", //This is for marker, concat of "lat,lon"
        address: "", // This is recieved from backend after rev-geocoding
        storeName:"",
        location: {
          type:"Point", // for now it hardcoded
          coordinates:[]//current_marker will be stored for latest marker booking
        }
      }

      handleChange = (event) =>{
        console.log("Clicked! and enterd")
        this.setState({storeName: event.target.value});
        console.log(this.state.storeName)
    }

       // Handles when clicked on mapcomponent anywhere.
      handleClick = (e) => {
        //const map = this.mapRef.current
        const {markers} = this.state // gets state.makers from line 43
        console.log(e.latlng) // e.latlng returns current ltatlng doc being pushed in marker directory
        markers.push(e.latlng)
        console.log(markers) // markers is storing all markers, first is array, and rest are documents.
        this.setState({markers})
        this.setState({
          current_marker: e.latlng
        })

        
        
        // Getting Longitude and Latitude for reverse geocoding (sent to backend)
        var lat  = this.state.current_marker.lat.toString();
        var lng  = this.state.current_marker.lng.toString();
        var pos = lat.concat(",",lng)
        //console.log(this.state.current_marker); //Debug latlng{} w
        //console.log(this.state.current_marker.lng); // Debug double w
        this.setState({
          position: pos 
        })
        this.postDataToServer(this.state.position);
        console.log(this.state.address)
      }
        
      // ?? What is handle is doing?
      handleLocationFound = (e) => {
        console.log(e.markers)
        this.setState({
          hasLocation: true,
          latlng: e.markers,
        })
        console.log(e.markers) // ??? What is e.markers doing?
      }

     handleSubmit = (e) =>{
        console.log(this.state.current_marker); // Debug
        console.log(this.state.current_marker.lng); // Debug double w
        var coordinates_arr = []
        coordinates_arr[0] = this.state.current_marker.lng;
        coordinates_arr[1] = this.state.current_marker.lat;
        console.log(coordinates_arr)
        this.setState(prevState =>({

          location: {
            ...prevState.location,
            coordinates : coordinates_arr
          }
          
        }),() =>{
          //callback, kept axios call in callback since setstate do not immediately updates state, so callback(google it)
          let document = {
            storeName:this.state.storeName,
            streetName:this.state.address.streetName,
            location:this.state.location
          }
          console.log(document)
          this.postLocationToServer(document);
        })
        console.log(this.state.location);
        
      }

      // Send Location data to 8082
      postLocationToServer = async (data)=>{
        await axios.post(`http://localhost:8082/booking/create`,data).then(
          (response) => {
            toast.success("Booking confirmed");
          },
          (error) => {
            toast.error("Booking Failed");
          }
        )
      }

      // Send Data to Server 8081
    postDataToServer= async (data)=>{
        await axios.get(`${base_url}/revgeocode/${this.state.position}`).then(
            (response)=>{
                
                this.setState({
                    address: response.data 
                })
                toast.success("Address retrived successfully");
                console.log(this.state.address) // Returned from backend and sent to Locationinfo.js
            },
            (error)=>{
                console.log(error)
                this.setState({
                    address:"Please Try after some time"
                })
                toast.error("Something is wrong");
            }
          )
    };
      
    render() {
        const SearchBar = withLeaflet(SearchMap); 
        return (
        <div className="mapContainer">
            <ToastContainer />
            <Map 
                className="map" 
                center={[51.505, -0.09]}
                zoom={13} 
                onlocationfound={this.handleLocationFound}  // Not doing anything rn.
                onclick={this.handleClick}
                ref={this.mapRef}>
                <TileLayer
                attribution='&amp;copy <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />
                <SearchBar />
                {this.state.markers.map((position, idx) => 
                    <Marker key={`marker-${idx}`} position={position} icon={myIcon}>
                        <Popup>
                        <span>testing123<br/> Easily customizable.</span>
                        </Popup>
                    </Marker>
                    )
                }
                
            </Map>
            
            <LocationInfo 
              onSubmit={this.handleSubmit} 
              storeName={this.state.storeName}
              streetName={this.state.address.streetName} 
              municipality={this.state.address.municipality} 
              countrySubdivisionName={this.state.address.countrySubdivisionName}
              postalCode={this.state.address.postalCode} 
              country = {this.state.address.country}
              />
              <input type="text" value={this.state.storeName} onChange={this.handleChange}></input>

        </div>

        )
    } 
}

export default MapComponent