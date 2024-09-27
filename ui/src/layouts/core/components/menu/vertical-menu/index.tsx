// ** React Imports
import {Fragment, useState, useRef} from 'react';
import {RefObject} from 'react';

// ** Vertical Menu Items Array
import navigation from '../../../../../navigation/horizontal';

// ** Third Party Components
import classnames from 'classnames';
import PerfectScrollbar from 'react-perfect-scrollbar';

// ** Vertical Menu Components
import VerticalMenuHeader from './VerticalMenuHeader';
import VerticalNavMenuItems from './VerticalNavMenuItems';

// ** Define types for props
interface SidebarProps {
    menuCollapsed: boolean;
    setMenuCollapsed: any;
    menuVisibility: boolean;
    setMenuVisibility: any;
    routerProps: any; // Adjust this type based on actual props
    menu?: (props: SidebarProps) => JSX.Element;
    currentActiveItem: string | null;
    skin: 'light' | 'semi-dark' | 'dark';
}

const Sidebar: React.FC<SidebarProps> = (props) => {
    // ** Props
    const {
        menuCollapsed,
        setMenuCollapsed,
        menuVisibility,
        setMenuVisibility,
        routerProps,
        menu,
        currentActiveItem,
        skin
    } = props;

    // ** States
    const [groupOpen, setGroupOpen] = useState<string[]>([]);
    const [groupActive, setGroupActive] = useState<string[]>([]);
    const [activeItem, setActiveItem] = useState<string | null>(null);

    // ** Menu Hover State
    const [menuHover, setMenuHover] = useState(false);

    // ** Ref
    const shadowRef: RefObject<HTMLDivElement> = useRef<HTMLDivElement>(null);

    // ** Function to handle Mouse Enter
    const onMouseEnter = () => {
        if (menuCollapsed) {
            setMenuHover(true);
        }
    };

    const scrollMenu = (container: HTMLElement) => {
        if (shadowRef.current) {
            if (container.scrollTop > 0) {
                if (!shadowRef.current.classList.contains('d-block')) {
                    shadowRef.current.classList.add('d-block');
                }
            } else {
                if (shadowRef.current.classList.contains('d-block')) {
                    shadowRef.current.classList.remove('d-block');
                }
            }
        }
    };

    return (
        <Fragment>
            <div
                className={classnames('main-menu menu-fixed menu-accordion menu-shadow', {
                    expanded: menuHover || !menuCollapsed,
                    'menu-light': skin !== 'semi-dark' && skin !== 'dark',
                    'menu-dark': skin === 'semi-dark' || skin === 'dark',
                })}
                onMouseEnter={onMouseEnter}
                onMouseLeave={() => setMenuHover(false)}
            >
                {menu ? (
                    menu(props)
                ) : (
                    <Fragment>
                        {/* Vertical Menu Header */}
                        <VerticalMenuHeader
                            menuCollapsed={menuCollapsed}
                            setMenuCollapsed={setMenuCollapsed}
                            menuVisibility={menuVisibility}
                            setMenuVisibility={setMenuVisibility}
                            setGroupOpen={setGroupOpen} menuHover={menuHover}
                        />
                        {/* Vertical Menu Header Shadow */}
                        <div className='shadow-bottom' ref={shadowRef}></div>
                        {/* Perfect Scrollbar */}
                        <PerfectScrollbar
                            className='main-menu-content'
                            options={{wheelPropagation: false}}
                            onScrollY={container => scrollMenu(container)}
                        >
                            <ul className='navigation navigation-main'>
                                <VerticalNavMenuItems
                                    items={navigation}
                                    groupActive={groupActive}
                                    setGroupActive={setGroupActive}
                                    activeItem={activeItem}
                                    setActiveItem={setActiveItem}
                                    groupOpen={groupOpen}
                                    setGroupOpen={setGroupOpen}
                                    routerProps={routerProps}
                                    menuCollapsed={menuCollapsed}
                                    menuHover={menuHover}
                                    currentActiveItem={currentActiveItem}
                                    toggleActiveGroup={() => {
                                    }}
                                    parentItem={undefined}
                                />
                            </ul>
                        </PerfectScrollbar>
                    </Fragment>
                )}
            </div>
        </Fragment>
    );
};

export default Sidebar;
