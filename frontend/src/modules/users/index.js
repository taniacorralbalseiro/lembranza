import * as actions from './actions';
import * as actionTypes from './actionTypes';
import reducer from './reducer';
import * as selectors from './selectors';

// Re-exporta componentes del módulo si quieres usarlos desde fuera
export { default as Login } from './components/Login';

// Evita warning de export default de objeto literal (estilo apuntes)
export default { actions, actionTypes, reducer, selectors };
