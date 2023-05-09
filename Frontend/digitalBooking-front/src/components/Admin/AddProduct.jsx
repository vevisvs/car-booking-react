import React, { useEffect, useRef } from 'react'
import { useNavigate } from 'react-router-dom'
import { useState } from 'react'
import Loader from '../Loader/Loader'
import axios from 'axios'
import ImageList from './ImageList'

const AddProduct = () => {
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false)

    //states:
    const [categoryList, setCategoryList] = useState();
    const [cityList, setCityList] = useState();
    const [caracteristicas, setCaracteristicas] = useState([])
    const [handleCategory, setHandleCategory] = useState([]);
    const [handleModel, setHandleModel] = useState("");
    const [handleCity, setHandleCity] = useState();
    const [handleBrand, setHandleBrand] = useState("");
    const [handleScore, setHandleScore] = useState();
    const [handleDescription, setHandleDescription] = useState();
    const [arrayImages, setArrayImages] = useState([]);

    const [handleRules, setHandleRules] = useState("");
    const [handleSecurity, setHandleSecurity] = useState("");
    const [handleCancelation, setHandleCancelation] = useState("");

    const [wrongMessage, setWrongMessage] = useState(); 
    const [imageError, setimageError] = useState("");
    const imagenRef = useRef(null);

    useEffect(() => {
        async function fetching(){
            try {
                const [categoryResponse, cityResponse] = await Promise.all([
                    axios.get('http://3.141.42.20:8080/categorie/list'),
                    axios.get('http://3.141.42.20:8080/city/list'),
                ]);
                setCategoryList(categoryResponse.data);
                setCityList(cityResponse.data);
            } catch (error) {
                console.log(error);
            }
        }
        fetching();
    }, []);


    const handleCaracteristics = (e) => {
        if(e.target.checked){
            setCaracteristicas([...caracteristicas, Number(e.target.value)])
        } else {
            setCaracteristicas(caracteristicas.filter((elem) => elem !== Number(e.target.value))) 
        }
    };

    //determino el máximo de urls que tendrá el array de imagenes:
    const disabledInput = arrayImages.length === 5;
    
    // se agrega un objeto con nombre y url al array de imagenes:
    const handleAddImages = (e) => {
        e.preventDefault();
        let urlImage = e.target.form.elements.imageUrl.value;
        const urlPattern = new RegExp('^https?://(?:[a-zA-Z0-9\\-]+\\.)+[a-zA-Z]{2,}(?:/[^/]*)*$'); //el input solo aceptara urls
        if(urlPattern.test(urlImage)){
            setArrayImages([
            ...arrayImages,
            {
                name: "imagen",
                url: urlImage,
            },
        ]);
            e.target.form.elements.imageUrl.value = ''; //blanqueo el input
            setimageError("");
        } else {
            setimageError("Por favor, ingresa una URL válida");
        }
        
    }

    const handleDeleteImage = (index) => {
        setArrayImages(arrayImages.filter((image, i) => i !== index));
    }

    //Manejar la ciudad seleccionada, parsear a valor numerico para enviar a API:
    const handleCitySelected = (e) => {
        const cityId = parseInt(e.target.value);
        setHandleCity(cityId)
    }

    // parsear a valor numerico la categoria, para enviar a API:
    const handleCategorySelected = (e) => {
        const categoryId = parseInt(e.target.value);
        setHandleCategory(categoryId)
    }

    const handleScoreSelected = (e) => {
        const inputScore = e.target.value; //capturo el valor del input
        const longValue = parseFloat(inputScore, 10); //lo convierto a tipo Long, parseFloat para conservar parte decimal
        setHandleScore(longValue)
    }

    //envio de formulario:
    const createProduct = () => {
    if( handleModel && handleCategory && handleBrand && handleScore && handleCity
        && handleDescription && arrayImages && caracteristicas && handleRules && handleSecurity && handleCancelation ){ 
        const token = sessionStorage.getItem('token');
        // console.log(token)
        setLoading(true);
        axios.post('http://3.141.42.20:8080/model/add', {
            name: handleModel,
            description: handleDescription,
            brand: handleBrand,
            score: handleScore,
            images: arrayImages,
            characteristics: caracteristicas,
            id_City: handleCity,
            id_Categorie: handleCategory,
            rules: handleRules,
            securityRules: handleSecurity,
            cancellation: handleCancelation
        }, {
            headers: {
            Authorization: 'Bearer ' + token
            },
        })
            .then(res => {
            console.log('producto creado correctamente', res.data); 
            if(res.status === 200 || res.status === 201){
                setLoading(false)
                navigate("/administracion/confirmacion")
            }
        })
        .catch(error => setWrongMessage('Hubo un error al crear el producto', error));
        } else {
        console.log("faltan datos para completar la creacion del producto")
        setWrongMessage("Faltan datos para completar la creación del producto.");
        }
    }
    return (
        <div className='container-admin'>
            <h2>Agregar vehículo</h2>
            <div className='ctn-add-product'>
                <form className='add-form-product'>
                    <div className='section-name-category'>
                        <div className='model-car'>
                            <label>Modelo del vehículo</label>
                            <input className='model-input' type="text" placeholder='Modelo del vehiculo'
                            onChange={(e) => setHandleModel(e.target.value)}></input>
                        </div>
                        <div className='categorie-car'>
                            <label>Categoría</label>
                            <select defaultValue={1} className='cat-car-select' 
                                onChange={handleCategorySelected}>
                                <option value={1} disabled hidden>Selecciona una categoria</option>
                                { categoryList &&
                                    <>
                                        {categoryList.map(cat => {
                                            return <option key={cat.id} value={cat.id}>{cat.name}</option>
                                        })}
                                    </>
                                }
                            </select>
                        </div>
                    </div>
                    <div className='section-brand'>
                        <div className='data-brand'>
                            <label>Marca</label>
                            <input className='brand-input' type="text" placeholder='Marca' 
                                onChange={(e) => setHandleBrand(e.target.value)}></input>
                        </div>
                        <div className='data-score'>
                            <label>Puntuación</label>
                            <input className='score-input' type="text" placeholder='9.0' 
                                onChange={handleScoreSelected}></input>
                        </div>
                        <div className='data-city'>
                            <label>Ciudad</label>
                            <select defaultValue={2} className='city-options' 
                                onChange={handleCitySelected}>
                                <option value={2} disabled hidden>Selecciona una ciudad</option>
                                    { cityList &&
                                        <>
                                            {cityList.map(city => {
                                                return <option key={city.id} value={city.id}>{city.name}</option>
                                            })}
                                        </>
                                    }
                            </select>
                        </div>
                    </div>
                    <div className='description-product'>
                        <label>Descripción</label>
                        <textarea className='description-text' placeholder='Escribí aquí'
                        onChange={(e) => setHandleDescription(e.target.value)}></textarea>
                    </div>
                    <h2 className='atributos-title'>Agregar caracteristicas</h2>
                    <div className='ctn-atributos'>
                        <div class="form-check">
                            <input 
                                type="checkbox" 
                                class="form-check-input" 
                                id="acondicionado" 
                                name="acondicionado"
                                value={1}
                                onChange={handleCaracteristics}
                                />
                            <label class="form-check-label" >Aire acondicionado</label>
                        </div>
                        <div class="form-check">
                            <input 
                                type="checkbox" 
                                className="form-check-input"   
                                id="equipaje" 
                                name="equipaje"
                                value={2}
                                onChange={handleCaracteristics}/>
                            <label class="form-check-label" >Equipaje</label>
                        </div>
                        <div class="form-check">
                            <input 
                                type="checkbox" 
                                className="form-check-input"   
                                id="asistencia" 
                                name="asistencia"
                                value={3}
                                onChange={handleCaracteristics}/>
                            <label class="form-check-label" >Asistencia al viajero</label>
                        </div>
                        <div class="form-check">
                            <input 
                                type="checkbox" 
                                className="form-check-input"   
                                id="gps" 
                                name="gps"
                                value={4}
                                onChange={handleCaracteristics}/>
                            <label class="form-check-label" >GPS</label>
                        </div>
                        <div class="form-check">
                            <input 
                                type="checkbox" 
                                className="form-check-input"   
                                id="vtv" 
                                name="vtv"
                                value={5}
                                onChange={handleCaracteristics}/>
                            <label class="form-check-label" >VTV</label>
                        </div>
                    </div>
                    <h2 className='politicas-title'>Políticas del vehículo</h2>
                    <div className='ctn-politics'>
                        <div className='rules'>
                            <h3>Normas</h3>
                            <div className='text-rules'>
                                <label>Descripcion</label>
                                <textarea className='commments' placeholder='Escribí aquí'
                                    onChange={(e) => setHandleRules(e.target.value)}></textarea>
                            </div>                       
                        </div>
                        <div className='security'>
                            <h3>Salud y seguridad</h3>
                            <div className='text-security'>
                                <label>Descripcion</label>
                                <textarea className='commments' placeholder='Escribí aquí'
                                    onChange={(e) => setHandleSecurity(e.target.value)}></textarea>
                            </div>
                            
                        </div>
                        <div className='cancelation'>
                            <h3>Políticas de cancelación</h3>
                            <div className='text-cancelation'>
                                <label>Descripcion</label>
                                <textarea className='commments' placeholder='Escribí aquí'
                                    onChange={(e) => setHandleCancelation(e.target.value)}></textarea>
                            </div>
                            
                        </div>
                    </div>
                    <h2 className='update-images-title'>Cargar imágenes</h2>
                    {
                            imageError && <p style={{color: "red", display: "flex", justifyContent:"center"}}>{imageError}</p>
                    }
                    <div className='ctn-update-images'>
                        
                        <div className='ctn-inserting'>
                            <input className='route-image' type="text" 
                                name="imageUrl"
                                id="imageUrl"
                                placeholder='Insertar https://'
                                ref={imagenRef}></input>
                            <button className='add-image-button' 
                                type="button"
                                onClick={handleAddImages}
                                disabled={disabledInput}>✙</button>
                        </div>
                    </div>
                    <ImageList images={arrayImages} handleDeleteImage={handleDeleteImage}/>
                    <div className='ctn-image-message'>
                        {disabledInput && (
                            <span className='image-message'>
                                Haz alcanzado el máximo número de imágenes para agregar.
                            </span>
                        )}
                    </div>
                </form>
                {
                    wrongMessage && 
                        <span className='error-message'>{wrongMessage}</span>
                }
                <div className={`ctn-button-form-product ${loading && 'button-hidden'}`}>
                    <button type="submit" className='create-product-button' onClick={createProduct}>Crear</button>
                </div>
                {
                    loading && <span className='waiting'><Loader /></span>  
                }
            </div>
        </div>
    )
}
export default AddProduct