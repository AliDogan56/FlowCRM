// ** React Imports
import {useContext} from 'react';
import {FC} from 'react';

// ** Vertical Menu Components
import VerticalNavMenuLink from './VerticalNavMenuLink';
import VerticalNavMenuGroup from './VerticalNavMenuGroup';
import VerticalNavMenuSectionHeader from './VerticalNavMenuSectionHeader';

// ** Ability Context
import {AbilityContext} from '../../../../../utility/context/Can';

// ** Utils
import {
    resolveVerticalNavMenuItemComponent as resolveNavItemComponent,
    canViewMenuGroup,
    canViewMenuItem
} from '../../../utils';

// ** Define types for props
interface VerticalMenuNavItemsProps {
    items: {
        id: string;
        icon?: React.ReactNode;
        title: string;
        badge?: string;
        badgeText?: string;
        children?: any[];
    }[];
    groupActive: string[];
    setGroupActive: (activeGroups: string[]) => void;
    groupOpen: string[];
    setGroupOpen: (openGroups: string[]) => void;
    toggleActiveGroup: (groupId: string) => void;
    parentItem: any; // Define this type as needed
    menuCollapsed: boolean;
    menuHover: boolean;
    routerProps: any; // Define this type as needed
    currentActiveItem: string | null;
    activeItem: string | null;
    setActiveItem: (item: string | null) => void;
}

// ** Components Object Type
interface Components {
    VerticalNavMenuSectionHeader: FC<any>;
    VerticalNavMenuGroup: FC<any>;
    VerticalNavMenuLink: FC<any>;
}

// ** VerticalMenuNavItems Component
const VerticalMenuNavItems: FC<VerticalMenuNavItemsProps> = props => {
    // ** Context
    useContext(AbilityContext);

    // ** Components Object
    const Components: Components = {
        VerticalNavMenuSectionHeader,
        VerticalNavMenuGroup,
        VerticalNavMenuLink
    };

    // ** Render Nav Menu Items
    const RenderNavItems = props.items.map((item, index) => {
        const TagName = Components[resolveNavItemComponent(item)];
        if (item.children) {
            return canViewMenuGroup(item) && <TagName item={item} index={index} key={item.id} {...props} />;
        }
        return canViewMenuItem(item) && <TagName key={item.id || item.title} item={item} {...props} />;
    });

    return <>{RenderNavItems}</>;
};

export default VerticalMenuNavItems;
