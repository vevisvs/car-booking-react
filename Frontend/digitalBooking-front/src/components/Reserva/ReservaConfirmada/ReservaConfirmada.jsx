import React from 'react'
import { useNavigate } from 'react-router-dom';
import icon from '../../../assets/Vector.png'
import '../ReservaConfirmada/ReservaConfirmada.css'

const ReservaConfirmada = () => {
    const navigate = useNavigate();
    return (
        <div className='ctn-reserva-confirm'>
            <section className='section-confirm'>
                <img src={icon} className='reserva-confirm-icon'></img>
                <h2>¡Muchas gracias!</h2>
                <h3>Su reserva se ha realizado con éxito</h3>
                <button onClick={() => navigate("/")} className='reserva-confirm-button'>Ok</button> 
            </section>
        </div>
    )
}

export default ReservaConfirmada