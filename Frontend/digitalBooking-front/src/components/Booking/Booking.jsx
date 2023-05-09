import React, { useEffect } from 'react'
import { useState } from 'react';
import DatePicker, { registerLocale } from 'react-datepicker'
import 'react-datepicker/dist/react-datepicker.css'
import es from 'date-fns/locale/es'
import './Booking.css'
import { DateContext} from '../Context/DateContext'
import { useContext } from 'react';
import { useParams } from 'react-router-dom'
import axios from 'axios'
registerLocale("es",es)

const Booking = () => {
    const { id } = useParams();
    const { dateBooking, setDateBooking} = useContext(DateContext); //context de fecha
    const [rangeDate, setRangeDate] = useState([null, null]);       // state para guardar el rango de fechas
    const [startDate, endDate] = rangeDate;                         // fecha inicio y final
    const [bookings, setBookings] = useState([]);                   // state para guardar las reservas generadas

    // useEffect(() => {
    //     setDateBooking(rangeDate); 
    // }, [rangeDate])

                                               // Al context le asigno la info guardada en rango de fechas

    const dateNow = new Date()                 // Asigno la fecha del dia de hoy a una variable 

    const connection = axios.create({           // creo una configuracion para la peticion con axios
        baseURL: "http://3.141.42.20:8080",
        headers: {
        "Content-type":"application/json",
        "Accept": "application/json",
        }
    })

    // peticion get a API para buscar las reservas realizadas y agregarlas al state:
    useEffect(() => {      
        connection.get("/booking/list")
            .then((res) => {
            setBookings(res.data);
        })
    }, [])

    //filtro el array de reservas y luego mapeo por la fecha, agregando un objeto con fecha de inicio y fin:
    // const getScheduledDates = () => {
    //     const dataBookings = bookings.filter((b) => b.model.id === id).map((date) => ({
    //         start: new Date(date.initialDate), end: new Date(date.finalDate),
    //     }));
    //     return dataBookings;
    // }


    //asegurar de que el estado se actualice inmediatamente después de que el usuario seleccione una fecha, 
    //y antes de que se cargue el componente ReservaDetails
    const handleDateChange = (dateRange) => {
        setRangeDate(dateRange);
        setDateBooking(dateRange);
    }

    return (
            <div className='booking-ctn'>
                <div className='booking-calendar-ctn'>
                    <DatePicker
                    locale={es}
                    dateFormat="d 'de 'MMMM 'de' yyyy"
                    minDate={dateNow}
                    placeholderText="Check in - Check out"
                    selectsRange={true}
                    monthsShown={2}
                    startDate={startDate}
                    endDate={endDate}
                    highlightDates={true}
                    // excludeDates={getScheduledDates()?.map((d) => d.end)}
                    // onChange={(update) => {setRangeDate(update)}}
                    onChange={handleDateChange} //manejador de los cambios
                    inline
                    />
                </div>
            </div>
    )
}

export default Booking