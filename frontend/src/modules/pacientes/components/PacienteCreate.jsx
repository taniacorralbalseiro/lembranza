// src/modules/pacientes/components/PacienteCreate.jsx
import { useState } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import * as actions from '../actions';
import PacienteForm from './PacienteForm';

const PacienteCreate = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [backendErrors, setBackendErrors] = useState(null);

  const handleSubmit = (dto) => {
    dispatch(actions.createPaciente(
      dto,
      (resp) => {

        const id = resp.payload?.publicId;

        navigate(
          id ? `/pacientes/detalle/${id}` : '/pacientes/find-by-centro',
          { state: id ? { created: true } : { flash: 'Paciente creado' } }
        );
      },
      (errs) => setBackendErrors(errs)
    ));
  };

  return (
    <PacienteForm
      isEdit={false}
      onSubmit={handleSubmit}
      onCancel={() => navigate(-1)}
      backendErrors={backendErrors}
    />
  );
};

export default PacienteCreate;
