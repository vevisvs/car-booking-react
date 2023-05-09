import React from "react"
import {useState, createContext} from 'react'

export const DateContext = createContext();

export const DateProvider = ({children}) => {

    const [dateBooking, setDateBooking] = useState([null, null]);

    return (
        <DateContext.Provider value={{dateBooking, setDateBooking}}>
            {children}
        </DateContext.Provider>
    )
}

export default DateProvider