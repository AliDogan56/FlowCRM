export const search = (navigation: any, currentURL: any, routerProps: any) => {
    let result
    navigation.some((child: any) => {
        let children
        // If child have children => It's group => Go deeper(recursive)
        if (child.children && (children = search(child.children, currentURL, routerProps))) {
            return (result = {
                id: child.id,
                children
            })
        }

        // else it's link => Check for matched Route
        if (isNavLinkActive(child.navLink, currentURL, routerProps)) {
            return (result = {
                id: child.id
            })
        }
    })
    return result
}

export const isNavLinkActive = (link: any, currentURL: any, routerProps: any) => {
    return (
        currentURL === link ||
        (routerProps && routerProps.meta && routerProps.meta.navLink && routerProps.meta.navLink === link)
    )
}

export const isNavGroupActive = (children: any, currentURL: any, routerProps: any) => {
    return children.some((child: any) => {
        // If child have children => It's group => Go deeper(recursive)
        if (child.children) {
            return isNavGroupActive(child.children, currentURL, routerProps)
        }
        return isNavLinkActive(child.navLink, currentURL, routerProps)
    })
}
