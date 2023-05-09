import React from 'react'
import './Politicas.css'

const Politicas = ({products}) => {

    return (
            <section>
                <h3 className='Section-nsc'>Qué tenés que saber</h3>
                <hr />
                <div className='conteiner-nsc'>
                    <article>
                    <h4 className='Normas'> Normas </h4>
                    <p>{products?.rules}</p>
                    </article>
                    <article>
                    <h4 className='security'> Seguridad </h4>
                    <p>{products?.securityRules}</p>
                    </article>
                    <article>
                    <h4 className='cancelation'> Cancelación </h4>
                    <p>{products?.cancellation}</p>
                    </article>
                </div>
            </section>
    )
}

export default Politicas