import React,{useState} from 'react'
import { Link } from 'react-router-dom'

const RecItem = ({id,brand,name,score,description,categorie_id,images,city}) => {
    const [totalText, setTotalText] = useState(false)

    const handleTotalText = ()=> setTotalText(totalText=>!totalText)

    const toggleTotalText = totalText ? "totalText" : ""

    
    return (
        <div className="recItem" key={id} >
            <div className='rec-img-ctn'>
                <img className='rec-img' src={images[0].url} alt={name} />
            </div>
            <div className='rec-info'>
                <div className='rec-info-main-ctn'>
                    <div className='rec-brand-ctn'>
                        <h5 className='rec-category'>{categorie_id}</h5>
                        <h4 className='rec-title'>{brand} {name}</h4>
                    </div>
                    <div className='rec-score-ctn'>
                        <span className='rec-score'>{score}</span>
                    </div>
                </div>
                <p className='rec-location'> <span className='map-marker'><i className="fa fa-map-marker" aria-hidden="true"></i></span> {city.name}</p>
                <p className={`rec-description ${toggleTotalText}`}>{description}</p>
                <span className='totalText-btn' onClick={handleTotalText}>{totalText?'Ver menos':'Ver mas'}</span>
                <Link to={"/product/" + id}>
                    <button className='rec-btn'>Ver detalle</button>
                </Link>
            </div>
        </div>
    )
}

export default RecItem