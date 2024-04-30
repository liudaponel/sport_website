import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import axios from 'axios';
import { TextField, Button, Container, Typography, Select, InputLabel, MenuItem } from '@mui/material';

import * as constList from '../addition/Constants.js';

const UserDetails = () => {
    const { id } = useParams(); // Получаем параметр id из URL
    const [user, setUser] = useState(null); // Состояние для хранения данных о мероприятии
    const [formData, setFormData] = useState({
        fio: '',
        email: '',
        phone_number: '',
        password: '',
        role: '',
        events: ''
    });

    useEffect(() => {
        const fetchUser = async () => {
        try {
            const token = localStorage.getItem('token');
            const url = `${constList.BASE_URL}/api/users/${id}`;
            const response = await axios.get(url, {
                headers: {
                    Authorization: `Bearer ${token}` // Добавляем токен в заголовок Authorization
                }
            });
            setUser(response.data);
            setFormData(response.data); // Заполняем форму начальными данными
            
            console.log(response.data);
        } catch (error) {
            console.error('Ошибка при получении информации о пользователе:', error);
        }
        };

        fetchUser();
    }, [id]);

    const handleChange = e => {
        const { name, value } = e.target;
        setFormData(prevFormData => ({
            ...prevFormData,
            [name]: value
        }));
    };

    const handleSubmit = async e => {
        const user = {
            fio: formData.fio,
            email: formData.email,
            phone_number: formData.phone_number,
            password: formData.password,
            role: formData.role.role_id
        }
        e.preventDefault();
        const token = localStorage.getItem('token');
        const url = `${constList.BASE_URL}/api/users/${id}`;
        try {
        const response = await axios.put(url, user, {
            headers: {
            Authorization: `Bearer ${token}`
            }
        });
        } catch (error) {
        }
    };

    return (
        <Container maxWidth="sm">
        <Typography variant="h4" gutterBottom>Пользователь</Typography>
        <form onSubmit={handleSubmit}>
            <TextField
                fullWidth
                margin="normal"
                label="ФИО"
                name="fio"
                value={formData.fio}
                onChange={handleChange}
            />
            <TextField
                fullWidth
                margin="normal"
                label="Email"
                name="email"
                value={formData.email}
                onChange={handleChange}
            />
            <TextField
                fullWidth
                margin="normal"
                label="Номер телефона"
                name="phone_number"
                value={formData.phone_number}
                onChange={handleChange}
            />
            <TextField
                fullWidth
                margin="normal"
                label="Пароль"
                name="password"
                value={formData.password}
                onChange={handleChange}
            />
            <TextField
                fullWidth
                margin="normal"
                label="Роль"
                name="role"
                value={formData.role?.name || ''}
                onChange={handleChange}
            />
            <Button type="submit" variant="contained" color="primary">Принять изменения</Button>
        </form>
        </Container>
    );
};

export default UserDetails;

