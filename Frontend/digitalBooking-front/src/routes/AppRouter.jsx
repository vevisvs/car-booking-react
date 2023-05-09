import React from 'react'
import { Routes , Route} from 'react-router-dom'
import Home from '../components/Home/Home'
import Register from '../components/Register/Register'
import Login from '../components/Login/Login'
import TemplateProduct from '../components/TemplateProduct/TemplateProduct'
import Reserva from '../components/Reserva/Reserva'
import ReservaConfirmada from '../components/Reserva/ReservaConfirmada/ReservaConfirmada'
import AdminTemplate from '../components/Admin/AdminTemplate'
import ConfirmTemplate from '../components/Admin/ConfirmTemplate'


const AppRouter = () => {
    
    return (
        <Routes>
            <Route path='/' element={<Home/>} />
            <Route path='/register' element={<Register/>}/>
            <Route path='/login' element={<Login/>}/>
            <Route path='/product/:id' element={<TemplateProduct/>}/>
            <Route path='/product/:id/reserva' element={<Reserva />}/>
            <Route path='/confirmacion' element={<ReservaConfirmada />}/>
            <Route path='/administracion' element={<AdminTemplate />} />
            <Route path='/administracion/confirmacion' element={<ConfirmTemplate />} />
        </Routes>
    )
}

export default AppRouter