import React, { useState, useEffect } from 'react';
import { TextField, Button, Container, Typography, Select, InputLabel, MenuItem } from '@mui/material';
import axios from 'axios';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';

import * as constList from '../addition/Constants.js';

const CreatePlace = ({onClose}) => {
    const [formData, setFormData] = useState({
        name: '',
        address: '',
        max_places: ''
    });

    const handleChange = e => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async e => {
        e.preventDefault();
        try {
            const token = localStorage.getItem('token');
            const url = `${constList.BASE_URL}/api/places`;
            await axios.post(url, formData, {
                headers: {
                Authorization: `Bearer ${token}`
                }
            });

            onClose();
        } catch (error) {
            console.error('Ошибка при создании нового места:', error);
        }
    };

    const handleClickBack = () => {
        onClose();
    }

    return (
        <div>
            <Button onClick={handleClickBack}> <ArrowBackIcon className='my-button'/> </Button>
            <Container maxWidth="sm">
            <Typography variant="h4" gutterBottom>Создать площадку</Typography>
            <form onSubmit={handleSubmit}>
                <TextField
                    fullWidth
                    margin="normal"
                    label="Название"
                    name="name"
                    value={formData.name}
                    onChange={handleChange}
                />
                <TextField
                    fullWidth
                    margin="normal"
                    label="Адрес"
                    name="address"
                    value={formData.address}
                    onChange={handleChange}
                />
                <TextField
                    fullWidth
                    margin="normal"
                    label="Максимальное количество мест"
                    name="max_places"
                    value={formData.max_places}
                    onChange={handleChange}
                />
                
                <Button type="submit" variant="contained" color="warning">Добавить</Button>
            </form>
            </Container>
        </div>
    );
};

export default CreatePlace;
