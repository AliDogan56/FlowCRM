// ** React Imports
import React, { useEffect, useState, createContext, ReactNode } from 'react'

// ** Create Context
interface ThemeColorsProps {
    colors: {
        primary: {
            light: string
            main: string
        }
        secondary: {
            light: string
            main: string
        }
        success: {
            light: string
            main: string
        }
        danger: {
            light: string
            main: string
        }
        warning: {
            light: string
            main: string
        }
        info: {
            light: string
            main: string
        }
        dark: {
            light: string
            main: string
        }
    }
}

const ThemeColors = createContext<ThemeColorsProps | undefined>(undefined)

interface ThemeContextProps {
    children: ReactNode
}

const ThemeContext: React.FC<ThemeContextProps> = ({ children }) => {
    // ** State
    const [colors, setColors] = useState<ThemeColorsProps['colors']>({
        primary: { light: '', main: '' },
        secondary: { light: '', main: '' },
        success: { light: '', main: '' },
        danger: { light: '', main: '' },
        warning: { light: '', main: '' },
        info: { light: '', main: '' },
        dark: { light: '', main: '' }
    })

    // ** ComponentDidMount
    useEffect(() => {
        if (typeof window !== 'undefined') {
            // ** Get variable value
            const getHex = (color: string) => window.getComputedStyle(document.body).getPropertyValue(color).trim()

            // ** Colors obj
            const obj: ThemeColorsProps['colors'] = {
                primary: {
                    light: getHex('--primary').concat('1a'),
                    main: getHex('--primary')
                },
                secondary: {
                    light: getHex('--secondary').concat('1a'),
                    main: getHex('--secondary')
                },
                success: {
                    light: getHex('--success').concat('1a'),
                    main: getHex('--success')
                },
                danger: {
                    light: getHex('--danger').concat('1a'),
                    main: getHex('--danger')
                },
                warning: {
                    light: getHex('--warning').concat('1a'),
                    main: getHex('--warning')
                },
                info: {
                    light: getHex('--info').concat('1a'),
                    main: getHex('--info')
                },
                dark: {
                    light: getHex('--dark').concat('1a'),
                    main: getHex('--dark')
                }
            }

            setColors(obj)
        }
    }, [])

    return <ThemeColors.Provider value={{ colors }}>{children}</ThemeColors.Provider>
}

export { ThemeColors, ThemeContext }
