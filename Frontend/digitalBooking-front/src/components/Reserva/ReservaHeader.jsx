import React from 'react'
import { useNavigate } from "react-router-dom";

const ReservaHeader = ({dataProduct}) => {
  const navigate = useNavigate();

  const goBack = () => {
    navigate(-1);
  }


  return (
    <div className='ctn-booking-header'>
        <div className='header-product-info'>
            {dataProduct && <h3>{dataProduct.name}</h3>}
            {dataProduct && <h2>{dataProduct.categorie?.name}</h2>}
        </div>
        <div className='icon-header'>
            <i className="fa fa-chevron-left" aria-hidden="true" onClick={goBack}></i>
        </div>
    </div>
  )
}

export default ReservaHeader