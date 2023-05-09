import React, { useState } from 'react'
import './Slideshow.css'

const Slideshow = ({images}) => {
    const [slidePosition, setSlidePosition] = useState(0)

    const slides = {
        backgroundImage: `url(${images[slidePosition].url})`
    }

    const previus = () => {
        const firstPosition = slidePosition === 0;
        const newPosition = firstPosition ? images.length - 1 : slidePosition - 1;
        setSlidePosition(newPosition)
        console.log("anterior...")
    }

    const next = () => {
        const lastPosition = slidePosition === images.length - 1;
        const newPosition = lastPosition ? 0 : slidePosition + 1;
        setSlidePosition(newPosition)
        console.log("proxima...")
    }

    return (
        <>
            {images && images.length >= 5 ? (
        <div className="ctn-slideshow">
            <div className="rows-slideshow">
                <div className="row-previus" onClick={previus}>
                    ❮
                </div>
                <div className="row-next" onClick={next}>
                    ❯
                </div>
            </div>
            <img className="image-slideshow" src={images[slidePosition].url} />
            </div>) : null}
        </>
    )
}

export default Slideshow