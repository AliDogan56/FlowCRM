// ** React Imports
import {useEffect} from 'react';
import {NavLink} from 'react-router-dom';

// ** Third Party Components
import {Disc, X, Circle} from 'react-feather';

// ** Config
import themeConfig from '../../../../../configs/themeConfig';

// ** Define types for props
interface VerticalMenuHeaderProps {
    menuCollapsed: boolean;
    setMenuCollapsed: any;
    setMenuVisibility: any;
    menuVisibility: boolean;
    setGroupOpen: (groups: string[]) => void;
    menuHover: boolean;
}

// ** VerticalMenuHeader Component
const VerticalMenuHeader: React.FC<VerticalMenuHeaderProps> = (props) => {
    // ** Props
    const {menuCollapsed, setMenuCollapsed, setMenuVisibility, setGroupOpen, menuHover} = props;

    // ** Reset open group
    useEffect(() => {
        if (!menuHover && menuCollapsed) setGroupOpen([]);
    }, [menuHover, menuCollapsed, setGroupOpen]);

    // ** Menu toggler component
    const Toggler = () => {
        return !menuCollapsed ? (
            <Disc
                color={"#2a2e30"}
                size={20}
                data-tour='toggle-icon'
                className='text-primary toggle-icon d-none d-xl-block'
                onClick={() =>{
                    setMenuVisibility(true);
                    setMenuCollapsed(true);
                }
            }
            />
        ) : (
            <Circle
                color={"#2a2e30"}
                size={20}
                data-tour='toggle-icon'
                className='text-primary toggle-icon d-none d-xl-block'
                onClick={() =>{
                    setMenuVisibility(false);
                    setMenuCollapsed(false);
                }
            }
            />
        );
    };

    return (
        <div className='navbar-header'>
            <ul className='nav navbar-nav flex-row'>
                <li className='nav-item mr-auto'>
                    <NavLink to='/' className='navbar-brand'>
            <span className='brand-logo'>
              <img src={themeConfig.app.appLogoImage} alt='logo'/>
            </span>
                        <h2 className='brand-text mb-0'>{themeConfig.app.appName}</h2>
                    </NavLink>
                </li>
                <li className='nav-item nav-toggle'>
                    <div className='nav-link modern-nav-toggle cursor-pointer'>
                        <Toggler/>
                        <X onClick={() => setMenuVisibility(false)} className='toggle-icon icon-x d-block d-xl-none'
                           size={20}/>
                    </div>
                </li>
            </ul>
        </div>
    );
};

export default VerticalMenuHeader;
