// src/modules/pacientes/components/PacienteEdit.jsx
import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useParams, useNavigate } from 'react-router-dom';
import * as actions from '../actions';
import * as selectors from '../selectors';
import PacienteForm from './PacienteForm';

const PacienteEdit = () => {
  const { publicId } = useParams();
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const paciente = useSelector(selectors.getPaciente);
  const [backendErrors, setBackendErrors] = useState(null);

  useEffect(() => {
    dispatch(actions.findPacienteById(publicId));
    return () => dispatch(actions.clearPaciente());
  }, [dispatch, publicId]);

  if (!paciente) return <div className="container mt-4">Cargando…</div>;

  const handleSubmit = (dto) => {
    dispatch(actions.updatePaciente(
      publicId,
      dto,
      (resp) => {

        const id = resp.payload?.publicId;

        navigate(
          id ? `/pacientes/detalle/${id}` : '/pacientes/find-by-centro',
          { state: { updated: true } }
        );
      },
      (errs) => setBackendErrors(errs)
    ));
  };

  const handleDelete = () => {
    if (window.confirm('¿Dar de baja este paciente?')) {
      dispatch(actions.darDeBajaPaciente(publicId, () =>
        navigate('/pacientes/find-by-centro', { state: { deleted: true } })
      ));
    }
  };

  return (
    <PacienteForm
      isEdit
      initial={paciente}
      onSubmit={handleSubmit}
      onCancel={() => navigate(-1)}
      onDelete={handleDelete}
      backendErrors={backendErrors}
    />
  );
};

export default PacienteEdit;
