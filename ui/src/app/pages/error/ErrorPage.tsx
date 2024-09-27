import React from 'react'
import {Button} from 'reactstrap'
import {Link} from 'react-router-dom'
import errorImg from '../../../assets/images/pages/error.svg'

import '../../../assets/scss/main/base/pages/page-misc.scss'
import {appRoutes} from "../../../routes";

const ErrorPage: React.FC = () => {
    return (
        <div className='misc-wrapper'>
            <a className='brand-logo' href={appRoutes.main}>
                <h2 className='brand-text text-primary ml-1'>FlowCRM</h2>
            </a>
            <div className='misc-inner p-2 p-sm-3'>
                <div className='w-100 text-center'>
                    <h2 className='mb-1'>Page Not Found 🕵🏻‍♀️</h2>
                    <p className='mb-2'>Oops! 😖 The requested URL was not found on this server.</p>
                    <Button tag={Link} to={appRoutes.main} color='primary' className='btn-sm-block mb-2'>
                        Back to home
                    </Button>
                    <img className='img-fluid' src={errorImg} alt='Not authorized page'/>
                </div>
            </div>
        </div>
    )
}

export default ErrorPage
