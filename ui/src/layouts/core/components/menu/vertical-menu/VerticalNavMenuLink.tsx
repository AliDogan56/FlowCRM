// ** React Imports
import {useEffect} from 'react';
import {NavLink, useLocation} from 'react-router-dom';
import {Badge} from 'reactstrap';
import classnames from 'classnames';

// ** Vertical Menu Array Of Items
import navigation from '../../../../../navigation/vertical';

// ** Utils
import {getAllParents, search} from '../../../utils';

interface VerticalNavMenuLinkProps {
    item: {
        id: string;
        navLink: string;
        externalLink?: boolean;
        newTab?: boolean;
        disabled?: boolean;
        icon?: React.ReactNode;
        title: string;
        badge?: string;
        badgeText?: string;
        children?: any;
    };
    groupActive: string[];
    setGroupActive: (groupActive: string[]) => void;
    activeItem: string | null;
    setActiveItem: (activeItem: string | null) => void;
    groupOpen: string[];
    setGroupOpen: (groupOpen: string[]) => void;
    toggleActiveGroup: (itemId: string, parents: string[]) => void;
    parentItem?: any;
    routerProps: any;
    currentActiveItem: string | null;
}

const VerticalNavMenuLink: React.FC<VerticalNavMenuLinkProps> = ({
                                                                     item,
                                                                     groupActive,
                                                                     setGroupActive,
                                                                     activeItem,
                                                                     setActiveItem,
                                                                     groupOpen,
                                                                     setGroupOpen,
                                                                     toggleActiveGroup,
                                                                     parentItem,
                                                                     routerProps,
                                                                     currentActiveItem
                                                                 }) => {
    // ** Conditional Link Tag, if item has newTab or externalLink props use <a> tag else use NavLink
    const LinkTag: React.ElementType = item.externalLink ? 'a' : NavLink;

    // ** URL Vars
    const location = useLocation();
    const currentURL = location.pathname;

    const searchParents = (navigation: any, currentURL: string) => {
        const parents = search(navigation, currentURL, routerProps); // Search for parent object
        const allParents = getAllParents(parents, 'id'); // Parents Object to Parents Array
        return allParents || []; // Ensure it always returns an array
    };

    const resetActiveGroup = (navLink: string) => {
        const parents = searchParents(navigation, navLink);
        toggleActiveGroup(item.id, parents);
    };

    // ** Reset Active & Open Group Arrays
    const resetActiveAndOpenGroups = () => {
        setGroupActive([]);
        setGroupOpen([]);
    };

    // ** Checks url & updates active item
    useEffect(() => {
        if (currentActiveItem !== null) {
            setActiveItem(currentActiveItem);
            const arr = searchParents(navigation, currentURL);
            setGroupActive([...arr]);
        }
    }, [location]);

    const linkProps = item.externalLink
        ? {href: item.navLink || '/'}
        : {
            to: item.navLink || '/'
        };

    return (
        <li
            className={classnames({
                'nav-item': !item.children,
                disabled: item.disabled,
                active: item.navLink === activeItem
            })}
        >
            <LinkTag
                className='d-flex align-items-center'
                target={item.newTab ? '_blank' : undefined}
                // @ts-ignore
                {...linkProps}
                onClick={(e) => {
                    if (!item.navLink.length) {
                        e.preventDefault();
                    }
                    parentItem ? resetActiveGroup(item.navLink) : resetActiveAndOpenGroups();
                }}
            >
                {item.icon}
                <span className='menu-item text-truncate'>
          {item.title}
        </span>

                {item.badge && item.badgeText ? (
                    <Badge className='ml-auto mr-1' color={item.badge} pill>
                        {item.badgeText}
                    </Badge>
                ) : null}
            </LinkTag>
        </li>
    );
};

export default VerticalNavMenuLink;
