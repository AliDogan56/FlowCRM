//** React Imports
import { useEffect } from 'react'

// ** Actions & Store
import { handleSkin } from '../../redux/actions/layout'
import { useSelector, useDispatch } from 'react-redux'

export const useSkin = () => {
  useDispatch();
  const skin = useSelector((state:any) => state.layout.skin)

  // ** Return a wrapped version of useState's setter function
  const setValue = (value:any) => {
    try {
      // ** Allow value to be a function so we have same API as useState
      const valueToStore = value instanceof Function ? value(skin) : value
      // ** Save to store & local storage
      handleSkin(value);
      window.localStorage.setItem('skin', JSON.stringify(valueToStore))
    } catch (error) {
      // ** A more advanced implementation would handle the error case
      console.log(error)
    }
  }

  useEffect(() => {
    // ** Get Body Tag
    const element = window.document.body

    // ** Define classnames for skins
    type SkinType = 'dark' | 'bordered' | 'semi-dark'; // Adjust based on actual values

    const skin: SkinType = 'dark'; // Example value; ensure this matches your logic

    const classNames: Record<SkinType, string> = {
      dark: 'dark-class',
      bordered: 'bordered-class',
      'semi-dark': 'semi-dark-class'
    };

    // ** Remove all classes from Body on mount
    const classList = Array.from(element.classList);
    element.classList.remove(...classList);

    // ** If skin is not light add skin class
    if (skin in classNames) {
      element.classList.add(classNames[skin as keyof typeof classNames]);
    }
  }, [skin])

  return [skin, setValue]
}
