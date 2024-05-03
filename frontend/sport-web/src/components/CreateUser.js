import React, { useState, useEffect } from 'react';
import { TextField, Button, Container, Typography, Select, InputLabel, MenuItem } from '@mui/material';
import axios from 'axios';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';

import * as constList from '../addition/Constants.js';

const CreateUser = ({onClose}) => {
    const [roles, setRoles] = useState([]);
    const [selectedRoleId, setSelectedRoleId] = useState('');

    const [formData, setFormData] = useState({
        fio: '',
        email: '',
        phone_number: '',
        password: '',
        role: ''
    });

    useEffect(() => {
        const fetchRoles = async () => {
            try {
                const token = localStorage.getItem('token');
                const response = await axios.get(`${constList.BASE_URL}/api/roles`, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                setRoles(response.data);
            } catch (error) {
                console.error('Ошибка при получении списка ролей:', error);
            }
        };
        fetchRoles();
    }, []);

    const handleChange = e => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSelectChange = (e) => {
        setSelectedRoleId(e.target.value);
    };

    const handleSubmit = async e => {
        const user = {
            fio: formData.fio,
            email: formData.email,
            phone_number: formData.phone_number,
            password: formData.password,
            role: selectedRoleId
        }
        e.preventDefault();
        try {
            const token = localStorage.getItem('token');
            const url = `${constList.BASE_URL}/api/users`;
            await axios.post(url, user, {
                headers: {
                Authorization: `Bearer ${token}`
                }
            });

            onClose();
        } catch (error) {
            console.error('Ошибка при создании пользователя:', error);
        }
    };

    const handleClickBack = () => {
        onClose();
    }

    return (
        <div>
            <Button onClick={handleClickBack}> <ArrowBackIcon className='my-button'/> </Button>
            <Container maxWidth="sm">
            <Typography variant="h4" gutterBottom>Создать Пользователя</Typography>
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
                <InputLabel id="select-label">Роль</InputLabel>
                <Select 
                    labelId="select-label"
                    label="Роль"
                    fullWidth={true}
                    variant="outlined"
                    value={selectedRoleId} 
                    onChange={handleSelectChange}>
                    
                    {roles.map(role => (
                        <MenuItem key={role.role_id} value={role.role_id}>
                            {role.name}
                        </MenuItem>
                    ))}
                </Select>
                
                <Button type="submit" variant="contained" color="warning">Добавить</Button>
            </form>
            </Container>
        </div>
    );
};

export default CreateUser;
