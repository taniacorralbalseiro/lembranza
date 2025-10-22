// src/modules/app/components/Body.jsx
import { Routes, Route, Navigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import users from '../../users';
import { Login } from '../../users';
import {FindPacientes, FindPacientesResult, PacienteDetails, PacienteCreate, PacienteEdit} from "../../pacientes/index.js";

const Home = () => (
    <div className="container mt-5 text-center">
        <h2>Bienvenido a Lembranza</h2>
        <p>Todo OK ✅</p>
    </div>
);

const Body = () => {
    const loggedIn = useSelector(users.selectors.isLoggedIn);

    return (
        <div className="container mt-4">
            <Routes>
                <Route path="/" element={<Home />} />
              {/* Pública solo si NO estás logueada */}
              {!loggedIn && <Route path="/login" element={<Login />} />}

              {/* Rutas privadas de Pacientes (solo si estás logueada) */}
              {loggedIn && <Route path="/pacientes/buscar" element={<FindPacientes />} />}
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
