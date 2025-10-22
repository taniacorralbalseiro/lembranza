import { combineReducers } from 'redux';
import app from '../modules/app';
import users from '../modules/users';
import pacientes from "../modules/pacientes";

const rootReducer = combineReducers({
    app: app.reducer,
    users: users.reducer,
    pacientes: pacientes.reducer
});

export default rootReducer;
