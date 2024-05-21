import React, { useState, useEffect } from 'react';
import { TextField, Button, Container, Typography, Select, InputLabel, MenuItem } from '@mui/material';
import axios from 'axios';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';

import * as constList from '../addition/Constants.js';

const CreateEvent = ({onClose}) => {
    const [places, setPlaces] = useState([]);
    const [selectedPlaceId, setSelectedPlaceId] = useState('');
    const [coaches, setCoaches] = useState([]);
    const [selectedCoachId, setSelectedCoachId] = useState('');

    const [formData, setFormData] = useState({
        name: '',
        start_time: '',
        taken_places: '',
        max_places: '',
        duration_hours: '',
        duration_minutes: '',
        price: ''
    });

    useEffect(() => {
        const fetchPlaces = async () => {
            try {
                const token = localStorage.getItem('token');
                const url = `${constList.BASE_URL}/api/places`;
                const response = await axios.get(url, {
                    headers: {
                    Authorization: `Bearer ${token}` // Добавляем токен в заголовок Authorization
                    }
                });
                setPlaces(response.data);
            } catch (error) {
                console.error('Ошибка при получении списка мест:', error);
            }
            try{
                const token = localStorage.getItem('token');
                const url2 = `${constList.BASE_URL}/api/coaches`;
                const response2 = await axios.get(url2, {
                    headers: {
                    Authorization: `Bearer ${token}` // Добавляем токен в заголовок Authorization
                    }
                });
                setCoaches(response2.data.content);
            }
            catch (error) {
                console.error('Ошибка при получении списка тренеров:', error);
            }
        };
    
        fetchPlaces();
    }, []);

    const handleChange = e => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSelectChange = (e) => {
        setSelectedPlaceId(e.target.value);
    };

    const handleSelectCoachChange = (e) => {
        setSelectedCoachId(e.target.value);
    };

    const handleSubmit = async e => {
        e.preventDefault();
        let new_startTime = new Date(formData.start_time);
        const event = {
            name: formData.name,
            start_time: new_startTime,
            taken_places: formData.taken_places,
            max_places: formData.max_places,
            duration_hours: formData.duration_hours,
            duration_minutes: formData.duration_minutes,
            price: formData.price,
            place: selectedPlaceId,
            coach: selectedCoachId
        }
        try {
            const token = localStorage.getItem('token');
            const url = `${constList.BASE_URL}/api/events`;
            await axios.post(url, event, {
                headers: {
                Authorization: `Bearer ${token}`
                }
            });

            onClose();
        } catch (error) {
            console.error('Ошибка при создании мероприятия:', error);
        }
    };

    const handleClickBack = () => {
        onClose();
    }

    return (
        <div>
            <Button onClick={handleClickBack}> <ArrowBackIcon className='my-button'/> </Button>
            <Container maxWidth="sm">
            <Typography variant="h4" gutterBottom>Создать мероприятие</Typography>
            <form onSubmit={handleSubmit}>
                <TextField
                    fullWidth
                    margin="normal"
                    label="Название"
                    name="name"
                    value={formData.name}
                    onChange={handleChange}
                />
                <InputLabel id="select-label">Место</InputLabel>
                <Select 
                    labelId="select-label"
                    label="Место"
                    fullWidth={true}
                    variant="outlined"
                    value={selectedPlaceId} onChange={handleSelectChange}>
                    {places.map(place => (
                        <MenuItem key={place.place_id} value={place.place_id}>{place.name}</MenuItem>
                    ))}
                </Select>
                <TextField
                    fullWidth
                    margin="normal"
                    label="Время начала"
                    name="start_time"
                    type="datetime-local"
                    value={formData.start_time.slice(0, 16)}
                    onChange={handleChange}
                />
                <TextField
                    fullWidth
                    margin="normal"
                    label="Длительность часы"
                    name="duration_hours"
                    type="number"
                    value={formData.duration_hours}
                    onChange={handleChange}
                />
                <TextField
                    fullWidth
                    margin="normal"
                    label="Длительность минуты"
                    name="duration_minutes"
                    type="number"
                    value={formData.duration_minutes}
                    onChange={handleChange}
                />
                <TextField
                    fullWidth
                    margin="normal"
                    label="Стоимость"
                    name="price"
                    type="number"
                    value={formData.price}
                    onChange={handleChange}
                />
                <InputLabel id="select-label2">Тренер</InputLabel>
                <Select 
                    labelId="select-label2"
                    label="Тренер"
                    fullWidth={true}
                    variant="outlined"
                    value={selectedCoachId} onChange={handleSelectCoachChange}>
                    {coaches.map(coach => (
                        <MenuItem key={coach.user_id} value={coach.user_id}>
                            {coach.user?.fio || 'Нет информации о тренере'}
                        </MenuItem>
                    ))}
                </Select>
                <TextField
                    fullWidth
                    margin="normal"
                    label="Занято мест"
                    name="taken_places"
                    type="number"
                    value={formData.taken_places}
                    onChange={handleChange}
                />
                <TextField
                    fullWidth
                    margin="normal"
                    label="Максимальное количество мест"
                    name="max_places"
                    type="number"
                    value={formData.max_places}
                    onChange={handleChange}
                />
                <Button type="submit" variant="contained" color="warning">Добавить</Button>
            </form>
            </Container>
        </div>
    );
};

export default CreateEvent;
