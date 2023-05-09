import React from 'react'
import { useNavigate } from 'react-router-dom'
import icon from './../../assets/Vector.png'

const ConfirmTemplate = () => {
    const navigate = useNavigate();
    return (
        <>
            <div className='ctn-product-confirm'>
                <section className='section-confirm'>
                    <img src={icon} className='product-confirm-icon' alt='icon'></img>
                    <h2>¡Pronto!</h2>
                    <h3>El vehículo se ha agregado correctamente</h3>
                    <button onClick={() => navigate("/")} className='product-confirm-button'>Ok</button> 
                </section>
            </div>
        </>
    )
}

export default ConfirmTemplate