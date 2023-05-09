import React, { useContext, useEffect } from 'react'
import { useState} from 'react';
import './Login.css'
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { UserContext } from '../Context/UserContext';

const Login = () => {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const { errorMessage } = useContext(UserContext);

    const navigate = useNavigate()
    const url = "http://3.141.42.20:8080/login"
    const user = {
        email,
        password,
    }
    const handleSignIn = async(url,user)=>{
        try {
        const res = await axios.post(url,user)
        const {token} = await res.data
        if (res.status === 200 || res.status === 201) {
            console.log(res.data);
            sessionStorage.setItem("token",token)
            sessionStorage.setItem("emailUser",email)
            window.location.reload()
        }
        } catch (error) {
            console.error("Error al loguearse", error);
        }
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        // Validar los campos de entrada
        if (email === '' || password === '') {
            alert('Por favor ingrese su nombre de usuario y contraseña');
            }else if (password.length < 6){  
                alert('La contraseña no tiene los caracteres suficientes');
            } else{
            // Enviar los datos del formulario al servidor de autenticación
            handleSignIn(url,user)
            navigate("/")
            }
        }
return (
    <div>
        <div className='contenedorPadre'>
            { errorMessage && (
                <div className='ctn-message'>
                    <i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
                    <p className='error-message'>{errorMessage}</p>
                </div> 
            )}
        <h2>Iniciar sesión</h2>
        <form className='inputs' onSubmit={handleSubmit}>
            <div>
                <input
                placeholder='Correo electronico'
                type="email"
                id="email"
                value={email}
                onChange={(event) => setEmail(event.target.value)}
                />
            </div>
            <div>
                <input
                placeholder='Contraseña'
                type="password"
                id="password"
                value={password}
                onChange={(event) => setPassword(event.target.value)}
                />
            </div>
            <button className='boton' type="submit">Iniciar sesión</button>
            <div className='paragrafo'>
                    <p>¿Aún no tienes Cuenta?</p>
                    <a href='/register'>Registrate</a>
            </div> 
        </form>
        </div>
    </div>
)
}

export default Login