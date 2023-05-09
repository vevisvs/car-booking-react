import axios from 'axios';
import React, {createContext, useEffect} from 'react'
import { useState } from "react";

export const UserContext = createContext();

export const UserProvider = ({children}) => {
    const [user,setUser] = useState(null)
    const [isLog, setIsLog] = useState(false);
    const [errorMessage, setErrorMessage] = useState("")
    const [userCity, setUserCity] = useState([]);
    const [citySelected, setCitySelected] = useState('')
    const [userLogged, setUserLogged] = useState()

    const getCities = async()=>{
        const res = await fetch('http://3.141.42.20:8080/city/list')
        const data = await res.json()
        setUserCity(data)
    }

    const userEmail = sessionStorage.getItem("emailUser")

    const getUser = async()=>{
        const res = await axios.get(`http://3.141.42.20:8080/user/searchEmail/${userEmail}`)
        const data = await res.data
        setUser(data)
    }
    return(
        <UserContext.Provider 
            value={{user, 
                    setUser , 
                    getUser, 
                    isLog, 
                    setIsLog, 
                    errorMessage, 
                    setErrorMessage, 
                    getCities,
                    userCity,
                    setUserCity,
                    citySelected, 
                    setCitySelected,
                    userLogged,
                    setUserLogged}}>
            {children}
        </UserContext.Provider>
    )
}
