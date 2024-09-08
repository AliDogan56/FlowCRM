
import React from 'react';
import { Menubar } from 'primereact/menubar';
import logo from '../../../assets/images/logo.svg';
import { Button } from 'primereact/button';



const NavBar = () => {

    const items = [
        {
            label: 'Home',
            icon: 'pi pi-home',
            url: '/'
        },
        {
            label: 'System Owners',
            icon: 'pi pi-star',
            url: '/user/systemowner/list'

        },
        {
            label: 'System Admins',
            icon: 'pi pi-box',
            url: '/user/systemadmin/list'

        },
        {
            label: 'Customers',
            icon: 'pi pi-user',
            url: '/user/customer/list'

        }
    ];

    const start = <img alt="logo" src={logo} height="40" className="mr-2"></img>;

    function logout() {
        console.log("logout")
    }

    const end = (
        <div className="flex align-items-center gap-2">
            <Button icon="pi pi-sign-out" severity="danger" onClick={() => logout()} />
        </div>
    );

    return (
        <div className="card">
            <Menubar model={items} start={start} end={end} />
        </div>
    )
}
export default NavBar
        