import React from 'react'
import { useNavigate } from 'react-router-dom'
import './Booking.css'
import { useContext } from 'react'
import { UserContext } from '../Context/UserContext'

const BookingButtom = ({id}) => {
    const navigate = useNavigate();
    const {user, setIsLog, setErrorMessage} = useContext(UserContext);

    const startReservation = () => {
        if (user){
            setIsLog(true)
            navigate(`/product/${id}/reserva`)
        } else {
            setErrorMessage("Debes iniciar sesión para realizar una reserva")
            navigate('/login')
        }
    }
    return (
        <div>
            <button className='booking-btn' onClick={startReservation}>Iniciar Reserva</button>
        </div>
    )
}

export default BookingButtom