LEMBRANZA — Aplicación Web para Gestión de Pacientes en Centros de Estimulación Cognitiva

Aplicación web desarrollada como parte del Trabajo Fin de Grado.
El proyecto incluye backend en Spring Boot + PostgreSQL y un frontend SPA en React con Redux.

Actualmente están implementadas las siguientes funcionalidades:

FUNCIONALIDADES IMPLEMENTADAS

A) Autenticación

Inicio de sesión con email/usuario y contraseña.

Validación HTML5 en el formulario.

Manejo de errores globales y por campo devueltos por el backend.

Almacenamiento del token JWT en sessionStorage.

B) Gestión de Pacientes

Búsqueda de pacientes por centro (publicId del centro).

Resultados paginados (anterior / siguiente).

Visualización del detalle de un paciente.

Creación de pacientes mediante formulario completo.

Edición de pacientes (campos personales, email, estado, etc.).

Baja de pacientes (soft delete).

Mensajes flash tras crear o editar.

Validación HTML5 y procesamiento de errores del backend.

C) Formularios unificados

Un único componente PacienteForm para creación y edición.

Validación programática, campos controlados y manejo de fieldErrors.

D) Arquitectura Frontend

Módulos: app, users, pacientes, common.

Redux Toolkit para la gestión del estado global.

Redux Thunk para acciones asíncronas (peticiones HTTP).

React Router para navegación y rutas dinámicas.

Bootstrap para estilos y validación visual.

ESTRUCTURA RESUMIDA DEL FRONTEND

src/
backend/ Capa de acceso a servicios (fetch + JWT)
modules/
users/ Login y acciones de autenticación
pacientes/ CRUD, búsqueda, paginación y detalle
app/ Layout general
common/ Componentes compartidos
store/ rootReducer + configuración Redux
i18n/ Mensajes de idioma (parcial)
index.js

EJECUCIÓN DEL FRONTEND

npm install

Configurar archivo .env.development:
REACT_APP_BACKEND_URL=http://localhost:8080

npm start

ESTADO ACTUAL DEL BACKEND

Endpoints REST para login de usuarios.

Endpoints para CRUD de pacientes.

Lógica de validación y retorno de errores (global y por campo).

Gestión de pacientes activa/baja.

Integración con base de datos PostgreSQL.

AUTORA

Tania Corral Balseiro