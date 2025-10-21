import { useState, useRef } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import * as actions from '../actions';

const FindPacientes = () => {
  const [centroPublicId, setCentroPublicId] = useState('');
  const formRef = useRef(null);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    const form = formRef.current;

    if (!form.checkValidity()) {
      form.classList.add('was-validated');
      return;
    }

    await dispatch(actions.findPacientesByCentro({ centroPublicId, page: 0 }));
    navigate('/pacientes/resultado');
  };

  return (
    <div className="container mt-4" style={{ maxWidth: 500 }}>
      <h2>Buscar pacientes por centro</h2>
      <form ref={formRef} className="needs-validation" noValidate onSubmit={handleSubmit}>
        <div className="mb-3">
          <label htmlFor="centroPublicId" className="form-label">
            Public ID del centro
          </label>
          <input
            id="centroPublicId"
            className="form-control"
            value={centroPublicId}
            onChange={(e) => setCentroPublicId(e.target.value)}
            required
            placeholder="Ej: 7d8398c2-6b9e-4f7d-9b0f-f7c1e85a2f0c"
          />
          <div className="invalid-feedback">Debes introducir el publicId del centro.</div>
        </div>
        <button type="submit" className="btn btn-primary w-100">
          Buscar
        </button>
      </form>
    </div>
  );
};

export default FindPacientes;
