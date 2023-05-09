import React from 'react'
import "./Footer.css"

const Footer = () => {

return (
    
    <footer>
        <div className='contenedor'>
            <div className='copyBrand'>
                <p>©2023 Digital Booking</p>
            </div>
            <div className='redesSociales'>
                <a href='https://www.facebook.com/' className='linked'>
                        <i className="fa fa-facebook-official" aria-hidden="true"></i>
                </a>
                <a href='https://www.linkedin.com/' className='linked'>
                        <i className="fa fa-linkedin" aria-hidden="true"></i>
                </a>
                <a href='https://twitter.com/' className='linked'>
                        <i className="fa fa-twitter" aria-hidden="true"></i>
                </a>
                <a href='https://www.instagram.com/' className='linked'>
                    <i className="fa fa-instagram" aria-hidden="true"></i>
                </a>
            </div>
        </div>
    </footer>

)
}

export default Footer