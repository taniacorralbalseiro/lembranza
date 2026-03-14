// Por convención, el módulo se monta bajo 'users' en el rootReducer
const getModuleState = (state) => state.users;

// Usuario autenticado completo (o null)
export const getUser = (state) => getModuleState(state).authenticatedUser;

// ¿Hay sesión?
export const isLoggedIn = (state) => Boolean(getUser(state));

export const isEmpleado = (state) =>
  getUser(state)?.rol === "EMPLEADO";
// Campos de utilidad (defensivos por si backend aún no los manda)
export const getUserName   = (state) => getUser(state)?.nombre ?? getUser(state)?.email ?? null;
export const getUserEmail  = (state) => getUser(state)?.email ?? null;
export const getUserRole   = (state) => getUser(state)?.rol ?? getUser(state)?.role ?? null;

// Último error de autenticación (para pintar <Errors/> en el Login)
export const getAuthError  = (state) => getModuleState(state).authError;
