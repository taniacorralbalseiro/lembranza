// src/backend/userService.js
import { appFetch, setServiceToken, setReauthenticationCallback, removeServiceToken } from './appFetch';

export async function login(userName, password, reauthCb) {
    const resp = await appFetch('POST', '/api/auth/login', { email:userName, password });

    if (resp.ok && resp.payload?.token) {
        setServiceToken(resp.payload.token);
        setReauthenticationCallback(reauthCb);
    }

    return resp; // <-- payload ya es { token, expiresIn, type, user }
}

export async function loginFromServiceToken(token) {
    const resp = await appFetch('POST', '/api/auth/loginFromServiceToken', { token });
    if (resp.ok && resp.payload?.token) {
        setServiceToken(resp.payload.token);
    }
    return resp; // mismo formato
}

export function logout() {
    removeServiceToken();
}
