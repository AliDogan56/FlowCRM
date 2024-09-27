// ** React Imports
import { useState, useEffect } from 'react'

// ** Configs
import themeConfig from '../../configs/themeConfig'

// ** Custom Hook
export const useLayout = (): [string | null, (value: string | ((prev: string | null) => string)) => void] => {
    // ** States
    const [lastLayout, setLastLayout] = useState<string | null>(null)
    const [layout, setLayout] = useState<string>(() => {
        try {
            return themeConfig.layout.type
        } catch (error) {
            // ** If error return initialValue
            console.log(error)
            return themeConfig.layout.type
        }
    })

    // ** Return a wrapped version of useState's setter function
    const setValue = (value: string | ((prev: string) => string)) => {
        try {
            // ** Allow value to be a function so we have same API as useState
            const valueToStore = value instanceof Function ? value(layout) : value

            // ** Set state
            setLayout(valueToStore)
        } catch (error) {
            // ** A more advanced implementation would handle the error case
            console.log(error)
        }
    }

    const handleLayout = () => {
        // ** If layout is horizontal & screen size is equals to or below 1200
        if (layout === 'horizontal' && window.innerWidth <= 1200) {
            setLayout('vertical')
            setLastLayout('horizontal')
        }
        // ** If lastLayout is horizontal & screen size is equals to or above 1200
        if (lastLayout === 'horizontal' && window.innerWidth >= 1200) {
            setLayout('horizontal')
        }
    }

    // ** ComponentDidMount
    useEffect(() => {
        handleLayout()
    }, [])

    useEffect(() => {
        // ** Window Resize Event
        const handleResize = () => handleLayout()
        window.addEventListener('resize', handleResize)
        return () => {
            window.removeEventListener('resize', handleResize)
        }
    }, [layout, lastLayout])

    return [layout, setValue]
}
