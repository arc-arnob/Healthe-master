import React, { Component } from 'react'

export default class LocationInfo extends Component {
    constructor(props){
        super(props)
        
    }
    state ={
        storeName:"",
        streetName:"",
        municipality:"",
        countrySubdivisionName:"",
        postalCode:"",
        country:"",
        lat:"",
        lng:""

    }
    onFormSubmit = (event) =>{
        event.preventDefault();
        this.props.onSubmit(this.state.term)
        console.log("Clicked!")
        
    }
    
    


    getCurrentDate = (separator='') =>{
        let newDate = new Date()
        let date = newDate.getDate();
        let month = newDate.getMonth() + 1;
        let year = newDate.getFullYear();

        return `${year}${separator}${month<10?`0${month}`:`${month}`}${separator}${date}`
    }
    render() {
        return (
            <form onSubmit={this.onFormSubmit}>
            <div className="locationinfo">
            <div className="ui card">
                {/* <div className="image">
                    <img src="/images/avatar2/large/kristy.png" alt="Welocme"/>
                </div> */}
                <div className="content">
            <a className="header">Location Info:</a>
                <p>Store Name: {this.props.storeName}</p>
                <p>Street: {this.props.streetName}</p>
                <p>Area: {this.props.municipality}</p>
                <p>State: {this.props.countrySubdivisionName}</p>
                <p>Postal code: {this.props.postalCode}</p>
                <p>Country: {this.props.country}</p>
                    <div className="meta">
            <span className="date">{this.getCurrentDate}</span>
                    </div>
                    <div className="description">
                    {/* Description */}
                    Desc
                    </div>
                </div>
                <div className="extra content">
                    <a>
                    <i className="user icon"></i>
                    22 People currently booked thier visit here form backend
                    </a>
                </div>
                <button type = "submit" className="ui secondary button">
                    Mark my visit
                </button>
            </div>
            </div>
            </form>
        )
    }
}
