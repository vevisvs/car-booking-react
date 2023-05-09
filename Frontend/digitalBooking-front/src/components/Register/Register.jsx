import React, { useContext, useEffect } from 'react'
import { useState } from 'react';
import './Register.css'
import axios from 'axios';
import { UserContext } from '../Context/UserContext';

const Register = () => {
        const [name, setName] = useState('');
        const [lastName, setLastName] = useState('');
        const [email, setEmail] = useState('');
        const [password, setPassword] = useState('');
        const [confirmPassword, setConfirmPassword] = useState('');
        const {getCities, userCity, citySelected, setCitySelected, userLogged, setUserLogged} = useContext(UserContext);
        
        const newUser = {
            name,
            lastName,
            email,
            password,
            city: citySelected,
            "roleId": 1,
            "id_City": 1
        }

        useEffect(()=>{
            getCities()
        },[])

        console.log("city en context:", userCity)
        console.log("city seleccionada:", citySelected)


        const url = "http://3.141.42.20:8080/user/add"

        const handleSignUp = (url,newUser)=>{
            axios.post(url,newUser)
            .then(res=>console.log(res))
        }
        console.log("nuevo usuario:", newUser)

        const handleSubmit = (event) => {
            event.preventDefault();
            // Validar los campos de entrada
            if (
            name === '' ||
            lastName === '' ||
            email === '' ||
            password === '' ||
            confirmPassword === '' ||
            citySelected === ''
            ) {
            alert('Por favor complete todos los campos');
            } else if (password !== confirmPassword) {
            alert('La contraseña y la confirmación de contraseña no coinciden');
            } else {
            // Enviar los datos del formulario al servidor de registro
            handleSignUp(url,newUser)
            setUserLogged(newUser)
            alert("Se creo el usuario con exito")
            setName('')
            setLastName('')
            setEmail('')
            setPassword('')
            setConfirmPassword('')
            setCitySelected('')
            }
        };

return (
    <div>
        <div className='contenedorForm'>
                <h2>Crear Cuenta</h2>
                <form id='register' className='inputsV2' onSubmit={handleSubmit}>
                <div className='nombreApellido'>
                        <input
                        className='nombre-input-register'
                        placeholder='Nombre'
                        type="text"
                        id="name"
                        value={name}
                        onChange={(event) => setName(event.target.value)}
                        />
                        <input
                        className='apellido-input-register'
                        placeholder='Apellido'
                        type="text"
                        id="lastName"
                        value={lastName}
                        onChange={(event) => setLastName(event.target.value)}
                        />
                </div>
                <select defaultValue={1} name='ciudades' className='input-city-user' onChange={(event)=>setCitySelected(event.target.value)}>
                    <option value={1} disabled hidden>Selecciona tu ciudad</option>
                    {
                        userCity.map(city=>{
                            return(
                            <option className='city-options' key={city.id} value={city.name}>{city.name}</option>
                            )
                        })
                    }
            </select>
                <div className='correoContraseña'>
                        <input
                        className='email-register'
                        placeholder='Correo electronico'
                        type="email"
                        id="email"
                        value={email}
                        onChange={(event) => setEmail(event.target.value)}
                        />
                        <input
                        className='password-register'
                        placeholder='Contraseña'
                        type="password"
                        id="password"
                        value={password}
                        onChange={(event) => setPassword(event.target.value)}
                        />
                        <input
                        className='confirm-register'
                        placeholder='Confirmar contraseña'
                        type="password"
                        id="confirm-password"
                        value={confirmPassword}
                        onChange={(event) => setConfirmPassword(event.target.value)}
                        />
                </div>
                <button className='button-register' type="submit">Crear cuenta</button>
                <div className='paragrafoRegister'>
                    <p>¿Ya tienes una Cuenta?</p>
                    <a href='/login'>Iniciar sesión</a>
                </div> 
            </form>
        </div>
    </div>
    )
}

export default Register