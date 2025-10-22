const mod = (state) => state.pacientes;

export const getPacienteSearch = (state) => mod(state).pacienteSearch;
export const getPaciente = (state) => mod(state).paciente;
export const getLastOperation  = (state) => getModule(state).lastOperation;
