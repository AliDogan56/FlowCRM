// ** Redux, Thunk & Root Reducer Imports
import { configureStore } from '@reduxjs/toolkit'
import createDebounce from 'redux-debounced'
import rootReducer from '../reducers/rootReducer'
import thunk from 'redux-thunk'

// ** Create store
const store = configureStore({
    reducer: rootReducer,
    middleware: (getDefaultMiddleware) =>
        getDefaultMiddleware().concat(thunk, createDebounce()),
    devTools: process.env.NODE_ENV !== 'production',
})

export { store }
