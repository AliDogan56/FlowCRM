// ** React Imports
import React, { useContext, MouseEvent } from 'react';

// ** Third Party Components
import { UncontrolledDropdown, DropdownMenu, DropdownItem, DropdownToggle } from 'reactstrap';
import ReactCountryFlag from 'react-country-flag';

// ** Internationalization Context
interface IntlContextType {
    switchLanguage: (lang: string) => void;
    locale: string;
}

const IntlContext = React.createContext<IntlContextType | undefined>(undefined);

const IntlDropdown: React.FC = () => {
    // ** Context
    const intlContext = useContext(IntlContext);

    if (!intlContext) {
        throw new Error('IntlDropdown must be used within an IntlProvider');
    }

    // ** Vars
    const langObj: { [key: string]: string } = {
        en: 'English',
        de: 'German',
        fr: 'French',
        pt: 'Portuguese',
    };

    // ** Function to switch Language
    const handleLangUpdate = (e: MouseEvent, lang: string) => {
        e.preventDefault();
        intlContext.switchLanguage(lang);
    };

    return (
        <UncontrolledDropdown tag="li" className="dropdown-language nav-item">
            <DropdownToggle href="/" tag="a" className="nav-link" onClick={(e) => e.preventDefault()}>
                <ReactCountryFlag
                    className="country-flag flag-icon"
                    countryCode={intlContext.locale === 'en' ? 'us' : intlContext.locale}
                    svg
                />
                <span className="selected-language">{langObj[intlContext.locale]}</span>
            </DropdownToggle>
            <DropdownMenu className="mt-0" right>
                {Object.entries(langObj).map(([langCode, langName]) => (
                    <DropdownItem key={langCode} href="/" tag="a" onClick={(e) => handleLangUpdate(e, langCode)}>
                        <ReactCountryFlag className="country-flag" countryCode={langCode === 'en' ? 'us' : langCode} svg />
                        <span className="ml-1">{langName}</span>
                    </DropdownItem>
                ))}
            </DropdownMenu>
        </UncontrolledDropdown>
    );
};

export default IntlDropdown;
