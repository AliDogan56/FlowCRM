import {useEffect, useState} from "react";
import userService from "../../../services/user/UserService";
import {CustomerUserModel} from "../../../model/user/CustomerUserModel";
import {useNavigate} from "react-router-dom";
import {UserRole} from "../../../model/user/AppUserModel";
import {CardTitle, CardBody, FormGroup, Button, Label, Input, FormText, Col, Row, Card, Form} from "reactstrap";
import {ArrowLeft} from "react-bootstrap-icons";


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
    }, [id, operationType, userType]);

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
        <Card>
            <Row>
                <Col>
                    <CardTitle>
                        <h2>{id ? 'Edit User' : 'Create User'}</h2>
                    </CardTitle>
                </Col>
                <Col>
                    <div className={'d-flex justify-content-end'}>
                        <Button color="warning" onClick={() => {
                            navigate(-1);
                        }}>
                            <ArrowLeft></ArrowLeft>
                        </Button>
                    </div>

                </Col>
            </Row>
            <CardBody>
                <Form onSubmit={handleSubmit}>
                    <Row>
                        <Col>
                            <FormGroup>
                                <Label>Username</Label>
                                <Input
                                    type="text"
                                    name="username"
                                    value={selectedUser?.username}
                                    onChange={handleChange}
                                />
                            </FormGroup>
                        </Col>
                        <Col>
                            <FormGroup>
                                <Label>First Name</Label>
                                <Input
                                    type="text"
                                    name="firstName"
                                    value={selectedUser?.firstName}
                                    onChange={handleChange}
                                />
                            </FormGroup>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <FormGroup>
                                <Label>Last Name</Label>
                                <Input
                                    type="text"
                                    name="lastName"
                                    value={selectedUser?.lastName}
                                    onChange={handleChange}
                                />
                            </FormGroup>
                        </Col>
                        <Col>
                            <FormGroup>
                                <Label>Email</Label>
                                <Input
                                    type="email"
                                    name="email"
                                    value={selectedUser?.email}
                                    onChange={handleChange}
                                />
                            </FormGroup>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <FormGroup>
                                <Label>Region</Label>
                                <Input
                                    type="text"
                                    name="region"
                                    value={selectedUser?.region}
                                    onChange={handleChange}
                                />
                            </FormGroup>
                        </Col>
                        <Col>
                            {!id &&
                                <FormGroup>
                                    <Label htmlFor="inputPassword5">Password</Label>
                                    <Input
                                        type="password"
                                        name="password"
                                        id="inputPassword5"
                                        aria-describedby="passwordHelpBlock"
                                        value={selectedUser?.password}
                                        onChange={handleChange}
                                    />
                                    <FormText id="passwordHelpBlock" muted>
                                        Your password must be 8-20 characters long, contain letters and numbers,
                                        and must not contain spaces, special characters, or emoji.
                                    </FormText>
                                </FormGroup>
                            }
                        </Col>

                    </Row>
                    <Row>
                        <Col>
                            <FormGroup className={'text-end'}>
                                <Button color="primary" type="submit">
                                    Save Changes
                                </Button>
                            </FormGroup>
                        </Col>
                    </Row>

                </Form>

            </CardBody>
        </Card>
    </>

}
export default UserDetailPage