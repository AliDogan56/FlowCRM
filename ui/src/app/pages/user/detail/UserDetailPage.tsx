import {useEffect, useState} from "react";
import userService from "../../../services/user/UserService";
import {CustomerUserModel} from "../../../model/user/CustomerUserModel";
import {Button, Col, Form, Row} from 'react-bootstrap';
import Container from "react-bootstrap/Container";
import {useNavigate} from "react-router-dom";
import {UserRole} from "../../../model/user/AppUserModel";


const UserDetailPage = () => {
    const url = new URL(window.location.href);
    const pathSegments = url.pathname.split("/");
    const id = Number(pathSegments[pathSegments.length - 1]);
    const userType = pathSegments[2];
    const operationType = pathSegments[3];
    const [selectedUser, setSelectedUser] = useState<CustomerUserModel>({
        role: userType === 'customer' ? UserRole.Customer : (userType === 'systemowner' ? UserRole.SystemOwner : UserRole.SystemAdmin),
    });
    const navigate = useNavigate();


    useEffect(() => {

        switch (operationType) {
            case 'detail':
                userService.getUser(userType, id).then((res) => {
                    if (id) {
                        setSelectedUser(res);
                    }
                }, (error) => {

                });
        }
    }, [userType]);

    const handleChange = (e: any) => {
        e.preventDefault();
        const {name, value} = e.target;
        setSelectedUser(prevState => ({
            ...prevState,
            [name]: value
        }));

    };

    const handleSubmit = (e: any) => {
        e.preventDefault();

        if (id) {
            userService.updateUser(userType, selectedUser).then((res) => {
                navigate(-1);

            }, (error) => {

            });

        } else {
            userService.createUser(userType, selectedUser).then((res) => {
                navigate(-1);

            }, (error) => {

            });

        }


    };


    return <>
        <Container>
            <h2>{id ? 'Edit User' : 'Create User'}</h2>
            <Form onSubmit={handleSubmit}>
                <Form.Group>
                    <Form.Label>Username</Form.Label>
                    <Form.Control
                        type="text"
                        name="username"
                        value={selectedUser?.username}
                        onChange={handleChange}
                    />
                </Form.Group>

                <Form.Group>
                    <Form.Label>First Name</Form.Label>
                    <Form.Control
                        type="text"
                        name="firstName"
                        value={selectedUser?.firstName}
                        onChange={handleChange}
                    />
                </Form.Group>

                <Form.Group>
                    <Form.Label>Last Name</Form.Label>
                    <Form.Control
                        type="text"
                        name="lastName"
                        value={selectedUser?.lastName}
                        onChange={handleChange}
                    />
                </Form.Group>

                <Form.Group>
                    <Form.Label>Email</Form.Label>
                    <Form.Control
                        type="email"
                        name="email"
                        value={selectedUser?.email}
                        onChange={handleChange}
                    />
                </Form.Group>

                <Form.Group>
                    <Form.Label>Region</Form.Label>
                    <Form.Control
                        type="text"
                        name="region"
                        value={selectedUser?.region}
                        onChange={handleChange}
                    />
                </Form.Group>

                {!id &&
                    <Form.Group>
                        <Form.Label htmlFor="inputPassword5">Password</Form.Label>
                        <Form.Control
                            type="password"
                            name="password"
                            id="inputPassword5"
                            aria-describedby="passwordHelpBlock"
                            value={selectedUser?.password}
                            onChange={handleChange}
                        />
                        <Form.Text id="passwordHelpBlock" muted>
                            Your password must be 8-20 characters long, contain letters and numbers,
                            and must not contain spaces, special characters, or emoji.
                        </Form.Text>
                    </Form.Group>
                }

                <div className="mt-4 text-end">
                    <Row>
                        <Col>
                            <Button variant="primary" type="submit">
                                Save Changes
                            </Button>
                        </Col>
                    </Row>
                </div>

            </Form>

        </Container>
    </>

}
export default UserDetailPage