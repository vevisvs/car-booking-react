import React from 'react'
import { useNavigate } from 'react-router-dom';

const Headlong = () => {
  const navigate = useNavigate();
  const toBack = () => {
    navigate(-1);
  }
  return (
    <div className='ctn-headlong'>
        <h1>Administración</h1>
        <i className="fa fa-chevron-left" aria-hidden="true" onClick={toBack}></i>
    </div>
  )
}

export default Headlong