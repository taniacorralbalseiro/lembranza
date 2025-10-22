// src/modules/pacientes/components/PacienteForm.jsx
import { useRef, useState } from 'react';

const fieldError = (errors, name) =>
  errors?.fieldErrors?.find(e => e.fieldName === name)?.message;

const PacienteForm = ({
                        initial = {},
                        onSubmit,
                        onCancel,
                        onDelete,
                        backendErrors,
                        isEdit = false,
                      }) => {
  const formRef = useRef(null);

  // Estado controlado
  const [nombre, setNombre] = useState(initial.nombre ?? '');
  const [apellidos, setApellidos] = useState(initial.apellidos ?? '');
  const [email, setEmail] = useState(initial.email ?? '');
  const [telefono, setTelefono] = useState(initial.telefono ?? '');
  const [fechaNacimiento, setFechaNacimiento] = useState(initial.fechaNacimiento ?? '');
  const [centroPublicId, setCentroPublicId] = useState(initial.centroPublicId ?? '');
  const [grupoPublicId, setGrupoPublicId] = useState(initial.grupoPublicId ?? '');
  const [fechaBajaCentro, setFechaBajaCentro] = useState(initial.fechaBajaCentro ?? '');
  // Campos solo para creación
  const [password, setPassword] = useState('');
  const [password2, setPassword2] = useState('');

  const [nif, setNif] = useState(initial.nif ?? '');
  const [fechaAltaCentro, setFechaAltaCentro] = useState(
    initial.fechaAltaCentro ?? new Date().toISOString().slice(0, 10)
  );
  const [estadoCivil, setEstadoCivil] = useState(initial.estadoCivil ?? '');
  const [nivelEducativo, setNivelEducativo] = useState(initial.nivelEducativo ?? '');
  const [situacionLegal, setSituacionLegal] = useState(initial.situacionLegal ?? '');
  const [estadoPaciente, setEstadoPaciente] = useState(initial.estadoPaciente ?? '');

  const handleSubmit = (e) => {
    e.preventDefault();
    const form = formRef.current;

    if (!form.checkValidity()) {
      form.classList.add('was-validated');
      return;
    }

    if (!isEdit && password !== password2) {
      return onSubmit(null, { fieldErrors: [{ fieldName: 'password2', message: 'Las contraseñas no coinciden' }] });
    }

    const dto = isEdit
      ? {
        nombre: nombre.trim(),
        apellidos: apellidos.trim(),
        email: email.trim(),
        telefono: telefono.trim() || null,
        fechaNacimiento: fechaNacimiento || null,
        fechaAltaCentro,
        estadoCivil,
        nivelEducativo,
        situacionLegal,
        estadoPaciente,
        fechaBajaCentro: fechaBajaCentro || null,
      }
      : {
        nombre: nombre.trim(),
        apellidos: apellidos.trim(),
        email: email.trim(),
        telefono: telefono.trim() || null,
        fechaNacimiento: fechaNacimiento || null,
        centroPublicId: centroPublicId || null,
        grupoPublicId: grupoPublicId || null,
        password: password || null,
        nif: nif.trim(),
        fechaAltaCentro,
        estadoCivil,
        nivelEducativo,
        situacionLegal,
        estadoPaciente,
      };

    onSubmit(dto);
  };

  return (
    <div className="container mt-4" style={{ maxWidth: 720 }}>
      <h2 className="mb-3">{isEdit ? 'Editar paciente' : 'Nuevo paciente'}</h2>

      {backendErrors?.globalError && (
        <div className="alert alert-danger">{backendErrors.globalError}</div>
      )}

      <form ref={formRef} className="needs-validation" noValidate onSubmit={handleSubmit}>
        <div className="row g-3">
          {/* Nombre */}
          <div className="col-md-6">
            <label htmlFor="nombre" className="form-label">Nombre</label>
            <input id="nombre" className="form-control" value={nombre}
                   onChange={e => setNombre(e.target.value)} required />
            <div className="invalid-feedback">Requerido.</div>
            {fieldError(backendErrors, 'nombre') && (
              <div className="invalid-feedback d-block">{fieldError(backendErrors, 'nombre')}</div>
            )}
          </div>

          {/* Apellidos */}
          <div className="col-md-6">
            <label htmlFor="apellidos" className="form-label">Apellidos</label>
            <input id="apellidos" className="form-control" value={apellidos}
                   onChange={e => setApellidos(e.target.value)} required />
            <div className="invalid-feedback">Requerido.</div>
            {fieldError(backendErrors, 'apellidos') && (
              <div className="invalid-feedback d-block">{fieldError(backendErrors, 'apellidos')}</div>
            )}
          </div>

          {/* Email */}
          <div className="col-md-6">
            <label htmlFor="email" className="form-label">Email</label>
            <input id="email" type="email" className="form-control" value={email}
                   onChange={e => setEmail(e.target.value)} required />
            <div className="invalid-feedback">Email inválido.</div>
            {fieldError(backendErrors, 'email') && (
              <div className="invalid-feedback d-block">{fieldError(backendErrors, 'email')}</div>
            )}
          </div>

          {/* Teléfono */}
          <div className="col-md-6">
            <label htmlFor="telefono" className="form-label">Teléfono</label>
            <input id="telefono" className="form-control" value={telefono}
                   onChange={e => setTelefono(e.target.value)} />
          </div>

          {/* Fecha nacimiento */}
          <div className="col-md-4">
            <label htmlFor="fechaNacimiento" className="form-label">Fecha nacimiento</label>
            <input id="fechaNacimiento" type="date" className="form-control" value={fechaNacimiento}
                   onChange={e => setFechaNacimiento(e.target.value)} />
          </div>



          <div className="col-md-4">
            <label className="form-label">Estado civil</label>
            <select className="form-select" value={estadoCivil}
                    onChange={(e) => setEstadoCivil(e.target.value)} required>
              <option value="">— Selecciona —</option>
              {/* Ajusta literales EXACTOS a tu enum de backend */}
              <option value="SOLTERO">Soltero/a</option>
              <option value="CASADO">Casado/a</option>
              <option value="DIVORCIADO">Divorciado/a</option>
              <option value="VIUDO">Viudo/a</option>
              <option value="OTRO">Otro/a</option>
            </select>
            <div className="invalid-feedback">Requerido.</div>
            {fieldError(backendErrors, 'estadoCivil') && (
              <div className="invalid-feedback d-block">{fieldError(backendErrors, 'estadoCivil')}</div>
            )}
          </div>

          <div className="col-md-4">
            <label className="form-label">Nivel educativo</label>
            <select className="form-select" value={nivelEducativo}
                    onChange={(e) => setNivelEducativo(e.target.value)} required>
              <option value="">— Selecciona —</option>
              {/* Ajusta a tu enum SIN_ESTUDIOS, BASICO, MEDIO, UNIVERSITARIO, OTRO*/}
              <option value="SIN_ESTUDIOS">Sin Estudios</option>
              <option value="BASICO">Basico</option>
              <option value="MEDIO">Medio</option>
              <option value="UNIVERSITARIO">Universitario</option>
              <option value="OTRO">Otro</option>
            </select>
            <div className="invalid-feedback">Requerido.</div>
            {fieldError(backendErrors, 'nivelEducativo') && (
              <div className="invalid-feedback d-block">{fieldError(backendErrors, 'nivelEducativo')}</div>
            )}
          </div>

          <div className="col-md-4">
            <label className="form-label">Situación legal</label>
            <select className="form-select" value={situacionLegal}
                    onChange={(e) => setSituacionLegal(e.target.value)} required>
              <option value="">— Selecciona —</option>
              {/* Ajusta a tu enum AUTONOMO, INCAPACITADO_PARCIAL, INCAPACITADO_TOTAL */}
              <option value="INCAPACITADO_PARCIAL">Incapacitado Parcial</option>
              <option value="AUTONOMO">Autónomo</option>
              <option value="INCAPACITADO_TOTAL">Incapacitado Total</option>
            </select>
            <div className="invalid-feedback">Requerido.</div>
            {fieldError(backendErrors, 'situacionLegal') && (
              <div className="invalid-feedback d-block">{fieldError(backendErrors, 'situacionLegal')}</div>
            )}
          </div>

          <div className="col-md-4">
            <label className="form-label">Estado paciente</label>
            <select className="form-select" value={estadoPaciente}
                    onChange={(e) => setEstadoPaciente(e.target.value)} required>
              <option value="">— Selecciona —</option>
              {/* Ajusta a tu enum ACTIVO, BAJA, PENDIENTE, DEFUNCION*/}
              <option value="ACTIVO">Activo</option>
              <option value="BAJA">Baja</option>
              <option value="PENDIENTE">Pendiente</option>
              <option value="DEFUNCION">Defunción</option>
            </select>
            <div className="invalid-feedback">Requerido.</div>
            {fieldError(backendErrors, 'estadoPaciente') && (
              <div className="invalid-feedback d-block">{fieldError(backendErrors, 'estadoPaciente')}</div>
            )}
          </div>

          {/* Fecha nacimiento */}
          <div className="col-md-4">
            <label htmlFor="fechaBajaCentro" className="form-label">Fecha Baja</label>
            <input id="fechaBajaCentro" type="date" className="form-control" value={fechaBajaCentro}
                   onChange={e => setFechaBajaCentro(e.target.value)} />
          </div>

          {!isEdit && (
            <>
              {/* NIF */}
              <div className="col-md-4">
                <label htmlFor="nif" className="form-label">NIF</label>
                <input id="nif" className="form-control" value={nif}
                       onChange={e => setNif(e.target.value)} required />
              </div>

              {/* Contraseñas */}
              <div className="col-md-6">
                <label htmlFor="password" className="form-label">Contraseña</label>
                <input id="password" type="password" className="form-control" value={password}
                       onChange={e => setPassword(e.target.value)} required minLength={4} />
              </div>
              <div className="col-md-6">
                <label htmlFor="password2" className="form-label">Repite contraseña</label>
                <input id="password2" type="password" className="form-control" value={password2}
                       onChange={e => setPassword2(e.target.value)} required minLength={4} />
              </div>

              <div className="col-md-4">
                <label htmlFor="centroPublicId" className="form-label">Centro (publicId)</label>
                <input id="centroPublicId" className="form-control" value={centroPublicId}
                       onChange={e => setCentroPublicId(e.target.value)} required />
                <div className="invalid-feedback">Requerido.</div>
              </div>

              <div className="col-md-4">
                <label htmlFor="grupoPublicId" className="form-label">Grupo (publicId)</label>
                <input id="grupoPublicId" className="form-control" value={grupoPublicId}
                       onChange={e => setGrupoPublicId(e.target.value)} required />
                <div className="invalid-feedback">Requerido.</div>
              </div>

            </>
          )}
        </div>

        <div className="d-flex gap-2 mt-4">
          <button type="submit" className="btn btn-primary">
            {isEdit ? 'Guardar cambios' : 'Crear paciente'}
          </button>
          <button type="button" className="btn btn-secondary" onClick={onCancel}>Cancelar</button>
          {isEdit && (
            <button type="button" className="btn btn-outline-danger ms-auto" onClick={onDelete}>
              Dar de baja
            </button>
          )}
        </div>
      </form>
    </div>
  );
};

export default PacienteForm;
