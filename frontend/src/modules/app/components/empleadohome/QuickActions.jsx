import React from 'react';
import {Link} from 'react-router-dom';

const QuickActions = () => {
  return (
    <div>
      <h2>Accesos rápidos</h2>
      <div>
        <Link to="/pacientes/buscar">Buscar pacientes</Link>
      </div>
      <div>
        <Link to="/pacientes/create">Nuevo paciente</Link>
      </div>
    </div>
  );
};

export default QuickActions;
