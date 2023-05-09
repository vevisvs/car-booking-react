import React,{ useState , useEffect} from 'react'
import './TemplateProduct.css'
import Booking from '../Booking/Booking'
import BookingButtom from '../Booking/BookingButtom'
import { Link,useParams } from 'react-router-dom'
import Carousel1 from '../Carousel/Carousel1'
import MapView from '../MapViewer/MapView'
import Politicas from '../Politicas/Politicas'
import '../Politicas/Politicas.css'
import axios from 'axios'

const TemplateProduct = () => {
    const [products, setProducts] = useState([])
    const {id} = useParams()
    const [caracteristicas, setCaracteristicas] = useState();
    
    const getProducts =()=>{
        axios.get(`http://3.141.42.20:8080/model/search/${id}`)
        .then(
            res=>{
                setProducts(res.data); //recibo toda la data del producto
                const characteristicsOfModel = res.data?.characteristics || []; //accedo a la propiedad caracteristicas
                setCaracteristicas(characteristicsOfModel); //guardo las caracteristicas en el state
            }
        )
    }

    useEffect(()=>{
        getProducts()
        window.scroll(0,0)
    },[])

return (
    <div className='conteiner-Tp'>
        <div className='nameProduct'>
            <h2>{products?.brand}<p>{products?.name}</p></h2>
            <Link to='/'>
            <span><i className="fa fa-arrow-left" aria-hidden="true"></i></span>
            </Link>
        </div>
        <div className='cityDescription'>
            <div className='ubication'>
                <i className="fa fa-map-marker" aria-hidden="true"></i>
                <p>{products?.city?.name} {products?.city?.country}<br/> A 940m del centro</p> 
            </div>
            <div className='image-icons'>
                <i className="fa fa-share-alt" aria-hidden="true"></i>
                <i className="fa fa-heart-o" aria-hidden="true"></i>
            </div>
        </div>
        <div className='imageProduct'>
            <Carousel1 images={products?.images}/>
        </div> 
        <div className='Title-Description'>
            <h3>Sobre el vehiculo</h3>
            <p>{products?.description}</p>
        </div>
        <div className='Ofers'>
            <h4 className='Ofers-h'>Caracteristicas</h4>
            <hr/>
            <div className='ofers-map'>
                {
                    caracteristicas && caracteristicas.map((c, index) => (
                        <div key={index}>
                            <i className={`fa ${c?.icon}`} aria-hidden="true"></i>                          
                            <h4>{c?.name}</h4>
                        </div>
                    ))
                }
            </div>
        </div>
        <section className='ctn-mapa'>
            <div className='ctn-mapa-title'>
                <h3 className='mapa-title'>¿Dónde vas a estar?</h3>
            </div>
            <hr />
            <div className='ctn-mapa-viewer'>
                <MapView products={products}/>
            </div>
        </section>  
        <Politicas products={products}/>
        <div className='calendar-section'>
            <h2>Fechas disponibles</h2>
            <div className='calendar-info'>
                <div className='ctn-calendar'>
                    <Booking/>
                </div>
                <div className='ctn-btn-calendar'>
                    <BookingButtom id={id}/>
                </div>
            </div>
        </div>
    </div>
)
}

export default TemplateProduct