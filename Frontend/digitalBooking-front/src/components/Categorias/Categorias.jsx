import React,{useState,useEffect} from 'react'
import CatItem from './CatItem'
import './Categorias.css'

const Categorias = () => {
    const [categories, setCategories] = useState([])
    const getCategories = async()=>{
        const res = await fetch('http://3.141.42.20:8080/categorie/list')
        const data = await res.json()
        setCategories(data)
    }
    useEffect(()=>{
        getCategories()
    },[])
    return (
        <div className='categories'>
            <h2 className='categories-title'>Buscar por tipo de Auto</h2>
            <div className='categories-ctn'>
                {
                    categories.map(categorie=>{
                        return(
                            <CatItem key={categorie.id} {...categorie}/>
                        )
                    })
                }
            </div>
        </div>
    )
}

export default Categorias