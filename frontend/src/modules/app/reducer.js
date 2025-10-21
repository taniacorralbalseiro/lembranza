import * as actionTypes from './actionTypes';

const initialState = {
    error: null
};

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case actionTypes.ERROR:
            return { ...state, error: action.error };
        default:
            return state;
    }
};

export default reducer;
