import {lazy} from 'react'
import {appRoutes} from "../../routes";

const Pages = [
    {
        path: appRoutes.auth.login,
        component: lazy(() => import('../../app/pages/auth/LoginPage')),
        layout: 'BlankLayout',
        meta: {
            authRoute: true
        }
    },
    {
        path: appRoutes.main,
        exact: true,
        appLayout: true,
        className: 'HomePage',
        meta: {
            defaultRoute: true
        },
        component: lazy(() => import('../../app/pages/home/HomePage'))
    },
    {
        path: appRoutes.home,
        exact: true,
        appLayout: true,
        className: 'HomePage',
        component: lazy(() => import('../../app/pages/home/HomePage'))
    },
    {
        path: appRoutes.user.customer.list,
        exact: true,
        appLayout: true,
        className: 'CustomerUserList',
        component: lazy(() => import('../../app/pages/user/list/UserListPage'))
    },
    {
        path: appRoutes.user.customer.detail,
        exact: true,
        appLayout: true,
        className: 'CustomerUserDetail',
        component: lazy(() => import('../../app/pages/user/detail/UserDetailPage'))
    },
    {
        path: appRoutes.user.systemadmin.list,
        exact: true,
        appLayout: true,
        className: 'SystemAdminList',
        component: lazy(() => import('../../app/pages/user/list/UserListPage'))
    },
    {
        path: appRoutes.user.systemadmin.detail,
        exact: true,
        appLayout: true,
        className: 'SystemAdminDetail',
        component: lazy(() => import('../../app/pages/user/detail/UserDetailPage'))
    },
    {
        path: appRoutes.user.systemowner.list,
        exact: true,
        appLayout: true,
        className: 'SystemOwnerList',
        component: lazy(() => import('../../app/pages/user/list/UserListPage'))
    },
    {
        path: appRoutes.user.systemowner.detail,
        exact: true,
        appLayout: true,
        className: 'SystemOwnerDetail',
        component: lazy(() => import('../../app/pages/user/detail/UserDetailPage'))
    }

]

export default Pages
