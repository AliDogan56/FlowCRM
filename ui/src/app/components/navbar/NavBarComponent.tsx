import React from 'react';
import logo from '../../../assets/images/logo.svg';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Button from 'react-bootstrap/Button';
import Navbar from 'react-bootstrap/Navbar';
import Dropdown from 'react-bootstrap/Dropdown';
import DropdownButton from 'react-bootstrap/DropdownButton';
import {ButtonGroup} from "react-bootstrap";
import {House, Person, Box, Command, BoxArrowLeft, ArrowLeft} from 'react-bootstrap-icons';
import authService from "../../services/auth/AuthService";
import {useNavigate} from "react-router-dom";


const NavBarComponent = () => {

    const isOwnerOrAdmin = authService.isSystemAdminOrOwner();
    const navigate = useNavigate();


    function logout() {
        authService.logout();
    }


    return (
        <Navbar expand="lg" className="bg-body-tertiary" style={{marginBottom: "10px"}}>
            <Container fluid>
                <Navbar.Brand>
                    <img alt="logo" src={logo} height="40" className="mr-2"></img>
                </Navbar.Brand>
                <Navbar.Toggle aria-controls="navbarScroll"/>
                <Navbar.Collapse id="navbarScroll">
                    <Nav
                        className="me-auto my-2 my-lg-0"
                        style={{maxHeight: '100px'}}
                        navbarScroll
                    >
                        <Nav.Link href="/">
                            <div style={{display: "inline-flex", alignItems: "center"}}>
                                <House className="header-icon" color="royalblue"/>
                                <span style={{marginLeft: "5px"}}>{'Home'}</span>
                            </div>
                        </Nav.Link>
                        {isOwnerOrAdmin &&
                            <Nav.Link href="/user/systemowner/list">
                                <div style={{display: "inline-flex", alignItems: "center"}}>
                                    <Person className="header-icon" color="royalblue"/>
                                    <span style={{marginLeft: "5px"}}>{'System Owner'}</span>
                                </div>
                            </Nav.Link>
                        }
                        {isOwnerOrAdmin &&
                            <Nav.Link href="/user/systemadmin/list">
                                <div style={{display: "inline-flex", alignItems: "center"}}>
                                    <Box className="header-icon" color="royalblue"/>
                                    <span style={{marginLeft: "5px"}}>{'System Admins'}</span>
                                </div>
                            </Nav.Link>
                        }
                        <Nav.Link href="/user/customer/list">
                            <div style={{display: "inline-flex", alignItems: "center"}}>
                                <Command className="header-icon" color="royalblue"/>
                                <span style={{marginLeft: "5px"}}>{'Customers'}</span>
                            </div>
                        </Nav.Link>
                    </Nav>
                    <div style={{display: "flex", alignItems: "center"}}>
                        <Button variant="warning"
                                onClick={() => navigate(-1)}
                                style={{marginRight: "10px", display: "inline-flex", alignItems: "center"}}>
                            <ArrowLeft style={{marginRight: "5px"}}/>
                            Back
                        </Button>

                        <DropdownButton
                            as={ButtonGroup}
                            drop={'start'}
                            variant="danger"
                            title=""
                        >
                            <Dropdown.Item eventKey="1" onClick={logout}>
                                <div style={{display: "inline-flex", alignItems: "center"}}>
                                    <BoxArrowLeft className="header-icon"/>
                                    <span style={{marginLeft: "5px"}}>Logout</span>
                                </div>
                            </Dropdown.Item>
                        </DropdownButton>
                    </div>


                </Navbar.Collapse>

            </Container>
        </Navbar>
    )
}
export default NavBarComponent
        