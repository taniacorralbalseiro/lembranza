// users/actions.js
import * as actionTypes from './actionTypes';
import backend from '../../backend';

export const loginCompleted = (authenticatedUser) => ({
    type: actionTypes.LOGIN_COMPLETED,
    authenticatedUser
});

export const loginFailed = (errorPayload) => ({
    type: actionTypes.LOGIN_FAILED,
    error: errorPayload
});

export const logout = () => ({ type: actionTypes.LOGOUT });

export const login = (userName, password) => async (dispatch) => {

  try {
    const response = await backend.userService.login(userName, password, () => {
      // reauth callback -> forzar logout
      dispatch(logout());
    });

    if (response.ok) {
      // En tus apuntes suelen pasar el user completo en payload
      dispatch(loginCompleted(response.payload.user));
      // Devolvemos ok para que el componente decida navegar
      return {ok: true};
    } else {
      dispatch(loginFailed(response.payload));
      return {ok: false, error: response.payload};
    }
  } catch (err) {
    const payload = { globalError: 'No se pudo conectar con el servidor.' };
    dispatch(loginFailed(payload));
    return { ok: false, error: payload};
  }
};
