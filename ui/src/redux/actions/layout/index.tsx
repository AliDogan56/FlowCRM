import {Dispatch} from 'redux'
import {
    HANDLE_CONTENT_WIDTH,
    HANDLE_MENU_COLLAPSED,
    HANDLE_MENU_HIDDEN,
    HANDLE_RTL,
    HANDLE_SKIN
} from './actionsTypes'

// Define Action Types
interface HandleContentWidthAction {
    type: typeof HANDLE_CONTENT_WIDTH
    value: any
}

interface HandleMenuCollapsedAction {
    type: typeof HANDLE_MENU_COLLAPSED
    value: any
}

interface HandleMenuHiddenAction {
    type: typeof HANDLE_MENU_HIDDEN
    value: any
}

interface HandleRTLAction {
    type: typeof HANDLE_RTL
    value: any
}

interface HandleSkinAction {
    type: typeof HANDLE_SKIN
    value: any
}

// Combine Action Types
type LayoutActions =
    | HandleContentWidthAction
    | HandleMenuCollapsedAction
    | HandleMenuHiddenAction
    | HandleRTLAction
    | HandleSkinAction

// Action Creators
export const handleContentWidth = (value: any) => (dispatch: Dispatch<LayoutActions>) =>
    dispatch({type: HANDLE_CONTENT_WIDTH, value})

export const handleMenuCollapsed = (value: any) => (dispatch: Dispatch<LayoutActions>) =>
    dispatch({type: HANDLE_MENU_COLLAPSED, value})

export const handleMenuHidden = (value: any) => (dispatch: Dispatch<LayoutActions>) =>
    dispatch({type: HANDLE_MENU_HIDDEN, value})

export const handleRTL = (value: any) => (dispatch: Dispatch<LayoutActions>) =>
    dispatch({type: HANDLE_RTL, value})

export const handleSkin = (value: any) => (dispatch: Dispatch<LayoutActions>) =>
    dispatch({type: HANDLE_SKIN, value})
