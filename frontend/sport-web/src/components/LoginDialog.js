import React, { useState } from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import TextField from '@mui/material/TextField';
import axios from 'axios'
import HighlightOffIcon from '@mui/icons-material/HighlightOff';

import * as constList from '../addition/Constants.js';
import '../styles/LoginDialog.css'

function decodeAndSaveToken(token) {
    const [headerEncoded, payloadEncoded] = token.split('.');
    const base64 = payloadEncoded.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
    const user = JSON.parse(jsonPayload);

    localStorage.setItem('token', token);
    localStorage.setItem('email', user.sub);
    localStorage.setItem('role', user.role[0].authority);
}

function LoginDialog({ open, onClose }) {
    const [fio, setFio] = useState('');
    const [email, setEmail] = useState('');
    const [phone, setPhone] = useState('');
    const [password, setPassword] = useState('');
    const [isRegistering, setIsRegistering] = useState(false);

    const [errors, setErrors] = useState({});

    const handleLogin = () => {

        const urlSignIn = `${constList.BASE_URL}/api/auth/authenticate`;
        const user = {
            email: email,
            password: password,
        };

        axios.post(urlSignIn, user)
        .then(response => {
            console.log('Response: ', response)
            alert("Вход выполнен успешно!");
            decodeAndSaveToken(response.data.access_token);
            onClose();
        })
        .catch(error => {
            console.error('Error:', error);
            console.log(error.response.data);
            alert(error.response.data.message);
        });
    };

    const handleRegister = () => {
        const urlSignUp = `${constList.BASE_URL}/api/auth/register`;
        const user = {
            fio: fio,
            email: email,
            phone_number: phone,
            password: password,
        };

        axios.post(urlSignUp, user)
        .then(response => {
            console.log('Response: ', response);
            alert("Регистрация прошла успешно!");
            decodeAndSaveToken(response.data.access_token);
            onClose();
        })
        .catch(error => {
            console.error('Error:', error);
            if (error.response.data.message) {
                alert(error.response.data.message);
            }
            if (error.response.data.violations) {
                const newErrors = {};
                error.response.data.violations.forEach(violation => {
                  newErrors[violation.fieldName] = violation.message;
                });
                setErrors(newErrors);
            }
        });
    };

    return (
        <Dialog open={open} onClose={onClose}>
            <DialogTitle sx={{ fontSize: 20, display: 'flex', justifyContent: 'space-between' }}>
                <span>{isRegistering ? 'Регистрация' : 'Вход'}</span>
                <Button onClick={onClose}><HighlightOffIcon /></Button>
            </DialogTitle>

        <DialogContent>
            <DialogContentText>
            {isRegistering
                ? 'Введите свои данные для регистрации:'
                : 'Введите имя или эл.почту и пароль:'}
            </DialogContentText>
            {isRegistering > 0 && (<TextField
            autoFocus
            margin="dense"
            label="ФИО"
            value={fio}
            onChange={(e) => setFio(e.target.value)}
            fullWidth
            inputProps={{
                style: {
                height: "30px",
                fontSize: 20
                },
            }}
            />
            )}
            {errors.fio && <div className="red-text">{errors.fio}</div>}
            <TextField
            autoFocus
            margin="dense"
            label="Электронная почта"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            fullWidth
            inputProps={{
                style: {
                height: "30px",
                fontSize: 20
                },
            }}
            />
            {errors.email && <div className="red-text">{errors.email}</div>}
            {isRegistering > 0 && (<TextField
            autoFocus
            margin="dense"
            label="Номер телефона"
            value={phone}
            onChange={(e) => setPhone(e.target.value)}
            fullWidth
            inputProps={{
                style: {
                height: "30px",
                fontSize: 20
                },
            }}
            />
            )}
            {errors.phone_number && <div className="red-text">{errors.phone_number}</div>}
            <TextField
            margin="dense"
            label="Пароль"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            fullWidth
            inputProps={{
                style: {
                height: "30px",
                fontSize: 20
                },
            }}
            />
            {errors.password && <div className="red-text">{errors.password}</div>}
        </DialogContent>
        <DialogActions>
            {isRegistering ? (
            <Button onClick={handleRegister} variant="contained" color="primary" sx={{ fontSize: 16 }}>
                Зарегистрироваться
            </Button>
            ) : (
            <Button onClick={handleLogin} variant="contained" color="primary" sx={{ fontSize: 16 }}>
                Войти
            </Button>
            )}
            {!isRegistering ? (
            <Button onClick={() => setIsRegistering(true)} sx={{ fontSize: 16 }}>Зарегистрироваться</Button>
            ) : (
            <Button onClick={() => setIsRegistering(false)} sx={{ fontSize: 16 }}>Войти</Button>
            )}
        </DialogActions>
        </Dialog>
  );
}

export default LoginDialog;
