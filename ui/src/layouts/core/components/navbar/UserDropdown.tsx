// ** React Imports
import {useEffect, useState} from 'react'
import {Link} from 'react-router-dom'
import {UncontrolledDropdown, DropdownMenu, DropdownToggle, DropdownItem} from 'reactstrap'
import {User, Power} from 'react-feather'

// ** Default Avatar Image
import defaultAvatar from '../../../../assets/images/avatars/avatar-s-11.jpg'
import {isUserLoggedIn} from "../../../../auth/utils";
import Avatar from "../../../../components/avatar";
import authService from "../../../../app/services/auth/AuthService";

const UserDropdown = () => {


    // ** State
    const [userData, setUserData] = useState({avatar: undefined, role: undefined, username: undefined});

    //** ComponentDidMount
    useEffect(() => {
        if (isUserLoggedIn() !== null) {
            const userDataString = localStorage.getItem('userData') || '{}';
            const userData = JSON.parse(userDataString);
            setUserData(userData);
        }
    }, [])

    //** Vars
    const userAvatar = (userData && userData.avatar) || defaultAvatar
    return (
        <UncontrolledDropdown tag='li' className='dropdown-user nav-item'>
            <DropdownToggle href='/' tag='a' className='nav-link dropdown-user-link' onClick={e => e.preventDefault()}>
                <div className='user-nav d-sm-flex d-none'>
                    <span
                        className='user-name font-weight-bold'>{authService.getCurrentUserName()}</span>
                    <span className='user-status'>{(userData && userData.role) || 'Admin'}</span>
                </div>
                <Avatar img={userAvatar} imgHeight='40' imgWidth='40' status='online'/>
            </DropdownToggle>
            <DropdownMenu right>
                <DropdownItem tag={Link} onClick={
                    () => {
                        authService.logout()
                    }
                }>
                    <Power size={14} className='mr-75'/>
                    <span className='align-middle'>Logout</span>
                </DropdownItem>
            </DropdownMenu>
        </UncontrolledDropdown>
    )
}

export default UserDropdown
