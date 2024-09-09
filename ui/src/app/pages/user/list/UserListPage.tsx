import TableComponent from "../../../components/table/TableComponent";
import {useEffect, useState} from "react";
import {Pencil, Trash} from "react-bootstrap-icons"
import authService from "../../../services/auth/AuthService";
import {Button} from "react-bootstrap"
import {CustomerUserModel, CustomerUserSearchDTO} from "../../../model/user/CustomerUserModel";
import {UserRole} from "../../../model/user/AppUserModel";
import userService from "../../../services/user/UserService";
import {toast} from "react-toastify";

const UserListPage = () => {
    const userType = new URL(window.location.href).pathname.toString().split("/")[2];
    const userRole = authService.isSystemAdminOrOwner();

    const [userData, setUserData] = useState<CustomerUserModel[]>([]);
    const [searchData, setSearchData] = useState<CustomerUserSearchDTO>();

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
                        }}
                    >
                        <Pencil/>
                    </Button>
                    <Button
                        className="btn-table-cell btn-full-back btn-bg-danger"
                        variant="danger"
                        onClick={() => {
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
                        }}
                    >
                        <Pencil/>
                    </Button>
                    <Button
                        className="btn-table-cell btn-full-back btn-bg-danger"
                        variant="danger"
                        onClick={() => {
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
    }, [userType]);


    const fetchData = async () => {
        const fakeData: CustomerUserModel[] = [
            {
                id: 1,
                createdAt: "2024-09-09T12:00:00Z",
                username: "john.doe",
                role: UserRole.Customer,
                firstName: "John",
                lastName: "Doe",
                region: "North",
                email: "john.doe@example.com"
            },
            {
                id: 2,
                createdAt: "2024-09-10T14:30:00Z",
                username: "jane.smith",
                role: UserRole.Customer,
                firstName: "Jane",
                lastName: "Smith",
                region: "South",
                email: "jane.smith@example.com"
            },
            {
                id: 3,
                createdAt: "2024-09-11T09:15:00Z",
                username: "alice.jones",
                role: UserRole.Customer,
                firstName: "Alice",
                lastName: "Jones",
                region: "East",
                email: "alice.jones@example.com"
            },
            {
                id: 4,
                createdAt: "2024-09-12T17:45:00Z",
                username: "bob.brown",
                role: UserRole.Customer,
                firstName: "Bob",
                lastName: "Brown",
                region: "West",
                email: "bob.brown@example.com"
            }
        ];
        setUserData(fakeData);
        userService.search(userType, searchData).then(response => {
                setUserData(response.data);
            },
            error => {
                toast.error(error.response.data.message ? error.response.data.message.interpolateError() : error.message.interpolateError());
            });
    };


    return (
        <div className="container mt-4">
            <h1>User Data Table</h1>
            <TableComponent columns={userType === 'customer' ? customerColumns : systemOwnerOrAdminColumns}
                            data={userData}
                            showActions={true}/>
        </div>
    );
}
export default UserListPage