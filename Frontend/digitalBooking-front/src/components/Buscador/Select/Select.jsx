import React,{useContext, useEffect, useState} from 'react'
import { DataProductContext } from '../../Context/DataProductContext'
import './Select.css'

const Select = () => {
    const [cities, setCities] = useState([])
    const {cityValue,setCityValue} = useContext(DataProductContext) 
    const getCities = async()=>{
        const res = await fetch('http://3.141.42.20:8080/city/list')
        const data = await res.json()
        setCities(data)
    }
    useEffect(()=>{
        getCities()
    },[])
    return (
            <select defaultValue={1} name='ciudades'onChange={(event)=>setCityValue(event.target.value)}>
                <option value="">¿A dónde vamos?</option>
                {
                    cities.map(city=>{
                        return(
                        <option key={city.id} value={city.name} >{city.name}</option>
                        )
                    })
                }
            </select>
    )
}

export default Select