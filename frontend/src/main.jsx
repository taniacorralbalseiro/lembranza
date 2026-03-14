// src/main.jsx
import React from 'react';
import ReactDOM from 'react-dom/client';

import { Provider } from 'react-redux';
import { BrowserRouter } from 'react-router-dom';
import { IntlProvider } from 'react-intl';

import store from './store/store';
import { App } from './modules/app';
import users from './modules/users';
import app from './modules/app';
import AppGlobalComponents from './modules/app/components/AppGlobalComponents';
import backend from './backend';
import { getServiceToken, init as initAppFetch } from './backend/appFetch';

// CSS / JS global (Bootstrap 4)
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap';
import NetworkError from "./backend/NetworkError.js";
import './index.css';
// 1) Callback global ante errores de red (5xx / conexión)
initAppFetch(() => {
    store.dispatch(app.actions.error(new NetworkError()));
});

// 2) Reautenticación automática en recargas
const token = getServiceToken();
if (token) {
    backend.userService
        .loginFromServiceToken(token)
        .then((resp) => {
            if (resp.ok) {
                // payload: { token, expiresIn, type, user }
                store.dispatch(users.actions.loginCompleted(resp.payload.user));
            } else {
                store.dispatch(users.actions.logout());
            }
        })
        .catch(() => {
            store.dispatch(users.actions.logout());
        });
}

// 3) i18n básico (puedes cargar tus mensajes reales aquí)
const locale = 'es';
const messages = {}; // si tienes src/i18n/messages, impórtalos y colócalos aquí

// 4) Render raíz (Vite usa index.html en la raíz con <div id="root" />)
ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <Provider store={store}>
            <IntlProvider locale={locale} messages={messages}>
                <BrowserRouter>
                    <App />
                    <AppGlobalComponents />
                </BrowserRouter>
            </IntlProvider>
        </Provider>
    </React.StrictMode>,
);
