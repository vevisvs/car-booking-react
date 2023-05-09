import React,{useEffect, useContext} from 'react'
import './Recomendados.css'
import RecItem from './RecItem'
import { DataProductContext } from '../Context/DataProductContext'
import Loader from '../Loader/Loader'

const Recomendados = () => {

    const {dataProduct, loading, setLoading, getRecommendations} = useContext(DataProductContext)

    //cuando se monta el componente, se muestra toda la lista de vehiculos. Luego la seccion va cambiando
    //a medida de que se ejecuten las busquedas 
    useEffect(() => {
        const urlAllModels = `/model/list`;
        setLoading(true);
        getRecommendations(urlAllModels)
            .finally(() => setLoading(false));
    }, []);

    if (loading) {
        return <div className="no-products-ctn"><span className='no-product'><Loader /></span></div>;
    }
    // if (dataProduct === "" || dataProduct.length === 0) {
    //     return <div className="no-products-ctn"><span className='no-product'>No se encontraron productos disponibles</span></div>;
    // }

    return (
        <div className='recommended'>
            <h2 className='recommended-title'>Recomendaciones</h2>        
                    {dataProduct?.length>0 ?
                    <div className='rec-ctn'>
                        {dataProduct?.map(product=>(
                            <RecItem key={product.id} {...product}/>
                        ))}    
                    </div>:
                    <div className="no-products-ctn"><span className='no-product'>No se encontraron productos disponibles</span></div>
                }
        </div>
    )
}

{/* <div className='rec-ctn'>
                    {dataProduct?.length ? dataProduct?.map(product => (
                        <RecItem key={product.id} {...product} />
                    )) : <div className="no-products-ctn"
                        style={{ gridColumn: "span 2" }}><span className='no-product'>No se encontraron productos disponibles</span></div>
                    }
                </div> */}

export default Recomendados