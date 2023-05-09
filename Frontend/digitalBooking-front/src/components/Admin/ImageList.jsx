import React from 'react'

const ImageList = ({images, handleDeleteImage}) => {
    return (
    <div className='ctn-list'>
        {images.map((image, index) => (
        <div key={index} className='ctn-list-images'>
            <img className='model-image' src={image.url} alt={`Imagen ${index+1}`}></img>
            <button className='delete-image-btn' onClick={() => handleDeleteImage(index)}><i class="fa fa-trash" aria-hidden="true"></i></button>
        </div>
        ))}
    </div>
    )
}

export default ImageList