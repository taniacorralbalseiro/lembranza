import { useDispatch, useSelector } from 'react-redux';
import * as selectors from '../selectors';
import * as actions from '../actions';
import { Link } from 'react-router-dom';

const FindPacientesResult = () => {
  const dispatch = useDispatch();
  const pacienteSearch = useSelector(selectors.getPacienteSearch);

  if (!pacienteSearch) return null;

  const { criteria, result } = pacienteSearch;
  const pacientes = result?.items ?? result ?? [];
  const existMore = result?.existMoreItems ?? false;

  return (
    <div className="container mt-4">
      <h2 className="mb-3">Pacientes del centro {criteria.centroPublicId}</h2>

      {pacientes.length === 0 ? (
        <div className="alert alert-warning">No se encontraron pacientes.</div>
      ) : (
        <table className="table table-striped">
          <thead>
          <tr>
            <th>Nombre</th>
            <th>Apellidos</th>
            <th>Email</th>
            <th></th>
          </tr>
          </thead>
          <tbody>
          {pacientes.map((p) => (
            <tr key={p.publicId}>
              <td>{p.nombre}</td>
              <td>{p.apellidos}</td>
              <td>{p.email}</td>
              <td className="text-end">
                <Link
                  className="btn btn-sm btn-outline-primary"
                  to={`/pacientes/detalle/${encodeURIComponent(p.publicId)}`}
                >
                  Ver detalle
                </Link>
              </td>
            </tr>
          ))}
          </tbody>
        </table>
      )}

      <div className="d-flex justify-content-between">
        <button
          className="btn btn-outline-secondary"
          disabled={criteria.page <= 0}
          onClick={() => dispatch(actions.previousPage(criteria))}
        >
          ← Anterior
        </button>
        <button
          className="btn btn-outline-secondary"
          disabled={!existMore}
          onClick={() => dispatch(actions.nextPage(criteria))}
        >
          Siguiente →
        </button>
      </div>
    </div>
  );
};

export default FindPacientesResult;
