import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useParams, Link } from 'react-router-dom';
import * as actions from '../actions';
import * as selectors from '../selectors';

const PacienteDetails = () => {
  const { id } = useParams();
  const dispatch = useDispatch();
  const paciente = useSelector(selectors.getPaciente);

  useEffect(() => {
    dispatch(actions.findPacienteById(id));
    return () => dispatch(actions.clearPaciente());
  }, [id, dispatch]);

  if (!paciente) return null;

  return (
    <div className="container mt-4" style={{ maxWidth: 700 }}>
      <Link to="/pacientes/buscar" className="btn btn-link p-0 mb-3">
        ← Volver
      </Link>
      <div className="card">
        <div className="card-header">
          <strong>{paciente.nombre} {paciente.apellidos}</strong>
        </div>
        <div className="card-body">
          <p><b>Email:</b> {paciente.email}</p>
          <p><b>Teléfono:</b> {paciente.telefono}</p>
          <p><b>Estado:</b> {paciente.estadoPaciente}</p>
          <p><b>Centro:</b> {paciente.centro?.nombre ?? '—'}</p>
        </div>
      </div>
    </div>
  );
};

export default PacienteDetails;
