// ** React Imports
import {Fragment} from 'react'

// ** Dropdowns Imports
import UserDropdown from './UserDropdown'


// ** Third Party Components

const ThemeNavbar = () => {
    return (
        <Fragment>
            <ul className='nav navbar-nav align-items-center ml-auto'>
                <UserDropdown/>
            </ul>
        </Fragment>
    )
}

export default ThemeNavbar
