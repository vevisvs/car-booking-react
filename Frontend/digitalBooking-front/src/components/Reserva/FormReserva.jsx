import React, { useContext, useState } from 'react'
import './Reserva.css'

const FormReserva = ({user}) => {
    const [ciudad, setCiudad] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    const regex =  /^[a-zA-Z\s]*$/;

    const validateInput = (input, regex, nombre) => {
        if(!input){
            return `El campo ${nombre} es obligatorio`;
        }
        if(!regex.test(input)){
            return `El campo ${nombre} solo debe contener letras`;
        }
        return;
    }
    const validateInputCity = (ciudad) => {
        return validateInput(ciudad, regex, 'ciudad');
    }

    const handleCity = (event) => {
        const valueInput = event.target.value;
        const error = validateInputCity(valueInput, regex, 'ciudad');
        setErrorMessage(error);
        setCiudad(valueInput);
    }
    return (
        <> 
            <form className='ctn-form'>
                <h2>Completá tus datos</h2>
                <div className='ctn-form-info'>
                    <div className='form-row-names'>
                        <div className='row-name'>
                            <label htmlFor='nombre'>Nombre</label>
                            <input type='text' 
                                name='nombre' 
                                placeholder={user.name} 
                                className='form-input'
                                disabled
                            ></input>
                        </div>
                        <div className='row-lastname'>
                            <label htmlFor='apellido'>Apellido</label>
                            <input type='text' 
                                name='apellido' 
                                placeholder={user.lastName} 
                                className='form-input'
                                disabled
                                ></input>
                        </div>
                    </div>
                    <div className='form-row-contact'>
                        <div className='row-email'>
                            <label htmlFor='email'>Email</label>
                            <input type='email' 
                                name='email' 
                                placeholder={user.email} 
                                className='form-input'
                                disabled
                                ></input>
                        </div>
                        <div className='row-city'>
                            <label htmlFor='ciudad'>
                                Ciudad
                                <input type='text' 
                                    name='ciudad'
                                    className='form-input'
                                    onChange={handleCity}
                                    >
                                </input>
                                {errorMessage && <span style={{color: 'red'}}>{errorMessage}</span>}
                            </label>
                        </div>
                    </div>
                </div>
            </form> 
        </>
    )
}

export default FormReserva