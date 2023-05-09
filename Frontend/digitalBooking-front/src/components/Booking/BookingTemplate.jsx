import React from 'react'
import Booking from './Booking'
import BookingButtom from './BookingButtom'
import './Booking.css'

const BookingTemplate = () => {

  return (
    <div className='booking'>
        <h2 className='booking-title'>Fechas disponibles</h2>
        <Booking />
        <BookingButtom />
    </div>
  )
}

export default BookingTemplate