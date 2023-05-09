import React from "react"
import {useState, createContext} from 'react'
import axios from 'axios'

export const DataProductContext = createContext();

export const DataProductProvider = ({children}) => {

    const [product,setProduct] = useState()
    const [dataProduct, setDataProduct] = useState(); 
    const [categoryValue, setCategoryValue] = useState("")
    const [cityValue, setCityValue] = useState("") //guardo los modelos de ciudad id
    const [dateRange, setDateRange] = useState([null, null]);
    const [startDate, endDate] = dateRange;

    const[loading, setLoading] = useState(false);

    const connection = axios.create({                              
        baseURL: "http://3.141.42.20:8080/",
        headers: {
        "Content-type":"application/json",
        "Accept": "application/json",
        } 
    })

    const getRecommendations = async (url) => {
        try {
            const response = await connection.get(url);
                setDataProduct(response.data);
                return response.data;
        } catch (error) {
            console.log(error);
            setDataProduct([]);
        } 
    }

    const getProduct = async(id) => {                                         
        return connection.get(`/model/search/${id}`)
            .then((res) => {
            setProduct(res.data)
            return res.data;
        })
    }
    return (
        <DataProductContext.Provider 
            value={{dataProduct, 
                    setDataProduct, 
                    connection,
                    categoryValue,
                    setCategoryValue,
                    cityValue,
                    setCityValue,
                    product,
                    getProduct,
                    startDate,
                    endDate,
                    dateRange, 
                    setDateRange,  
                    getRecommendations,
                    loading,
                    setLoading, 
                    }}>
            {children}
        </DataProductContext.Provider>
    )
}

export default DataProductProvider 



