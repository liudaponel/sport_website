import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { TextField, Button } from '@mui/material';
import axios from 'axios';
import * as constList from '../addition/Constants.js';

const ResetPassword = () => {
    const [password, setPassword] = useState('');
    const {token} = useParams();
    const [redirectToEvents, setRedirectToEvents] = useState(false);
    let navigate = useNavigate();

    const handleChange = (event) => {
        setPassword(event.target.value);
    };

    const handleSubmit = async () => {
        try {
            const url = `${constList.BASE_URL}/api/auth/change-password?token=${token}`;
            const newPassword = {
                newPassword: password
            }
            await axios.post(url, newPassword);
            setRedirectToEvents(true);
        } catch (error) {
        console.error('Ошибка:', error);
        }
    };

    useEffect(() => {
        if (redirectToEvents){
           return navigate("/");
        }
    },[redirectToEvents]);

    return (
        <div>
        <TextField
            label="Новый пароль"
            type="password"
            value={password}
            onChange={handleChange}
            fullWidth
            margin="normal"
        />
        <Button variant="contained" color="primary" onClick={handleSubmit}>
            Сохранить
        </Button>
        </div>
    );
};

export default ResetPassword;
