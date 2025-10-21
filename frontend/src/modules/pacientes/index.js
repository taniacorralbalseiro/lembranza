import * as actions from './actions';
import * as actionTypes from './actionTypes';
import reducer from './reducer';
import * as selectors from './selectors';

export { default as FindPacientes } from './components/FindPacientes';
export { default as FindPacientesResult } from './components/FindPacientesResult';
export { default as PacienteDetails } from './components/PacienteDetails';

export default { actions, actionTypes, reducer, selectors };
