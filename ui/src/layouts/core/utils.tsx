// ** React Imports

import {Can} from "../../utility/context/Can";

/**
 * Return which component to render based on it's data/context
 * @param {Object} item nav menu item
 */
export const resolveVerticalNavMenuItemComponent = (item: any) => {
    if (item.header) return 'VerticalNavMenuSectionHeader'
    if (item.children) return 'VerticalNavMenuGroup'
    return 'VerticalNavMenuLink'
}

/**
 * Return which component to render based on it's data/context
 * @param {Object} item nav menu item
 */
export const resolveHorizontalNavMenuItemComponent = (item: any) => {
    if (item.children) return 'HorizontalNavMenuGroup'
    return 'HorizontalNavMenuLink'
}

/**
 * Check if nav-link is active
 * @param {Object} link nav-link object
 * @param currentURL
 * @param routerProps
 */
// export const isNavLinkActive = (link, currentURL, match) => {
//   // return currentURL === link || (URLParams && Object.keys(URLParams).length && currentURLFilter === item.navLink)
//   const getFirstObjProp = obj => obj[Object.keys(obj)[0]]
//   return (
//     currentURL === link ||
//     (match !== null && match !== undefined && match.url === `${link}/${getFirstObjProp(match.params)}`)
//   )
// }

export const isNavLinkActive = (link: any, currentURL: any, routerProps: any) => {
    return (
        currentURL === link ||
        (routerProps && routerProps.meta && routerProps.meta.navLink && routerProps.meta.navLink === link)
    )
    // return currentURL === link
}

/**
 * Check if nav group is
 * @param {Array} children Group children
 * @param currentURL
 * @param routerProps
 */
// export const isNavGroupActive = (children, currentURL, match) => {
//   return children.some(child => {
//     // If child have children => It's group => Go deeper(recursive)
//     if (child.children) {
//       return isNavGroupActive(child.children, currentURL, match)
//     }
//     // else it's link => Check for matched Route
//     return isNavLinkActive(child.navLink, currentURL, match)
//   })
// }
export const isNavGroupActive = (children: any, currentURL: any, routerProps: any) => {
    return children.some((child: any) => {
        // If child have children => It's group => Go deeper(recursive)
        if (child.children) {
            return isNavGroupActive(child.children, currentURL, routerProps)
        }
        // else it's link => Check for matched Route
        return isNavLinkActive(child.navLink, currentURL, routerProps)
    })
}

/**
 * Search for parent object
 * @param {Array} navigation Group children
 * @param {string} currentURL current URL
 * @param routerProps
 */
// export const search = (navigation, currentURL, match) => {
//   let result
//   navigation.some(child => {
//     let children
//     // If child have children => It's group => Go deeper(recursive)
//     if (child.children && (children = search(child.children, currentURL, match))) {
//       return (result = {
//         id: child.id,
//         children
//       })
//     }

//     // else it's link => Check for matched Route
//     if (isNavLinkActive(child.navLink, currentURL, match)) {
//       return (result = {
//         id: child.id
//       })
//     }
//   })
//   return result
// }

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

/**
 * Loop through nested object
 * @param {object} obj nested object
 * @param match
 */
export const getAllParents = (obj: any, match: any) => {
    const res: any[] = []
    const recurse = (obj: any, current: any) => {
        for (const key in obj) {
            const value = obj[key]
            if (value !== undefined) {
                if (value && typeof value === 'object') {
                    recurse(value, key) // Recursively call with updated current key
                } else {
                    if (key === match) {
                        res.push(value)
                    }
                }
            }
        }
    }
    recurse(obj, null) // Call recurse with initial obj and a placeholder for `current`
    return res
}


export const canViewMenuGroup = (item: any) => {
    // ! This same logic is used in canViewHorizontalNavMenuGroup and canViewHorizontalNavMenuHeaderGroup. So make sure to update logic in them as well
    const hasAnyVisibleChild = item.children && item.children.some((i: any) => Can(i.action, i.resource))

    // ** If resource and action is defined in item => Return based on children visibility (Hide group if no child is visible)
    // ** Else check for ability using provided resource and action along with checking if has any visible child
    if (!(item.action && item.resource)) {
        return hasAnyVisibleChild
    }
    return Can(item.action, item.resource) && hasAnyVisibleChild
}

export const canViewMenuItem = (item: any) => {
    return Can(item.action, item.resource)
}
