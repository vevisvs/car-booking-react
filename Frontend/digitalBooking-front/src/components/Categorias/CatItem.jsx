import React, { useContext } from 'react'
import { DataProductContext } from '../Context/DataProductContext';


const CatItem = ({name,url,description, }) => {
    const {setDataProduct, getRecommendations, setLoading} = useContext(DataProductContext)

    const searchByCategory = (e) => {
        let nameCategory =  ""; 
        nameCategory = e.currentTarget.dataset.category; //capturar nombre de categoria y guardarla en state
        setLoading(true);
        getRecommendations(`/model/searchNameByCategorie/${nameCategory}`)
            .then((data) => setDataProduct(data))
            .finally(() => setLoading(false));
    }

    return (
        <div onClick={searchByCategory} className="catItem" data-category={name}>
                    <img className='cat-img' src={url} alt={name} />
                <div className='cat-info'>
                    <h4 className='cat-title'>{name}</h4>
                    <p className='cat-description'>{description} </p>
                </div>
        </div>
    )
}

export default CatItem