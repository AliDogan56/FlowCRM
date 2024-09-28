// ** React Imports
import {useEffect} from 'react'

// ** Third Party Components
import classnames from 'classnames'

// ** Store & Actions
import {handleContentWidth, handleMenuCollapsed, handleMenuHidden} from '../../../../redux/actions/layout'

// ** Styles

const LayoutWrapper = (props: any) => {
    // ** Props
    const {children, routeMeta} = props


//** Vars
// ** Clean Up Function
    const cleanUp = () => {
        if (routeMeta) {
            if (routeMeta.contentWidth) {
                handleContentWidth('full')
            }
            if (routeMeta.menuCollapsed) {
                handleMenuCollapsed(!routeMeta.menuCollapsed)
            }
            if (routeMeta.menuHidden) {
                handleMenuHidden(!routeMeta.menuHidden)
            }
        }
    }

    // ** ComponentDidMount
    useEffect(() => {
        if (routeMeta) {
            if (routeMeta.contentWidth) {
                handleContentWidth(routeMeta.contentWidth)
            }
            if (routeMeta.menuCollapsed) {
                handleMenuCollapsed(routeMeta.menuCollapsed)
            }
            if (routeMeta.menuHidden) {
                handleMenuHidden(routeMeta.menuHidden)
            }
        }
        return () => cleanUp()
    }, [])

    return (
        <div
            className={classnames('app-content content overflow-hidden')}>
            <div className='content-overlay'></div>
            <div className='header-navbar-shadow'/>
            <div className={classnames({'content-wrapper': true, 'content-area-wrapper': true, 'container p-0': false})}>
                <div style={{width: '100vw', height: '100vh'}}> {children}</div>
            </div>
        </div>
    )
}

export default LayoutWrapper
