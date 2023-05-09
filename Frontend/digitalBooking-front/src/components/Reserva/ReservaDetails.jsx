import React, { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import './Reserva.css'
import { DateContext} from '../Context/DateContext'
import { UserContext } from '../Context/UserContext'
import { useContext } from "react";
import { TimeContext } from '../Context/TimeContext'
import axios from 'axios'

const ReservaDetails = ({dataProduct}) => {
  const navigate = useNavigate();

  const { dateBooking, setDateBooking } = useContext(DateContext); //context de fechas seleccionadas
  const { time } = useContext(TimeContext);                        //context de la hora seleccionada
  const { user, setUser } = useContext(UserContext);               //context de la info de usuario
  const [bookingError, setBookingError] = useState("");


//asegurar de que las fechas seleccionadas estén actualizadas antes de que se muestre el componente ReservaDetails
  useEffect(() => { 
    console.log("date en details:", dateBooking);
  }, [dateBooking]);

  const initialDate = dateBooking[0] ? dateBooking[0].toISOString().slice(0, 10) : "_/_/_";
  const finalDate = dateBooking[1] ? dateBooking[1].toISOString().slice(0, 10) : "_/_/_";

  const createBooking = () => {
    if( time && initialDate && finalDate && dataProduct?.id && user?.id){ //usando las propiedades thruty: la condicion es verdadera si no es vacio, nulo o indefinido
      const token = sessionStorage.getItem('token');
      axios.post('http://3.141.42.20:8080/booking/add', {
          startTime: time,
          initialDate,
          finalDate,
          idModel: dataProduct.id,
          idClient: user.id
      }, {
        headers: {
          Authorization: 'Bearer ' + token
        },
      })
        .then(res => {
        console.log('reserva creada exitosamente', res.data); 
        if(res.status === 200 || res.status === 201){
          navigate("/confirmacion")
        }
      })
      .catch(error => alert('Hubo un error al crear la reserva', error));
    } else {
      console.log("faltan datos para completar la reserva")
      setBookingError("Faltan datos para completar la reserva");
    }
  }

  return (
    <>
        <section className='reservation-details'>
          
          <div className='box-title-image'>
            <h2>Detalle de la reserva</h2>
              <div className='detail-prod-image'>
                { dataProduct &&
                  <img className='product-image' src={dataProduct?.images[0].url} alt='imagen de producto'/> 
                }
              </div>
          </div>
          <div className='box-information'>
            <div className='details-info'>
              {dataProduct && <h4>{dataProduct?.categorie.name}</h4>}
              {dataProduct && <h2>{dataProduct?.name}</h2>}
              {dataProduct && <h5 className='city-detail'>{` ${dataProduct?.city?.name} - ${dataProduct?.city?.country}`}</h5>}
              <div className='ctn-score'>
                <i class="fa fa-star" aria-hidden="true"></i>
                <i class="fa fa-star" aria-hidden="true"></i>
                <i class="fa fa-star" aria-hidden="true"></i>
                <i class="fa fa-star" aria-hidden="true"></i>
                <i class="fa fa-star-half-o" aria-hidden="true"></i>
              </div>
            </div>
            <div className='info-checking'>
              <hr />
                <div className='checks'>
                  <h3>Entrega</h3>
                  <p>{initialDate}</p>
                </div>
              <hr />
                <div className='checks'>
                  <h3>Devolución</h3>
                  <p>{finalDate}</p>
                </div>
              <hr />
            </div>
            {
              bookingError && <div className='error-booking'> <i class="fa fa-exclamation-triangle" aria-hidden="true"></i> {bookingError}</div>
            }
            <div className='ctn-button-reserv'>
              <button className='btn-reservation' onClick={createBooking}>Confirmar reserva</button>
            </div>
          </div>
        </section>
    </>
  )
}

export default ReservaDetails