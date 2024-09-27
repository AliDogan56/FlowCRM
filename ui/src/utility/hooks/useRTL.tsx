// ** React Imports
import { useEffect } from 'react'

// ** Store & Actions
import { useDispatch, useSelector } from 'react-redux'
import {handleRTL} from "../../redux/actions/layout";

// ** Custom Hook
export const useRTL = (): [boolean, (value: boolean | ((prev: boolean) => boolean)) => void] => {
  // ** Store Vars
  useDispatch();
  const isRtl = useSelector((state: any) => state.layout.isRTL)

  // ** Return a wrapped version of useState's setter function
  const setValue = (value: boolean | ((prev: boolean) => boolean)) => {
    try {
      // ** Allow value to be a function so we have same API as useState
      const valueToStore = value instanceof Function ? value(isRtl) : value
      handleRTL(valueToStore);
    } catch (error) {
      // ** A more advanced implementation would handle the error case
      console.log(error)
    }
  }

  useEffect(() => {
    // ** Get HTML Tag
    const element = document.getElementsByTagName('html')[0]

    // ** If isRTL then add attr dir='rtl' with HTML else attr dir='ltr'
    if (isRtl) {
      element.setAttribute('dir', 'rtl')
    } else {
      element.setAttribute('dir', 'ltr')
    }
  }, [isRtl])

  return [isRtl, setValue]
}
