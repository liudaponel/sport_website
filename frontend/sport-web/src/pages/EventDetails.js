import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import axios from 'axios';
import { TextField, Button, Container, Typography, duration } from '@mui/material';

import * as constList from '../addition/Constants.js';

const EventDetails = () => {
  const { id } = useParams(); // Получаем параметр id из URL
  const [event, setEvent] = useState(null); // Состояние для хранения данных о мероприятии
  const [formData, setFormData] = useState({
    name: '',
    place: '',
    start_time: '',
    taken_places: '',
    max_places: '',
    duration_hours : '',
    duration_minutes: '',
    price: '',
    coach: {
        user_id: ''
      }
  });

    useEffect(() => {
        const fetchEvent = async () => {
        try {
            const token = localStorage.getItem('token');
            const url = `${constList.BASE_URL}/api/events/${id}`;
            const response = await axios.get(url, {
                headers: {
                Authorization: `Bearer ${token}` // Добавляем токен в заголовок Authorization
                }
            });
            setEvent(response.data);
            setFormData(response.data); // Заполняем форму начальными данными о мероприятии
        } catch (error) {
            console.error('Ошибка при получении информации о мероприятии:', error);
        }
        };

        fetchEvent();
    }, [id]);

    const handleChange = e => {
        const { name, value } = e.target;
        console.log(name, value);
    
        // Если изменяется значение coach.user_id
        if (name === 'coach.user_id') {
            setFormData(prevFormData => ({
                ...prevFormData,
                coach: {
                ...prevFormData.coach,
                user_id: value
                }
            }));
        } else {
        // Если изменяется другое значение в форме
            setFormData(prevFormData => ({
                ...prevFormData,
                [name]: value
            }));
        }
    };
    

    const handleSubmit = async e => {
        const event = {
            name: formData.name,
            start_time: formData.start_time,
            taken_places: formData.taken_places,
            max_places: formData.max_places,
            duration_hours: formData.duration_hours,
            duration_minutes: formData.duration_minutes,
            price: formData.price,
            place: formData.place.place_id,
            coach: formData.coach.user_id
        }
        e.preventDefault();
        const token = localStorage.getItem('token');
        const url = `${constList.BASE_URL}/api/events/${id}`;
        try {
        const response = await axios.put(url, event, {
            headers: {
            Authorization: `Bearer ${token}`
            }
        });
        console.log(response);
        } catch (error) {
        }
    };

    return (
        <Container maxWidth="sm">
        <Typography variant="h4" gutterBottom>Мероприятие</Typography>
        <form onSubmit={handleSubmit}>
            <TextField
            fullWidth
            margin="normal"
            label="Название"
            name="name"
            value={formData.name}
            onChange={handleChange}
            readOnly={localStorage.getItem('role') !== 'Администратор'}
            />
            <TextField
            fullWidth
            margin="normal"
            label="Место"
            name="place"
            value={`${formData.place.name}, ${formData.place.address}`}
            readOnly
            />
            <TextField
            fullWidth
            margin="normal"
            label="Время начала"
            name="start_time"
            type="datetime-local"
            value={formData.start_time.slice(0, 16)}
            onChange={handleChange}
            readOnly={localStorage.getItem('role') !== 'Администратор'}
            />
            <TextField
            fullWidth
            margin="normal"
            label="Длительность часы"
            name="duration_hours"
            type="number"
            value={formData.duration_hours}
            onChange={handleChange}
            readOnly={localStorage.getItem('role') !== 'Администратор'}
            />
            <TextField
            fullWidth
            margin="normal"
            label="Длительность минуты"
            name="duration_minutes"
            type="number"
            value={formData.duration_minutes}
            onChange={handleChange}
            readOnly={localStorage.getItem('role') !== 'Администратор'}
            />
            <TextField
            fullWidth
            margin="normal"
            label="Стоимость"
            name="price"
            type="number"
            value={formData.price}
            onChange={handleChange}
            readOnly={localStorage.getItem('role') !== 'Администратор'}
            />
            <TextField
            fullWidth
            margin="normal"
            label="Тренер"
            name="coach"
            value={formData.coach.user ? formData.coach.user.fio : ''}
            onChange={handleChange}
            readOnly
            />
            {localStorage.getItem('role') == 'Администратор' && 
            (<TextField
            fullWidth
            margin="normal"
            label="Тренер id"
            name="coach.user_id"
            type="number"
            value={formData.coach.user_id}
            onChange={handleChange}
            />)}
            <TextField
            fullWidth
            margin="normal"
            label="Занято мест"
            name="taken_places"
            type="number"
            value={formData.taken_places}
            onChange={handleChange}
            readOnly={localStorage.getItem('role') !== 'Администратор'}
            />
            <TextField
            fullWidth
            margin="normal"
            label="Максимальное количество мест"
            name="max_places"
            type="number"
            value={formData.max_places}
            onChange={handleChange}
            readOnly={localStorage.getItem('role') !== 'Администратор'}
            />
            <Button type="submit" variant="contained" color="primary">Добавить</Button>
        </form>
        </Container>
    );
};

export default EventDetails;
