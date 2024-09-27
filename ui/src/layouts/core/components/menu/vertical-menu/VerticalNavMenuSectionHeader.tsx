// ** Third Party Components
import {MoreHorizontal} from 'react-feather';

// ** Type Definitions
interface VerticalNavMenuSectionHeaderProps {
    item: {
        header: string;
    };
    index: number;
}

// ** VerticalNavMenuSectionHeader Component
const VerticalNavMenuSectionHeader: React.FC<VerticalNavMenuSectionHeaderProps> = ({item, index}) => {
    return (
        <li className='navigation-header'>
      <span>
        {item.header}
      </span>
            <MoreHorizontal className='feather-more-horizontal'/>
        </li>
    );
};

export default VerticalNavMenuSectionHeader;
