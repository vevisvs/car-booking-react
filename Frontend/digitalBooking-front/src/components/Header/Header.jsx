import React,{useContext, useEffect, useState} from 'react'
import "./Header.css"
import logo from '../../assets/logo1.png'
import Avatar from './Avatar/Avatar'
import { Link, useLocation } from 'react-router-dom'
import { UserContext } from '../Context/UserContext'

const Header = () => {
    const {user,getUser} = useContext(UserContext)
    const [visibility, setVisibility] = useState(false)
    const handleVisibility = ()=> setVisibility(visibility=>!visibility)
    const toggleVisibility = visibility ? "visibility" : ""

    const location = useLocation().pathname

    useEffect(() => {
        getUser()
    }, [])
    
    const handleLocation = (location)=>{
        if (location==="/register") {
            return <Link to="/login"><li onClick={handleVisibility} className='nav-item'>Iniciar Sesion</li></Link> 
        } else if (location==="/login") {
            return <Link to="/register"><li onClick={handleVisibility} className='nav-item'>Crear Cuenta</li></Link>
        }else {
            return (<><Link to="/register"><li onClick={handleVisibility} className='nav-item'>Crear Cuenta</li></Link>
            <Link to="/login"><li onClick={handleVisibility} className='nav-item'>Iniciar Sesion</li></Link></>)
        }
    }

    return (
        <header>
            <Link to="/">
                <div className='logo'>
                    <img src={logo} alt="Digital Booking Logo"/>
                    <span className='span-logo'>Sentite como en tu hogar</span>
                </div>
            </Link>
            <span onClick={handleVisibility} className='open-menu'><i className="fa fa-bars" aria-hidden="true"></i></span>
            <nav className={toggleVisibility} id='nav'>
                    {
                        (user)? <Avatar userData={user} visibility={visibility} handleVisibility={handleVisibility} setVisibility={setVisibility}/> :
                        <>
                        <div className='close-ctn'>
                            <span onClick={handleVisibility} className='close-menu'><i className="fa fa-times" aria-hidden="true"></i></span>
                            <span className='span-menu'>MENU</span>
                        </div>
                        
                        <ul className='nav-list'>
                            {
                                handleLocation(location)
                            }
                            
                        </ul>
                        
                        <div className='menu-media'>
                            <a href='https://www.facebook.com/'>
                                    <i className="fa fa-facebook-official" aria-hidden="true"></i>
                            </a>
                            <a href='https://www.linkedin.com/'>
                                    <i className="fa fa-linkedin" aria-hidden="true"></i>
                            </a>
                            <a href='https://twitter.com/'>
                                    <i className="fa fa-twitter" aria-hidden="true"></i>
                            </a>
                            <a href='https://www.instagram.com/'>
                                <i className="fa fa-instagram" aria-hidden="true"></i>
                            </a>
                        </div>
                        </>
                    }
            </nav>
        </header>
)
}

export default Header