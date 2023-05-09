import React, { useEffect} from 'react'
import { Link } from 'react-router-dom'
import "./Avatar.css"
import {useNavigate}from 'react-router-dom'

const Avatar = ({userData ,handleVisibility ,visibility , setVisibility}) => {
    const navigate = useNavigate()
    
    const logOut = ()=>{
        sessionStorage.clear()
        navigate(0)
        navigate("/register")
    }
    console.log(userData.role.id)
return (
    <>
        <div className="container-1" key={userData.name}>
        <span onClick={handleVisibility} className='close-menu'><i className="fa fa-times" aria-hidden="true"></i></span>
            <div className='visibility-role'>
                {
                    userData.role.id === 2 ? <span className='route-admin'><Link to="/administracion">Administrador |</Link> </span> : ""
                }
            </div>
            <div className={userData.role.id === 2 ? 'iniciales-admin' : 'Iniciales'}>
                <span>{`${userData.name.substring(0, 1)}${userData.lastName.substring(0, 1)}`}</span>
            </div>
            <p className='P-in'>
                <span className='Nombre-ctm'>Hola,</span>
            <Link onClick={logOut}>
                <i className="fa fa-times" id="closeAvatar" aria-hidden="true"></i>
            </Link>
                <br />            
                {userData.name} {userData.lastName}
            </p>
        </div>
    </>
)
}

export default Avatar