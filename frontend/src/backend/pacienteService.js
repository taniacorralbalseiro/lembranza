import { appFetch } from './appFetch';

export const create = (dto) =>
  appFetch('POST', '/api/pacientes', dto);

export const update = (publicId, dto) =>
  appFetch('PUT', `/api/pacientes/${publicId}`, dto);

export const get = (publicId) =>
  appFetch('GET', `/api/pacientes/${publicId}`);

export const darDeBaja = (publicId) =>
  appFetch('DELETE', `/api/pacientes/${publicId}`);

export const findByCentro = ({ centroPublicId, page = 0, size = 10 }, onSuccess) => {
  if (!centroPublicId) {
    return Promise.resolve({
      ok: false,
      status: 400,
      payload: { globalError: 'centroPublicId requerido' }
    });
  }

  const params = new URLSearchParams({
    centroPublicId,
    page: String(page),
    size: String(size)
  });

  const path = `/api/pacientes/por-centro?${params.toString()}`;

  return appFetch('GET', path).then(resp => {
    if (resp.ok) {
      const p = resp.payload; // Page Spring
      // Normalizamos para que el resto del frontend no tenga que saber de Spring Page
      const normalized = {
        items: p?.content ?? [],
        existMoreItems: p?.last === false // si no es la última página
      };
      onSuccess?.(normalized);
      // Además, por si quieres acceder a la Page original en algún punto:
      resp.payload = normalized;
    }
    return resp;
  });
};

export const findPacienteById = (publicId, onSuccess) =>
  appFetch('GET', `/api/pacientes/${encodeURIComponent(publicId)}`).then(resp => {
    if (resp.ok) onSuccess?.(resp.payload);
    return resp;
  });
