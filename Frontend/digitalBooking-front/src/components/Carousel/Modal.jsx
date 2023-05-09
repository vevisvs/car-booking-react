import Slideshow from "./Slider/Slideshow"
import "./Modal.css"

const Modal = ({setModalSlider, images}) => {

    const cerrarModal = () => {
        setModalSlider(false)
    }

    return (
        <div className="ctn-modal">
            <div className="close-modal-icon">
                <span className="material-symbols-outlined" onClick={cerrarModal}>close</span> 
            </div>
            <section className="ctn-slider">
                <Slideshow images={images} />
            </section>
        </div>
    )
}

export default Modal