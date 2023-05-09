import React, { useContext } from 'react'
import DatePicker, { registerLocale } from 'react-datepicker'
import './Calendar.css'
import 'react-datepicker/dist/react-datepicker.css'
import es from 'date-fns/locale/es'
import { DataProductContext } from '../../Context/DataProductContext';
registerLocale("es",es)

const Calendar = () => {
    const {setDateRange, startDate, endDate} = useContext(DataProductContext);
    
    return (
            <DatePicker className='calendar'
            locale={es}
            showIcon
            dateFormat="d 'de 'MMMM 'de' yyyy"
            minDate={new Date()}
            placeholderText="Check in - Check out"
            selectsRange={true}
            startDate={startDate}
            endDate={endDate}
            onChange={(update) => {setDateRange(update)}}
        />
    )
}

export default Calendar