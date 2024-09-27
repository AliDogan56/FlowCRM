import {Box, CheckSquare, ShoppingCart, User} from 'react-feather'

export default [
    {
        id: 'user',
        title: 'Users',
        icon: <Box/>,
        children: [
            {
                id: 'systemOwner',
                title: 'System Owner',
                icon: <User/>,
                navLink: '/user/systemowner/list'
            },
            {
                id: 'systemAdmin',
                title: 'System Admins',
                icon: <CheckSquare/>,
                navLink: '/user/systemadmin/list'
            },
            {
                id: 'customer',
                title: 'Customer Users',
                icon: <ShoppingCart/>,
                navLink: '/user/customer/list'
            }
        ]
    }
]
