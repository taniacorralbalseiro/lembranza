// src/modules/users/components/Login.jsx
import { useState, useRef } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import * as actions from '../actions';

const Login = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const formRef = useRef(null);

    const [userName, setUserName] = useState('');
    const [password, setPassword] = useState('');
    const [backendErrors, setBackendErrors] = useState(null);

    const handleSubmit = async (e) => {
        e.preventDefault();

        const form = formRef.current;
        if (!form.checkValidity()) {
            setBackendErrors(null);
            form.classList.add('was-validated');
            return;
        }

        const { ok, error } = await dispatch(actions.login(userName, password));
        if (ok) {
            navigate('/');
        } else {
            setBackendErrors(error);
        }
    };

    return (
        <div className="container mt-4" style={{ maxWidth: 420 }}>
            <h2 className="mb-4">Iniciar sesión</h2>

            {backendErrors?.globalError && (
                <div className="alert alert-danger" role="alert">
                    {backendErrors.globalError}
                </div>
            )}

            <form
                ref={formRef}
                className="needs-validation"
                noValidate
                onSubmit={handleSubmit}
            >
                <div className="mb-3">
                    <label htmlFor="userName" className="form-label">Email / Usuario</label>
                    <input
                        id="userName"
                        type="email"
                        className="form-control"
                        value={userName}
                        onChange={(e) => setUserName(e.target.value)}
                        placeholder="tu@email.com"
                        required
                        autoFocus
                    />
                    <div className="invalid-feedback">Introduce un email válido.</div>
                    {backendErrors?.fieldErrors?.find(e => e.fieldName === 'email') &&
                        <div className="invalid-feedback d-block">
                            {backendErrors.fieldErrors.find(e => e.fieldName === 'email').message}
                        </div>
                    }
                </div>

                <div className="mb-3">
                    <label htmlFor="password" className="form-label">Contraseña</label>
                    <input
                        id="password"
                        type="password"
                        className="form-control"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        placeholder="••••••••"
                        required
                        minLength={4}
                    />
                    <div className="invalid-feedback">La contraseña es obligatoria.</div>
                    {backendErrors?.fieldErrors?.find(e => e.fieldName === 'password') &&
                        <div className="invalid-feedback d-block">
                            {backendErrors.fieldErrors.find(e => e.fieldName === 'password').message}
                        </div>
                    }
                </div>

                <button type="submit" className="btn btn-primary w-100">
                    Entrar
                </button>
            </form>
        </div>
    );
};

export default Login;
