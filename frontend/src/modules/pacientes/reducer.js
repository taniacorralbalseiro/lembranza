import { combineReducers } from 'redux';
import * as actionTypes from './actionTypes';

const initial = {
  pacienteSearch: null,
  paciente: null,
  lastOperation: null
};

const pacienteSearch = (state = initial.pacienteSearch, action) => {
  switch (action.type) {
    case actionTypes.FIND_BY_CENTRO_COMPLETED:
      return action.pacienteSearch;
    case actionTypes.CLEAR_PACIENTE_SEARCH:
      return null;
    default:
      return state;
  }
};

const paciente = (state = initial.paciente, action) => {
  switch (action.type) {
    case actionTypes.FIND_PACIENTE_COMPLETED:
      return action.paciente;
    case actionTypes.CLEAR_PACIENTE:
      return null;
    default:
      return state;
  }
};

const lastOperation = (state = initial.lastOperation, action) => {
  switch (action.type) {
    case actionTypes.SAVE_PACIENTE_COMPLETED:
      // si llega con publicId asumimos update; si no, alta
      return action.paciente?.publicId ? 'updated' : 'created';
    case actionTypes.DELETE_PACIENTE_COMPLETED:
      return 'deleted';
    case actionTypes.CLEAR_PACIENTE:
      return null;
    default:
      return state;
  }
};
export default combineReducers({ pacienteSearch, paciente, lastOperation });
