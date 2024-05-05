import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import axios from 'axios';
import { TextField, Button, Container, Typography, Select, InputLabel, MenuItem } from '@mui/material';

import * as constList from '../addition/Constants.js';
import UserEventsList from '../components/UserEventsList';

const UserDetails = () => {
    const [roles, setRoles] = useState([]);
    const [selectedRoleId, setSelectedRoleId] = useState('');

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

    const [events, setEvents] = useState([]);

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
            setSelectedRoleId(response.data.role.role_id);
            
            console.log(response.data);
        } catch (error) {
            console.error('Ошибка при получении информации о пользователе:', error);
        }
        };
        fetchUser();

        const fetchEvents = async () => {
            try {
                const token = localStorage.getItem('token');
                const url = `${constList.BASE_URL}/api/registrations/user/${id}`;
                const response2 = await axios.get(url, {
                    headers: {
                        Authorization: `Bearer ${token}` // Добавляем токен в заголовок Authorization
                    }
                });
                setEvents(response2.data);
                console.log(response2.data);
            } catch (error) {
                console.error('Ошибка при получении информации о пользователе:', error);
            }
            };
        fetchEvents();
    }, [id]);

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
        const { name, value } = e.target;
        setFormData(prevFormData => ({
            ...prevFormData,
            [name]: value
        }));
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

    const makeCoach = async e => {
        const coach = {
            user_id: id
        }
        e.preventDefault();
        const token = localStorage.getItem('token');
        const url = `${constList.BASE_URL}/api/coaches/${id}`;
        try {
        const response = await axios.put(url, coach, {
            headers: {
            Authorization: `Bearer ${token}`
            }
        });
        } catch (error) {
        }
    }

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
            <div style={{ margin: '10px'}}>
            <Button type="submit" variant="contained" color="warning">Принять изменения</Button>
            <Button variant="contained" color="warning" style={{ marginLeft: '10px' }} onClick={makeCoach}>Сделать Тренером</Button>
            </div>
        </form>
        <UserEventsList events={events} />
        </Container>
    );
};

export default UserDetails;

