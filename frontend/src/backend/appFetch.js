// src/backend/appFetch.js
const SERVICE_TOKEN_NAME = 'serviceToken';

let networkErrorCallback;
let reauthenticationCallback;

export const init = (callback) => { networkErrorCallback = callback; };
export const setReauthenticationCallback = (callback) => { reauthenticationCallback = callback; };

export const setServiceToken    = (t) => sessionStorage.setItem(SERVICE_TOKEN_NAME, t);
export const getServiceToken    = ()  => sessionStorage.getItem(SERVICE_TOKEN_NAME);
export const removeServiceToken = ()  => sessionStorage.removeItem(SERVICE_TOKEN_NAME);

const isJson = (response) => {
    const contentType = response.headers.get('content-type');
    return contentType && contentType.indexOf('application/json') !== -1;
};

const getOptions = (method, body) => {
    const options = { method };

    if (body) {
        if (body instanceof FormData) {
            options.body = body;
        } else {
            options.headers = { 'Content-Type': 'application/json' };
            options.body = JSON.stringify(body);
        }
    }

    const token = getServiceToken();
    if (token) {
        options.headers = { ...(options.headers || {}), Authorization: `Bearer ${token}` };
    }

    return options;
};

export const appFetch = async (method, path, body) => {

    const baseUrl = import.meta.env.VITE_BACKEND_URL || 'http://localhost:8080';

    try {
        const response = await fetch(`${baseUrl}${path}`, getOptions(method, body));
        const res = { ok: response.ok, status: response.status, payload: null };

        if (response.status === 401 && reauthenticationCallback) {
            reauthenticationCallback();
            return res;
        }

        if (isJson(response)) {
            res.payload = await response.json();
        }
        return res;

    } catch (err) {
        networkErrorCallback?.();
        return { ok: false, status: 0, payload: { code: 'NETWORK_ERROR', message: 'No se pudo conectar con el servidor.' },
        };
    }
};
