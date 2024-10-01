// ** React Imports
import {Suspense, lazy} from 'react'
// ** Redux Imports
import {Provider} from 'react-redux'
import {store} from './redux/storeConfig/store'

// ** Intl, CASL & ThemeColors Context
import ability from './configs/acl/ability'
import {ToastContainer} from 'react-toastify'
import {AbilityContext} from './utility/context/Can'
import {ThemeContext} from './utility/context/ThemeColors'
import {IntlProviderWrapper} from './utility/context/Internationalization'

import Spinner from './components/spinner/Fallback-spinner'

import 'react-perfect-scrollbar/dist/css/styles.css'
import './assets/scss/main/react/libs/toastify/toastify.scss'


import './assets/fonts/feather/iconfont.css'
import './assets/scss/main/core.scss'

// ** Service Worker
import * as serviceWorker from './serviceWorker'
import axios from "axios";
import authService from "./app/services/auth/AuthService";
import {createRoot} from "react-dom/client";

axios.interceptors.response.use(response => {
    return response;
}, error => {
    if (error.response.status === 401) {
        authService.logout();
    }
    return Promise.reject(error);
});

// ** Lazy load app
const LazyApp = lazy(() => import('./app/App'))
const container = document.getElementById('root');
if (container) {
    const root = createRoot(container);
    root.render(
        <Provider store={store}>
            <Suspense fallback={<Spinner />}>
                <AbilityContext.Provider value={ability}>
                    <ThemeContext>
                        <IntlProviderWrapper>
                            <LazyApp />
                            <ToastContainer newestOnTop />
                        </IntlProviderWrapper>
                    </ThemeContext>
                </AbilityContext.Provider>
            </Suspense>
        </Provider>
    );
} else {
    console.error("Root element not found");
}

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister()

