// ** React Imports
import {Link, useLocation} from 'react-router-dom';
import {FC} from 'react';

// ** Third Party Components
import classnames from 'classnames';
import {Collapse, Badge} from 'reactstrap';

// ** Vertical Menu Items Component
import VerticalNavMenuItems from './VerticalNavMenuItems';

// ** Utils
import {isNavGroupActive, getAllParents} from '../../../utils';

// ** Define types for props
interface VerticalNavMenuGroupProps {
    item: {
        id: string;
        icon: React.ReactNode;
        title: string;
        badge?: string;
        badgeText?: string;
        children?: {
            id: string;
            icon?: React.ReactNode;
            title: string;
            header: string;
            badge?: string;
            badgeText?: string;
            children?: any[];
        }[];
    };
    groupActive: string[];
    setGroupActive: (activeGroups: string[]) => void;
    activeItem: string | null;
    setActiveItem: (item: string | null) => void;
    groupOpen: string[];
    setGroupOpen: (openGroups: string[]) => void;
    parentItem?: {
        id: string;
    };
    menuCollapsed: boolean;
    menuHover: boolean;
    routerProps: any; // Adjust this type based on actual routerProps shape
    currentActiveItem: string;
}

interface NavItem {
    id: string;
    children?: NavItem[]; // Add this line to include children
}

// ** VerticalNavMenuGroup Component
const VerticalNavMenuGroup: FC<VerticalNavMenuGroupProps> = ({
                                                                 item,
                                                                 groupActive,
                                                                 setGroupActive,
                                                                 activeItem,
                                                                 setActiveItem,
                                                                 groupOpen,
                                                                 setGroupOpen,
                                                                 parentItem,
                                                                 menuCollapsed,
                                                                 menuHover,
                                                                 routerProps,
                                                                 currentActiveItem
                                                             }) => {
    // ** Current Val
    const currentURL = useLocation().pathname;

    // ** Toggles Open Group
    const toggleOpenGroup = (item: string, parentItem?: { id: string }) => {
        let openArr = [...groupOpen];
        let allParents: string[] = [];

        if (parentItem) {
            allParents = getAllParents(parentItem, 'id');
            allParents.pop();
        }

        if (groupOpen && allParents && groupOpen[0] === allParents[0]) {
            if (groupOpen.includes(item)) {
                openArr.splice(openArr.indexOf(item), 1);
            } else {
                openArr.push(item);
            }
        } else {
            openArr = [];
            if (!groupOpen.includes(item)) {
                openArr.push(item);
            }
        }

        setGroupOpen([...openArr]);
    };

    // ** Toggle Active Group
    const toggleActiveGroup = (item: string, parentItem?: { id: string }) => {
        let activeArr = [...groupActive];
        let allParents: string[] = [];

        if (parentItem) {
            allParents = getAllParents(parentItem, 'id');
            activeArr = allParents;
        } else {
            if (activeArr.includes(item)) {
                activeArr.splice(activeArr.indexOf(item), 1);
            } else {
                activeArr.push(item);
            }
        }

        const openArr = groupOpen.filter(val => !activeArr.includes(val));
        setGroupOpen([...openArr]);
        setGroupActive([...activeArr]);
    };

    // ** On Group Item Click
    const onCollapseClick = (
        e: React.MouseEvent<HTMLAnchorElement>,
        item: NavItem
    ) => {
        if (
            (groupActive && groupActive.includes(item.id)) ||
            isNavGroupActive(item.children || [], currentURL, routerProps)
        ) {
            toggleActiveGroup(item.id);
        } else {
            toggleOpenGroup(item.id, parentItem);
        }

        e.preventDefault();
    };

    // ** Returns condition to add open class
    const openClassCondition = (id: string) => {
        if ((menuCollapsed && menuHover) || !menuCollapsed) {
            return groupActive.includes(id) || groupOpen.includes(item.id);
        } else if (groupActive.includes(id) && menuCollapsed && !menuHover) {
            return false;
        }
        return null;
    };

    return (
        <li
            className={classnames('nav-item has-sub', {
                open: openClassCondition(item.id),
                'menu-collapsed-open': groupActive.includes(item.id),
                'sidebar-group-active': groupActive.includes(item.id) || groupOpen.includes(item.id)
            })}
        >
            <Link className='d-flex align-items-center' to='/' onClick={e => onCollapseClick(e, item)}>
                {item.icon}
                <span className='menu-title text-truncate'>
          {item.title}
        </span>

                {item.badge && item.badgeText ? (
                    <Badge className='ml-auto mr-1' color={item.badge} pill>
                        {item.badgeText}
                    </Badge>
                ) : null}
            </Link>

            {/* Render Child Recursively Through VerticalNavMenuItems Component */}
            <ul className='menu-content'>
                <Collapse isOpen={(groupActive.includes(item.id) || groupOpen.includes(item.id))}>
                    <VerticalNavMenuItems
                        items={item.children || []}
                        groupActive={groupActive}
                        setGroupActive={setGroupActive}
                        groupOpen={groupOpen}
                        setGroupOpen={setGroupOpen}
                        toggleActiveGroup={toggleActiveGroup}
                        parentItem={item}
                        menuCollapsed={menuCollapsed}
                        menuHover={menuHover}
                        routerProps={routerProps}
                        currentActiveItem={currentActiveItem}
                        activeItem={activeItem}
                        setActiveItem={setActiveItem}
                    />
                </Collapse>
            </ul>
        </li>
    );
};

export default VerticalNavMenuGroup;
