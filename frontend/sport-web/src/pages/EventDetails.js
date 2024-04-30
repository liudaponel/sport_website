import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import axios from 'axios';
import { TextField, Button, Container, Typography, Select, InputLabel, MenuItem } from '@mui/material';

import * as constList from '../addition/Constants.js';

const EventDetails = () => {
    const [places, setPlaces] = useState([]);
    const [selectedPlaceId, setSelectedPlaceId] = useState('');
    const [coaches, setCoaches] = useState([]);
    const [selectedCoachId, setSelectedCoachId] = useState('');

    const [isAdmin, setIsAdmin] = useState(false);
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
        const userRole = localStorage.getItem('role');
        setIsAdmin(userRole === 'Администратор');

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
            setSelectedPlaceId(response.data.place.place_id);
            setSelectedCoachId(response.data.coach.user.user_id);
        } catch (error) {
            console.error('Ошибка при получении информации о мероприятии:', error);
        }
        };

        fetchEvent();
    }, [id]);

    useEffect(() => {
        const userRole = localStorage.getItem('role');
        setIsAdmin(userRole === 'Администратор');
        console.log(isAdmin);
        if (isAdmin) {
            const fetchAdminData = async () => {
                try {
                    const token = localStorage.getItem('token');
                    const placesResponse = await axios.get(`${constList.BASE_URL}/api/places`, {
                        headers: {
                            Authorization: `Bearer ${token}`
                        }
                    });
                    setPlaces(placesResponse.data);
    
                    const coachesResponse = await axios.get(`${constList.BASE_URL}/api/coaches`, {
                        headers: {
                            Authorization: `Bearer ${token}`
                        }
                    });
                    setCoaches(coachesResponse.data);
                } catch (error) {
                    console.error('Ошибка при получении данных для администратора:', error);
                }
            };
            fetchAdminData();
        }
    }, [isAdmin]);

    const handleChange = e => {
        const { name, value } = e.target;

        setFormData(prevFormData => ({
            ...prevFormData,
            [name]: value
        }));
    };

    const handleSelectChange = (e) => {
        setSelectedPlaceId(e.target.value);
    };

    const handleSelectCoachChange = (e) => {
        setSelectedCoachId(e.target.value);
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
            place: selectedPlaceId,
            coach: selectedCoachId
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
                InputProps={{
                    readOnly: !isAdmin,
                }}
            />
            {!isAdmin && (
                <TextField
                    fullWidth
                    margin="normal"
                    label="Место"
                    name="place"
                    value={`${formData.place.name}, ${formData.place.address}`}
                    readOnly
                />
            )}
            {isAdmin && (
                <div>
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
                </div>
            )}
            <TextField
                fullWidth
                margin="normal"
                label="Время начала"
                name="start_time"
                type="datetime-local"
                value={formData.start_time.slice(0, 16)}
                onChange={handleChange}
                InputProps={{
                    readOnly: !isAdmin,
                }}
            />
            <TextField
                fullWidth
                margin="normal"
                label="Длительность часы"
                name="duration_hours"
                type="number"
                value={formData.duration_hours}
                onChange={handleChange}
                InputProps={{
                    readOnly: !isAdmin,
                }}
            />
            <TextField
                fullWidth
                margin="normal"
                label="Длительность минуты"
                name="duration_minutes"
                type="number"
                value={formData.duration_minutes}
                onChange={handleChange}
                InputProps={{
                    readOnly: !isAdmin,
                }}
            />
            <TextField
                fullWidth
                margin="normal"
                label="Стоимость"
                name="price"
                type="number"
                value={formData.price}
                onChange={handleChange}
                InputProps={{
                    readOnly: !isAdmin,
                }}
            />
            {!isAdmin &&(
                <TextField
                    fullWidth
                    margin="normal"
                    label="Тренер"
                    name="coach"
                    value={formData.coach.user ? formData.coach.user.fio : ''}
                    onChange={handleChange}
                    readOnly
                />
            )}
            
            {isAdmin && (
                <div>
                <InputLabel id="select-label2">Тренер</InputLabel>
                <Select 
                    labelId="select-label2"
                    label="Тренер"
                    fullWidth={true}
                    variant="outlined"
                    value={selectedCoachId} 
                    onChange={handleSelectCoachChange}>
                    
                    {coaches.map(coach => (
                        <MenuItem key={coach.user_id} value={coach.user_id}>
                            {coach.user?.fio || ''}
                        </MenuItem>
                    ))}
                </Select>
                </div>
            )}

            <TextField
                fullWidth
                margin="normal"
                label="Занято мест"
                name="taken_places"
                type="number"
                value={formData.taken_places}
                onChange={handleChange}
                InputProps={{
                    readOnly: !isAdmin,
                }}
            />
            <TextField
                fullWidth
                margin="normal"
                label="Максимальное количество мест"
                name="max_places"
                type="number"
                value={formData.max_places}
                onChange={handleChange}
                InputProps={{
                    readOnly: !isAdmin,
                }}
            />
            {localStorage.getItem('role') === 'Администратор' && (
            <Button type="submit" variant="contained" color="primary">Принять изменения</Button>)}
        </form>
        </Container>
    );
};

export default EventDetails;
