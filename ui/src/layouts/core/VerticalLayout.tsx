// ** React Imports
import {useState, useEffect} from 'react'
import {useLocation} from 'react-router-dom'

// ** Redux

// ** Third Party Components
import classnames from 'classnames'
import {Navbar} from 'reactstrap'

// ** Custom Components
import NavbarComponent from './components/navbar'
import SidebarComponent from './components/menu/vertical-menu'

// ** Styles
import '../../assets/scss/main/base/core/menu/menu-types/vertical-menu.scss'
import '../../assets/scss/main/base/core/menu/menu-types/vertical-overlay-menu.scss'
import Container from "react-bootstrap/Container";
import Card from "react-bootstrap/Card";


const VerticalLayout = ({children, menu, routerProps, currentActiveItem}: any) => {


    const [windowWidth, setWindowWidth] = useState(window.innerWidth)
    const [menuVisibility, setMenuVisibility] = useState(false)
    const [menuCollapsed, setMenuCollapsed] = useState(true)
    // ** Vars
    const location = useLocation()

    // ** Update Window Width
    useEffect(() => {
        const handleResize = () => setWindowWidth(window.innerWidth)
        window.addEventListener('resize', handleResize)

        return () => window.removeEventListener('resize', handleResize)
    }, [])

    // ** Hide Menu on Route Change (for mobile view)
    useEffect(() => {
        if (menuVisibility && windowWidth < 1200) {
            setMenuVisibility(false)
        }
    }, [location, menuVisibility, windowWidth])
// ** Layout Rendering Logic
    const isMobileView = windowWidth < 1200
    const wrapperClasses = classnames(
        'wrapper vertical-layout navbar-floating footer-static',
        {
            'vertical-menu-modern': !isMobileView,
            'menu-collapsed': menuCollapsed && !isMobileView,
            'menu-expanded': !menuCollapsed && !isMobileView,
            'vertical-overlay-menu': isMobileView,
            'menu-hide': !menuVisibility && isMobileView,
            'menu-open': menuVisibility && isMobileView
        }
    )

    return (
        <div className={wrapperClasses} {...(menuVisibility ? {'data-col': '1-column'} : {})}>

            {/* Sidebar */}
            {!menuVisibility && (
                <SidebarComponent
                    skin="light"
                    menu={menu}
                    menuCollapsed={menuCollapsed}
                    setMenuCollapsed={setMenuCollapsed}
                    menuVisibility={menuVisibility}
                    setMenuVisibility={setMenuVisibility}
                    routerProps={routerProps}
                    currentActiveItem={currentActiveItem}
                />
            )}

            {/* Navbar */}
            <Navbar expand="lg"
                    color={'dark'}
                    className="header-navbar align-items-center">
                <div className="navbar-container d-flex justify-content-between">
                    <div></div>
                    <div className="ms-auto">
                        <NavbarComponent/>
                    </div>
                </div>
            </Navbar>

            {/* Page Content */}
            {children}

            <div
                className={classnames('sidenav-overlay', {
                    show: menuVisibility
                })}
                onClick={() => setMenuVisibility(false)}
            ></div>

        </div>
    )
}

export default VerticalLayout
