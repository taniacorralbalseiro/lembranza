import * as actionTypes from './actionTypes';

const initialState = {
    authenticatedUser: null, // objeto usuario (id/publicId, nombre, apellidos, email, rol, etc.)
    authError: null          // últimos errores de login (si los hubo)
};

const reducer = (state = initialState, action) => {
    switch (action.type) {

        case actionTypes.LOGIN_COMPLETED:
            return {
                ...state,
                authenticatedUser: action.authenticatedUser || null,
                authError: null
            };

        case actionTypes.LOGIN_FAILED:
            return {
                ...state,
                authError: action.error || { globalError: 'Login failed' }
            };

        case actionTypes.LOGOUT:
            return initialState;

        default:
            return state;
    }
};

export default reducer;
