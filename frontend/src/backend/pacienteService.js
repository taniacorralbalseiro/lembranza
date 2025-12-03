import { appFetch } from './appFetch';

export const create = (dto) =>
  appFetch('POST', '/api/pacientes', dto);

export const update = (publicId, dto) =>
  appFetch('PUT', `/api/pacientes/${publicId}`, dto);

export const get = (publicId) =>
  appFetch('GET', `/api/pacientes/${publicId}`);

export const darDeBaja = (publicId) =>
  appFetch('DELETE', `/api/pacientes/${publicId}`);

const normalizePage = (pageResponse) => ({
  items: pageResponse?.content ?? [],
  existMoreItems: pageResponse?.last === false,
  totalElements: pageResponse?.totalElements ?? 0,
  currentPage: pageResponse?.number ?? 0,
});

export const findByCentro = ({ centroPublicId, page = 0, size = 10 }, onSuccess) => {
  if (!centroPublicId) {
    return Promise.resolve({
      ok: false,
      status: 400,
      payload: { globalError: 'centroPublicId requerido' },
    });
  }

  const params = new URLSearchParams({ centroPublicId, page: String(page), size: String(size) });
  const path = `/api/pacientes/por-centro?${params.toString()}`;

  return appFetch('GET', path).then((resp) => {
    if (resp.ok) {
      resp.payload = normalizePage(resp.payload);
      onSuccess?.(resp.payload);
    }
    return resp;
  });
};

export const findPacienteById = (publicId, onSuccess) =>
  appFetch('GET', `/api/pacientes/${encodeURIComponent(publicId)}`).then(resp => {
    if (resp.ok) onSuccess?.(resp.payload);
    return resp;
  });
