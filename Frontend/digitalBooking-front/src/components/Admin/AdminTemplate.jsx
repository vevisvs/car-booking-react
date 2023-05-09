import React from 'react'
import Headlong from './Headlong'
import AddProduct from '../Admin/AddProduct'
import './Admin.css'

const AdminTemplate = () => {
    return (
        <div className='admin-template'>
            <Headlong />
            <AddProduct />
        </div>
    )
}

export default AdminTemplate