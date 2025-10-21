import * as actionTypes from './actionTypes';
import backend from '../../backend';

const findByCentroCompleted = (pacienteSearch) => ({
  type: actionTypes.FIND_BY_CENTRO_COMPLETED,
  pacienteSearch,
});

export const clearPacienteSearch = () => ({
  type: actionTypes.CLEAR_PACIENTE_SEARCH,
});

export const findPacientesByCentro = ({ centroPublicId, page = 0, size = 10 }) => (dispatch) => {
  dispatch(clearPacienteSearch());
  return backend.pacienteService.findByCentro({ centroPublicId, page, size },
    result => dispatch(findByCentroCompleted({ criteria: { centroPublicId, page, size }, result }))
  );
};

export const previousPage = (c) => findPacientesByCentro({ ...c, page: c.page - 1 });
export const nextPage     = (c) => findPacientesByCentro({ ...c, page: c.page + 1 });

const findPacienteCompleted = (paciente) => ({
  type: actionTypes.FIND_PACIENTE_COMPLETED,
  paciente,
});

export const clearPaciente = () => ({
  type: actionTypes.CLEAR_PACIENTE,
});

export const findPacienteById = (publicId) => (dispatch) =>
  backend.pacienteService.findPacienteById(publicId, (p) =>
    dispatch(findPacienteCompleted(p))
  );

