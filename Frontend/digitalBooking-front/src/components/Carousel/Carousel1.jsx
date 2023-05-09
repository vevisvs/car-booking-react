import React from 'react'
import 'react-responsive-carousel/lib/styles/carousel.min.css';
import { useState, useEffect } from 'react';
import './Carousel1.css'
import Modal from './Modal';
import Loader from '../Loader/Loader';
import Slideshow from './Slider/Slideshow';

const Carousel1 = ({images}) => {    

    const [modalSlide, setModalSlider] = useState(false);
    const [spinnerLoader, setSpinnerLoader] = useState(true);
    const [isMobile, setIsMobile] = useState([window.innerWidth <= 768]);

    const handleModalSlide = () => {
        setModalSlider(true)
    }

    useEffect(() => {
        const handleResize = () => {
            setIsMobile(window.innerWidth <= 768);
        }
        window.addEventListener('resize', handleResize);
        handleResize();
        return () => window.removeEventListener('resize', handleResize);
    }, []);

    if (images && images.length >= 5) {
        return (
            <>
                {isMobile ? 
                    (<Slideshow images={images}/>
                    ) : ( 
                    <>
                        <section className="Contenedor-Galeria">
                            <div className="Container-pds">
                            <div className='first-image'>
                                <img src={images[0]?.url} alt={images[0]?.name} />
                            </div>
                            <div className='secondary-images'>
                                <div className='top-images'>
                                <img src={images[1]?.url} alt={images[1]?.name} />
                                <img src={images[2]?.url} alt={images[2]?.name} />
                                </div>
                                <div className='bottom-images'>
                                <img src={images[3]?.url} alt={images[3]?.name} />
                                <img src={images[4]?.url} alt={images[4]?.name} />
                                <div className="VerMas" onClick={handleModalSlide}>Ver más</div>
                                </div>
                            </div>
                            </div>
                        </section>
                        <section className='modal-section'>
                            {modalSlide && <Modal setModalSlider={setModalSlider} images={images}/>} 
                        </section>
                    </>
                )}
            </>
        );
    } else {
        return (
                <div className='ctn-loader-carousel'>
                    <Loader />
                </div>
            );
    }
}

export default Carousel1