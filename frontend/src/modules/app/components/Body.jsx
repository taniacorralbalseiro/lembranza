// src/modules/app/components/Body.jsx
import { Routes, Route, Navigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import users from '../../users';
import Home from './Home';
import { Login } from '../../users';
import {FindPacientes, FindPacientesResult, PacienteDetails, PacienteCreate, PacienteEdit} from "../../pacientes/index.js";


const Body = () => {
  const loggedIn = useSelector(users.selectors.isLoggedIn);
  const isEmpleado = useSelector(users.selectors.isEmpleado);
    return (
        <div className="container mt-4">
            <Routes>
                <Route path="/" element={<Home />} />
              {/* Pública solo si NO estás logueada */}
              {!loggedIn && <Route path="/login" element={<Login />} />}

              {/* Rutas privadas de Pacientes (solo si estás logueada) */}
              {loggedIn && isEmpleado && <Route path="/pacientes/buscar" element={<FindPacientes />} />}
              {loggedIn && <Route path="/pacientes/resultado" element={<FindPacientesResult />} />}
              {loggedIn && <Route path="/pacientes/detalle/:publicId" element={<PacienteDetails />} />}
              {loggedIn && (
                <>
                  <Route path="/pacientes/nuevo" element={<PacienteCreate />} />
                  <Route path="/pacientes/:publicId/editar" element={<PacienteEdit />} />
                </>
              )}
              {/* Catch-all */}
              <Route path="*" element={<Navigate to="/" replace />} />
            </Routes>
        </div>
    );
};

export default Body;
