import React, { useContext } from 'react'
import './Buscador.css'
import Calendar from './Calendar/Calendar'
import Select from './Select/Select'
import { DataProductContext } from '../Context/DataProductContext'

const Buscador = () => {
    const {cityValue, dateRange, setDataProduct, connection, setLoading} = useContext(DataProductContext)

    //esta funcion previene el envio del form automatico y ejecuta la funcion del boton con sus valores:
    const handleSubmit = (event) => {
        event.preventDefault();
        search(dateRange, cityValue);
    };

    //funcion que ejecuta el boton, aqui se realizan los filtros de busqueda:
    const search = async () => {
        if(!cityValue && !dateRange.length){ //si los campos estan vacios y se clickea el botón, no se ejecuta nada
            return;
        }
        try {
            let url;
            if (dateRange[0] && dateRange[1] && cityValue) {
                const startDate = dateRange[0].toISOString().slice(0, 10);
                const endDate = dateRange[1].toISOString().slice(0, 10);
                url = `/booking/available-models?cityName=${cityValue}&startDate=${startDate}&endDate=${endDate}`;
            } else if (dateRange[0] && dateRange[1]) {
                const startDate = dateRange[0].toISOString().slice(0, 10);
                const endDate = dateRange[1].toISOString().slice(0, 10);
                url = `/booking/available-modelsByDate?startDate=${startDate}&endDate=${endDate}`;
            } else {
                url = `/model/searchCity/${cityValue}`;
            } 
            setLoading(true);
            const response = await connection.get(url);
            setDataProduct(response.data);
        } catch (error) {
            console.log(error);
            setDataProduct([]);
        } finally {
            setLoading(false);
        }
    };

return (
    <div className='buscador'>
        <h1>Busca las mejores ofertas en Alquiler de Autos</h1>
        <form className='searcher' onSubmit={handleSubmit} >
            <Select/>
            <Calendar/>
            <button type="submit" className='btn-buscar' onClick={search} >Buscar</button>
        </form>
    </div>
)
}

export default Buscador