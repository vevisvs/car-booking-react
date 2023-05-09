import React from 'react'
import { createContext } from 'react'
import { useState } from 'react'

export const TimeContext = createContext();

export const TimeProvider = ({children}) => {
    const [time, setTime] = useState();

    return(
        <TimeContext.Provider value={{time, setTime}}>
            {children}
        </TimeContext.Provider>
    )
}
export default TimeProvider