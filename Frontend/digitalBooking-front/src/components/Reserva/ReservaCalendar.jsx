import React from 'react'
import Booking from '../Booking/Booking'
import horarios from './HorariosReserva'
import { TimeContext } from '../Context/TimeContext'
import { useContext } from 'react'

const ReservaCalendar = () => {
  const { time, setTime } = useContext(TimeContext); 

  const getHour = (e) => {
    setTime(e.target.value)
  }
  
  return (
    <>
        <div className='ctn-calendar-booking'>
            <h2>Seleccioná tu fecha de reserva</h2>
            <Booking />
        </div>
        <section className='ctn-reservation-hours'>
            <h2>Indica el horario de retiro</h2>
            <div className='selection-hours'>
              {time ? 
                <h4>Tu vehículo estará listo para el retiro a las {time.slice(0,5)}</h4> : <h4>Tu vehículo estará listo para el retiro a la hora indicada</h4>
              }
              <select defaultValue={1} className='hours-check' onChange={getHour} required>
                  <option value={1} disabled hidden>Selecciona el horario</option>
                  {horarios.map(item => {
                    return <option key={item.id} value={item.value}>{item.label}</option>
                  })}
              </select>
            </div>
        </section>
    </>
  )
}

export default ReservaCalendar