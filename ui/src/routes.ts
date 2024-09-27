export const appRoutes: any = {
    main: "/",
    home: "/home",
    auth: {
        login: "/login"
    },
    user: {
        systemowner: {
            list: "/user/systemowner/list",
            detail: "/user/systemowner/detail/:id",
        },
        systemadmin: {
            list: "/user/systemadmin/list",
            detail: "/user/systemadmin/detail/:id",
        },
        customer: {
            list: "user/customer/list",
            detail: "user/customer/detail/:id",
        }
    }
}