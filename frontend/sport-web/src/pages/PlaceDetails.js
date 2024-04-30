import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import axios from 'axios';
import { TextField, Button, Container, Typography, Select, InputLabel, MenuItem } from '@mui/material';

import * as constList from '../addition/Constants.js';

const PlaceDetails = () => {
    const { id } = useParams(); // Получаем параметр id из URL
    const [place, setPlace] = useState(null); // Состояние для хранения данных о мероприятии
    const [formData, setFormData] = useState({
        name: '',
        address: '',
        max_places: ''
    });

    useEffect(() => {
        const fetchPlace = async () => {
        try {
            const token = localStorage.getItem('token');
            const url = `${constList.BASE_URL}/api/places/${id}`;
            const response = await axios.get(url, {
                headers: {
                Authorization: `Bearer ${token}` // Добавляем токен в заголовок Authorization
                }
            });
            setPlace(response.data);
            setFormData(response.data);
        } catch (error) {
            console.error('Ошибка при получении информации о мероприятии:', error);
        }
        };

        fetchPlace();
    }, [id]);

    const handleChange = e => {
        const { name, value } = e.target;
        setFormData(prevFormData => ({
            ...prevFormData,
            [name]: value
        }));
    };

    const handleSubmit = async e => {
        e.prplaceDefault();
        const token = localStorage.getItem('token');
        const url = `${constList.BASE_URL}/api/places/${id}`;
        try {
        const response = await axios.put(url, formData, {
            headers: {
            Authorization: `Bearer ${token}`
            }
        });
        } catch (error) {
        }
    };

    return (
        <Container maxWidth="sm">
        <Typography variant="h4" gutterBottom>Спортивная площадка</Typography>
        <form onSubmit={handleSubmit}>
            <TextField
                fullWidth
                margin="normal"
                label="Название"
                name="name"
                value={formData?.name || ''}
                onChange={handleChange}
            />
            <TextField
                fullWidth
                margin="normal"
                label="Адрес"
                name="address"
                value={formData?.address || ''}
                onChange={handleChange}
            />
            <TextField
                fullWidth
                margin="normal"
                label="Максимальное кол-во мест"
                name="max_places"
                type="number"
                value={formData?.max_places || 0}
                onChange={handleChange}
            />
            <Button type="submit" variant="contained" color="primary">Принять изменения</Button>
        </form>
        </Container>
    );
};

export default PlaceDetails;
