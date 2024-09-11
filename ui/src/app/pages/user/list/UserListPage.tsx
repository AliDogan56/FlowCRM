import TableComponent from "../../../components/table/TableComponent";
import {useEffect, useState} from "react";
import {Pencil, Trash} from "react-bootstrap-icons"
import {CustomerUserModel, CustomerUserSearchDTO} from "../../../model/user/CustomerUserModel";
import userService from "../../../services/user/UserService";
import {toast} from "react-toastify";
import {Form, Button, Row, Col} from 'react-bootstrap';
import {SortOrder} from "../../../model/base/SortOrderDTO";
import authService from "../../../services/auth/AuthService";
import {appRoutes} from "../../../../routes";
import {useNavigate} from "react-router-dom";
import Modal from 'react-bootstrap/Modal';


const UserListPage = () => {
    const userType = new URL(window.location.href).pathname.toString().split("/")[2];
    const isOwnerOrAdmin = authService.isSystemAdminOrOwner();
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [region, setRegion] = useState('');
    const [email, setEmail] = useState('');
    const [userData, setUserData] = useState<CustomerUserModel[]>([]);
    const [searchData, setSearchData] = useState<CustomerUserSearchDTO>();
    const [sortOrder, setSortOrder] = useState<SortOrder>({fieldName: "id", orderProperty: "id", orderType: "ASC"});
    const [page, setPage] = useState(0);
    const [perPage, setPerPage] = useState(10);
    const navigate = useNavigate();
    const [confirmDialogShown, setConfirmDialogShown] = useState<boolean>(false);
    const [isOwnerOrAdminAndNotCustomer, setIsOwnerOrAdminAndNotCustomer] = useState<boolean>((isOwnerOrAdmin && userType === 'customer'));
    const [selectedUser, setSelectedUser] = useState<CustomerUserModel>();


    const customerColumns = [
        {
            Header: "Id",
            accessor: 'id',
            Cell: ({value}: any) => (<span>{value}</span>)
        },
        {
            Header: "Username",
            accessor: 'username',
            Cell: ({value}: any) => (<span>{value}</span>)
        },
        {
            Header: "First Name",
            accessor: 'firstName',
            Cell: ({value}: any) => (<span>{value}</span>)
        },
        {
            Header: "Last Name",
            accessor: 'lastName',
            Cell: ({value}: any) => (<span>{value}</span>)
        },
        {
            Header: "Email",
            accessor: 'email',
            Cell: ({value}: any) => (<span>{value}</span>)
        },
        {
            Header: "Region",
            accessor: 'region',
            Cell: ({value}: any) => (<span>{value}</span>)
        },
        {
            id: 'actions',
            accessor: 'id',
            className: 'p-0',
            Cell: ({value}: any) => (
                <div className="text-right d-flex justify-content-center" style={{gap: '10px'}}>
                    <Button
                        className="btn-table-cell btn-full-back btn-bg-danger"
                        variant="success"
                        onClick={() => {
                            if (userData) {
                                const selectedItem = userData.find(item => item.id === value);
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
                        <Pencil/>
                    </Button>
                    <Button
                        className="btn-table-cell btn-full-back btn-bg-danger"
                        variant="danger"
                        onClick={() => {
                            const selectedItem = userData.find(item => item.id === value);
                            setSelectedUser(selectedItem);
                            setConfirmDialogShown(true);
                        }}
                    >
                        <Trash/>
                    </Button>
                </div>
            )
        }
    ];

    const systemOwnerOrAdminColumns = [
        {
            Header: "Id",
            accessor: 'id',
            Cell: ({value}: any) => (<span>{value}</span>)
        },
        {
            Header: "Username",
            accessor: 'username',
            Cell: ({value}: any) => (<span>{value}</span>)
        },
        {
            id: 'actions',
            accessor: 'id',
            className: 'p-0',
            Cell: ({value}: any) => (
                <div className="text-right d-flex justify-content-center" style={{gap: '10px'}}>
                    <Button
                        className="btn-table-cell btn-full-back btn-bg-danger"
                        variant="success"
                        onClick={() => {
                            if (userData) {
                                const selectedItem = userData.find(item => item.id === value);
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
                                navigate(`${pushRoute + selectedItem?.id}`)
                            }
                        }}
                    >
                        <Pencil/>
                    </Button>
                    <Button
                        className="btn-table-cell btn-full-back btn-bg-danger"
                        variant="danger"
                        onClick={() => {
                            const selectedItem = userData.find(item => item.id === value);
                            setSelectedUser(selectedItem);
                        }}
                    >
                        <Trash/>
                    </Button>
                </div>
            )
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

    return (
        <div className="container mt-4">
            <Row className="align-items-center">
                <Col className="d-flex justify-content-start">
                    <h1>{getUserHeader()} Users</h1>
                </Col>
                {isOwnerOrAdminAndNotCustomer &&
                    <Col className="d-flex justify-content-end">
                        <Button variant="primary" onClick={() => {
                            let pushRoute;
                            switch (userType) {
                                case 'customer':
                                    pushRoute = appRoutes.user.customer.create;
                                    break;
                                case 'systemowner':
                                    pushRoute = appRoutes.user.systemowner.create;
                                    break;
                                case 'systemadmin':
                                    pushRoute = appRoutes.user.systemadmin.create;
                                    break;
                                default:
                                    pushRoute = null; // Handle the case where userType doesn't match any case
                            }
                            navigate(`/${pushRoute}`);
                        }}>Add</Button>
                    </Col>
                }
            </Row>
            {userType === 'customer' &&
                <Form onSubmit={handleSubmit} className="p-3">
                    <Row className="mb-3">
                        <Col md={6}>
                            <Form.Group controlId="formFirstName">
                                <Form.Label>First Name</Form.Label>
                                <Form.Control
                                    type="text"
                                    placeholder="Enter first name"
                                    value={firstName}
                                    onChange={(e) => setFirstName(e.target.value)}
                                />
                            </Form.Group>
                        </Col>
                        <Col md={6}>
                            <Form.Group controlId="formLastName">
                                <Form.Label>Last Name</Form.Label>
                                <Form.Control
                                    type="text"
                                    placeholder="Enter last name"
                                    value={lastName}
                                    onChange={(e) => setLastName(e.target.value)}
                                />
                            </Form.Group>
                        </Col>
                    </Row>
                    <Row className="mb-3">
                        <Col md={6}>
                            <Form.Group controlId="formRegion">
                                <Form.Label>Region</Form.Label>
                                <Form.Control
                                    type="text"
                                    placeholder="Enter region"
                                    value={region}
                                    onChange={(e) => setRegion(e.target.value)}
                                />
                            </Form.Group>
                        </Col>
                        <Col md={6}>
                            <Form.Group controlId="formEmail">
                                <Form.Label>Email</Form.Label>
                                <Form.Control
                                    type="text"
                                    placeholder="Enter email"
                                    value={email}
                                    onChange={(e) => setEmail(e.target.value)}
                                />
                            </Form.Group>
                        </Col>
                    </Row>
                    <div className="text-end">
                        <Button variant="primary" type="submit">
                            Search
                        </Button>
                    </div>
                </Form>
            }

            <TableComponent columns={userType === 'customer' ? customerColumns : systemOwnerOrAdminColumns}
                            data={userData}
                            showActions={(!(userType === "systemowner" || userType === "systemadmin") && (isOwnerOrAdmin))}/>


            <Modal show={confirmDialogShown} onHide={handleModalClose}>
                <Modal.Body>Are you sure for deleting?</Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleModalClose}>
                        Close
                    </Button>
                    <Button variant="danger" onClick={() => removeUser(selectedUser?.id)}>
                        Delete
                    </Button>
                </Modal.Footer>
            </Modal>

        </div>
    );
}
export default UserListPage