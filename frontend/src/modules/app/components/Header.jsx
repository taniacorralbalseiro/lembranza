import { Link } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import users from '../../users';

const Header = () => {
  const dispatch = useDispatch();
  const loggedIn = useSelector(users.selectors.isLoggedIn);
  const userName = useSelector(users.selectors.getUserName);

  const handleLogout = () => dispatch(users.actions.logout());

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark px-4">
      {/* Logo a la izquierda */}
      <Link className="navbar-brand" to="/">Lembranza</Link>

      {/* --- Este div se expande y empuja el resto al extremo derecho --- */}
      <div className="flex-grow-1"></div>

      {/* Bloque derecho */}
      <div className="d-flex align-items-center gap-3">
        {loggedIn && (
          <Link className="btn btn-outline-light btn-sm" to="/pacientes/buscar">
            Pacientes
          </Link>
        )}

        {loggedIn ? (
          <>
            <span className="text-light">Hola, {userName || 'usuario'}</span>
            <button
              className="btn btn-outline-light btn-sm"
              onClick={handleLogout}
            >
              Cerrar sesión
            </button>
          </>
        ) : (
          <Link className="btn btn-outline-light btn-sm" to="/login">
            Iniciar sesión
          </Link>
        )}
      </div>
    </nav>
  );
};

export default Header;
