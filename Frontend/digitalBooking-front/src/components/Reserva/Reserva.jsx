import React, { useEffect, useContext} from 'react'
import FormReserva from './FormReserva'
import ReservaCalendar from './ReservaCalendar'
import ReservaDetails from './ReservaDetails'
import ReservaHeader from './ReservaHeader'
import Politicas from '../Politicas/Politicas'
import Login from '../Login/Login'
import './Reserva.css'
import { DataProductContext } from '../Context/DataProductContext'
import { useParams } from 'react-router-dom'
import { UserContext } from '../Context/UserContext'

const Reserva = () => {
    const {id} = useParams();
    const {product, getProduct} = useContext(DataProductContext);
    const {user} = useContext(UserContext);

    useEffect(() => {
        getProduct(id);
        window.scroll(0,0)
    },[])

    return (
        <>
                <div className='ctn-booking'>
                    <ReservaHeader dataProduct={product}/>
                    <div className='booking-sections'>
                        <div className='booking-forms'>
                            <FormReserva user={user} />
                            <ReservaCalendar />
                        </div>
                        <div className='booking-details'>
                            <ReservaDetails dataProduct={product}/>
                        </div>
                    </div>
                    <div className='ctn-politicas'>
                        <Politicas products={product}/>
                    </div>
                </div> 
        </>
    )
}

export default Reserva