import {useEffect, useState} from "react";
import {CustomerUserModel, CustomerUserSearchDTO} from "../../../model/user/CustomerUserModel";
import userService from "../../../services/user/UserService";
import {toast} from "react-toastify";
import {SortOrder} from "../../../model/base/SortOrderDTO";
import authService from "../../../services/auth/AuthService";
import {appRoutes} from "../../../../routes";
import {useNavigate} from "react-router-dom";
import {CardTitle, FormGroup, Input, Label, Button, Form, Row, Col, CardBody, ModalBody, ModalFooter, Modal} from "reactstrap";
import DataTable from 'react-data-table-component'
import {Edit, Trash} from "react-feather";
import {ArrowLeft, PlusSquare} from "react-bootstrap-icons";


const UserListPage = () => {
    const userType = new URL(window.location.href).pathname.toString().split("/")[2];
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [region, setRegion] = useState('');
    const [email, setEmail] = useState('');
    const [userData, setUserData] = useState<CustomerUserModel[]>([]);
    const [searchData, setSearchData] = useState<CustomerUserSearchDTO>();
    const [sortOrder] = useState<SortOrder>({fieldName: "id", orderProperty: "id", orderType: "ASC"});
    const [page] = useState(0);
    const [perPage] = useState(10);
    const navigate = useNavigate();
    const [confirmDialogShown, setConfirmDialogShown] = useState<boolean>(false);
    const [selectedUser, setSelectedUser] = useState<CustomerUserModel>();

    const basicColumns = [
        {
            name: 'ID',
            selector: (row: CustomerUserModel) => row.id !== undefined ? row.id : 0,
        },
        {
            name: 'Username',
            selector: (row: CustomerUserModel) => row.username !== undefined ? row.username : '',
        },
        {
            name: 'First Name',
            selector: (row: CustomerUserModel) => row.firstName !== undefined ? row.firstName : '',
        },
        {
            name: 'Last Name',
            selector: (row: CustomerUserModel) => row.lastName !== undefined ? row.lastName : '',
        },
        {
            name: 'Email',
            selector: (row: CustomerUserModel) => row.email !== undefined ? row.email : '',
        },
        {
            name: 'Region',
            selector: (row: CustomerUserModel) => row.region !== undefined ? row.region : '',
        },
        {
            id: 'actions',
            selector: (row: CustomerUserModel) => row.id !== undefined ? row.id : '',
            className: 'p-0',
            cell: (row: CustomerUserModel) => (
                <div className={'d-flex justify-content-end'}>
                    <Button
                        className="mr-1 mb-1 me-1"
                        color="primary"
                        onClick={() => {
                            if (userData) {
                                const selectedItem = userData.find(item => item.id === row.id);
                                let pushRoute;
                                switch (userType) {
                                    case 'customer':
                                        pushRoute = appRoutes.user.customer.detail;
                                        break;
                                    case 'systemowner':
                                        pushRoute = appRoutes.user.systemowner.detail;
                                        break;
                                    case 'systemadmin':
                                        pushRoute = appRoutes.user.systemadmin.detail;
                                        break;
                                    default:
                                        pushRoute = null; // Handle the case where userType doesn't match any case
                                }
                                navigate(`/${pushRoute?.toString().replace(":id", selectedItem?.id)}`);
                            }
                        }}
                    >
                        <Edit size={14}/>
                    </Button>
                    <Button
                        className="mr-1 mb-1"
                        color="danger"
                        onClick={(value) => {
                            const selectedItem = userData.find(item => item.id === row.id);
                            setSelectedUser(selectedItem);
                            setConfirmDialogShown(true);
                        }}
                    >
                        <Trash size={14}/>
                    </Button>
                </div>
            )
        }
    ];

    const systemOwnerOrAdminColumns = [
        {
            name: 'ID',
            selector: (row: CustomerUserModel) => row.id !== undefined ? row.id : 0,
        },
        {
            name: 'Username',
            selector: (row: CustomerUserModel) => row.username !== undefined ? row.username : '',
        }
    ];

    useEffect(() => {
        fetchData();
    }, [userType, searchData]);


    const fetchData = async () => {
        userService.search(userType, searchData).then(response => {
                setUserData(response.data);
            },
            error => {
                toast.error(error.response.data.message ? error.response.data.message.interpolateError() : error.message.interpolateError());
            });
    };


    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();

        const newSearchData: CustomerUserSearchDTO = {
            firstName,
            lastName,
            region,
            email,
            orderProperty: sortOrder?.orderProperty,
            orderType: sortOrder?.orderType,
            page: page,
            pageSize: perPage
        };

        setSearchData(newSearchData);
    };

    const removeUser = (id: any) => {
        userService.removeUser(userType, id).then(
            (response) => {
                setConfirmDialogShown(false);
                setSelectedUser(undefined);
                fetchData();
                toast.success("SuccessfullyRemoved");

            },
            error => {
                toast.error(error.response.data.message ? error.response.data.message.interpolateError() : error.message.interpolateError());
            });
    };

    const handleModalClose = () => {
        setSelectedUser(undefined);
        setConfirmDialogShown(false);
    };

    const getUserHeader = () => {
        let userHeader;
        switch (userType) {
            case 'customer':
                userHeader = 'Customer Users';
                break;
            case 'systemowner':
                userHeader = 'System Owner';
                break;
            case 'systemadmin':
                userHeader = 'System Admins';
                break;
            default:
                userHeader = ''; // Handle the case where userType doesn't match any case
        }
        return userHeader;
    };

    return <>
        <Row>
            <Col>
                <CardTitle>
                    <h1>{getUserHeader()}</h1>
                </CardTitle>
            </Col>
            <Col>
                <div className={'d-flex justify-content-end'}>
                    { (authService.isSystemAdminOrOwner() && userType === 'customer') &&
                        <Button color="success" className="me-1" onClick={() => {
                            let pushRoute;
                            switch (userType) {
                                case 'customer':
                                    pushRoute = appRoutes.user.customer.detail;
                                    break;
                                default:
                                    pushRoute = null; // Handle the case where userType doesn't match any case
                            }
                            navigate(`/${pushRoute?.toString()}`);
                        }}>
                            <PlusSquare></PlusSquare>
                        </Button>
                    }

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
                            <Label className='form-label'>
                                First Name
                            </Label>
                            <Input
                                autoFocus
                                type='text'
                                onChange={(event) => setFirstName(event.target.value)}
                                id='firstName'
                                name='firstName'
                                placeholder='First Name'
                            />
                        </FormGroup>
                    </Col>
                    <Col>
                        <FormGroup>
                            <Label className='form-label'>
                                Last Name
                            </Label>
                            <Input
                                type='text'
                                onChange={(event) => setLastName(event.target.value)}
                                id='lastName'
                                name='lastName'
                                placeholder='Last Name'
                            />
                        </FormGroup>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <FormGroup>
                            <Label className='form-label'>
                                Region
                            </Label>
                            <Input
                                type='text'
                                onChange={(event) => setRegion(event.target.value)}
                                id='region'
                                name='region'
                                placeholder='Region'
                            />
                        </FormGroup>
                    </Col>
                    <Col>
                        <FormGroup>
                            <Label className='form-label'>
                                Email
                            </Label>
                            <Input
                                type='text'
                                onChange={(event) => setEmail(event.target.value)}
                                id='email'
                                name='email'
                                placeholder='Email'
                            />
                        </FormGroup>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <FormGroup className={'text-end'}>
                            <Button color="primary" type="submit">
                                Search
                            </Button>
                        </FormGroup>
                    </Col>
                </Row>
            </Form>

        </CardBody>
        <CardBody>
            <DataTable
                noHeader
                data={userData}
                columns={userType === 'customer' ? basicColumns : systemOwnerOrAdminColumns}
                className='react-dataTable'
            />
        </CardBody>


        <Modal show={confirmDialogShown} onHide={handleModalClose}>
            <ModalBody>Are you sure for deleting?</ModalBody>
            <ModalFooter>
                <Button color="secondary" onClick={handleModalClose}>
                    Close
                </Button>
                <Button color="danger" onClick={() => removeUser(selectedUser?.id)}>
                    Delete
                </Button>
            </ModalFooter>
        </Modal>
    </>
}
export default UserListPage