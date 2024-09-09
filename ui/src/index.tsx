import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './app/App';
import axios from 'axios';
import authService from "./app/services/AuthService";


axios.interceptors.response.use(response => {
    return response;
}, error => {
    if (error.response.status === 401) {
        authService.logout();
        window.location.href = '/auth/login';
    }
    return Promise.reject(error);
});

axios.interceptors.request.use(function (config) {
    if (!config.url?.includes("wp-json")) {
        config.headers['Cache-Control'] = "no-cache";
        config.headers['Cache-Control'] = "no-cache";
    }
    return config;
});




const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);

