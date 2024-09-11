export const appRoutes: any = {
    main: "/",
    auth: {
        login: "/auth/login"
    },
    user: {
        systemowner: {
            list: "/user/systemowner/list",
            detail: "/user/systemowner/detail/:id",
            create: "/user/systemowner/create"
        },
        systemadmin: {
            list: "/user/systemadmin/list",
            detail: "/user/systemadmin/detail/:id",
            create: "/user/systemadmin/create"
        },
        customer: {
            list: "user/customer/list",
            detail: "user/customer/detail/:id",
            create: "user/customer/create"
        }
    }
}