import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useParams, Link, useLocation, useNavigate } from 'react-router-dom';
import * as actions from '../actions';
import * as selectors from '../selectors';
import users from '../../users';

const PacienteDetails = () => {
  const { publicId } = useParams();
  const dispatch = useDispatch();
  const paciente = useSelector(selectors.getPaciente);
  const navigate = useNavigate();
  const location = useLocation();

  // 1) leer marca de navegación
  const [showFlash, setShowFlash] = useState(Boolean(location.state?.created));


  useEffect(() => {
    dispatch(actions.findPacienteById(publicId));
    return () => dispatch(actions.clearPaciente());
  }, [publicId, dispatch]);


  // 2) consumir la marca para que no reaparezca al volver atrás/adelante
  useEffect(() => {
    if (location.state?.created) {
      // elimina la marca del history (sin cambiar la URL)
      window.history.replaceState(
        { ...window.history.state, usr: { ...location.state, created: false } },
        ''
      );
    }
  }, [location.state]);

  // 3) autocierre opcional a los 3s
  useEffect(() => {
    if (!showFlash) return;
    const t = setTimeout(() => setShowFlash(false), 3000);
    return () => clearTimeout(t);
  }, [showFlash]);

  if (!paciente) {
    return (
      <div className="container mt-4" style={{ maxWidth: 700 }}>
        <p>Cargando…</p>
      </div>
    );
  }
  return (
    <div className="container mt-4" style={{ maxWidth: 700 }}>
      {showFlash && (
        <div className="alert alert-success" role="alert">
          Paciente creado correctamente.
        </div>
      )}
      <Link to="/pacientes/buscar" className="btn btn-link p-0 mb-3">
        ← Volver
      </Link>
      <div className="card">
        <div className="card-header">
          <strong>{paciente.nombre} {paciente.apellidos}</strong>
        </div>
        <div className="card-body">
          <p><b>PublicId:</b> {paciente.publicId}</p>
          <p><b>Email:</b> {paciente.email}</p>
          <p><b>Teléfono:</b> {paciente.telefono}</p>
          <div className="d-flex gap-2">
            <button className="btn btn-outline-primary"
                    onClick={() => navigate(`/pacientes/${publicId}/editar`)}>Editar</button>
            <button className="btn btn-secondary" onClick={() => navigate(-1)}>Volver</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default PacienteDetails;
