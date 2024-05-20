import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import AddIcon from '@mui/icons-material/Add';

import * as constList from '../addition/Constants.js';
import '../styles/Events.css'
import '../styles/button.css'
import { TextField, Button, Select, InputLabel, MenuItem, emphasize } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import FilterListIcon from '@mui/icons-material/FilterList';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import ArrowForwardIcon from '@mui/icons-material/ArrowForward';

import CreateEvent from '../components/CreateEvent.js';

const Events = () => {
    const [events, setEvents] = useState([]);
    const [showCreateEvent, setShowCreateEvent] = useState(false);

    const [places, setPlaces] = useState([]);
    const [selectedPlaceId, setSelectedPlaceId] = useState('');
    const [coaches, setCoaches] = useState([]);
    const [selectedCoachId, setSelectedCoachId] = useState('');
    const [filters, setFilters] = useState({
        name: '',
        place: '',
        start_time: '',
        duration_hours : '',
        duration_minutes: '',
        price: '',
        coach: '',
        isSorted: false
    });

    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    useEffect(() => {
        const fetchEvents = async () => {
            try {
                const token = localStorage.getItem('token');
                const url = `${constList.BASE_URL}/api/events?page=${currentPage}`;
                const response = await axios.get(url, {
                headers: {
                    Authorization: `Bearer ${token}` // Добавляем токен в заголовок Authorization
                }
                });
                setEvents(response.data.content);
                setTotalPages(response.data.totalPages);
                console.log(response.data);
            } catch (error) {
                console.error('Ошибка при получении мероприятий:', error);
            }
        };
        fetchEvents();

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
                setCoaches(coachesResponse.data.content);
            } catch (error) {
                console.error('Ошибка при получении данных для администратора:', error);
            }
        };
        fetchAdminData();
    }, [currentPage]);

    const handleCreateEventClick = () => {
        setShowCreateEvent(true);
    };

    const handleClickDelete = async (eventId) => {
        try {
            console.log(`deleted event with id: ${eventId}`);

            const token = localStorage.getItem('token');
            const url = `${constList.BASE_URL}/api/events/${eventId}`;
            await axios.delete(url, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            // Обновление списка мероприятий после удаления
            const updatedEvents = events.filter(event => event.event_id !== eventId);
            setEvents(updatedEvents);
        } catch (error) {
            console.error('Ошибка при удалении мероприятия:', error);
        }
    };
    
    const handleChange = e => {
        const { name, value } = e.target;

        setFilters(prevFilters => ({
            ...prevFilters,
            [name]: value
        }));
    };

    const handleSelectChange = (e) => {
        setSelectedPlaceId(e.target.value);
    };

    const handleSelectCoachChange = (e) => {
        setSelectedCoachId(e.target.value);
    };    

    const handleClickOKFilters = async () => {
        const event = {
            name: filters.name,
            start_time: filters.start_time,
            duration_hours: filters.duration_hours,
            duration_minutes: filters.duration_minutes,
            price: filters.price,
            place: selectedPlaceId,
            coach: selectedCoachId,
            isSorted: filters.isSorted ? "true" : "false"
        }
        const token = localStorage.getItem('token');
        const url = `${constList.BASE_URL}/api/events/filters?page=${currentPage}`;
        try {
        const response = await axios.post(url, event, {
            headers: {
            Authorization: `Bearer ${token}`
            }
        });
        setEvents(response.data.content);
        setTotalPages(response.data.totalPages);

        } catch (error) {
        }
    }

    const clickBackPage = async () => {
        if(currentPage > 0){
            setCurrentPage(currentPage - 1);
        }
    }

    const clickForwardPage = async () => {
        if(currentPage < totalPages - 1){
            setCurrentPage(currentPage + 1);
        }
    }

    return (
        <div>
            {/* Отображаем компонент CreateEvent только если showCreateEvent равно true */}
            {showCreateEvent ? (
                <CreateEvent onClose={() => setShowCreateEvent(false)}/>
            ) : (
                <div>
                    <h1 className='header'>
                        Мероприятия 
                        {localStorage.getItem('role') === 'Администратор' && 
                            (<Button onClick={handleCreateEventClick}> <AddIcon className="my-button"/> </Button>)}
                    </h1>
                    <div>
                        Страница: {currentPage + 1} / {totalPages}
                        <Button onClick={clickBackPage}> <ArrowBackIcon/> </Button>
                        <Button onClick={clickForwardPage}> <ArrowForwardIcon/> </Button>
                    </div>
                    <div style={{ display: 'flex', alignItems: 'center' }}>
                        <TextField
                            margin="normal"
                            label="Название"
                            name="name"
                            value={filters.name}
                            onChange={handleChange}
                        />
                        <TextField
                            margin="normal"
                            label="Время начала"
                            name="start_time"
                            type="datetime-local"
                            value={filters.start_time}
                            onChange={handleChange}
                        />
                        <TextField
                            margin="normal"
                            label="Длительность часы"
                            name="duration_hours"
                            type="number"
                            value={filters.duration_hours}
                            onChange={handleChange}
                        />
                        <TextField
                            margin="normal"
                            label="Длительность минуты"
                            name="duration_minutes"
                            type="number"
                            value={filters.duration_minutes}
                            onChange={handleChange}
                        />
                        <TextField
                            margin="normal"
                            label="Стоимость"
                            name="price"
                            type="number"
                            value={filters.price}
                            onChange={handleChange}
                        />
                        <div>
                            <InputLabel id="select-label">Место</InputLabel>
                            <Select 
                                labelId="select-label"
                                label="Место"
                                variant="outlined"
                                value={selectedPlaceId} onChange={handleSelectChange}>
                                {places.map(place => (
                                    <MenuItem key={place.place_id} value={place.place_id}>{place.name}</MenuItem>
                                ))}
                            </Select>
                        </div>
                        <div>
                            <InputLabel id="select-label2">Тренер</InputLabel>
                            <Select 
                                labelId="select-label2"
                                label="Тренер"
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
                        {/* <Button onClick={handleClickSort}> <FilterListIcon className='my-button'/> </Button> */}
                        <Button variant="contained" color='warning' onClick={handleClickOKFilters}>OK</Button>
                    </div>
                    <div className="event-container">
                        {events.map(event => {
                            const startTime = new Date(event.start_time);
                            const startDate = startTime.toISOString().split('T')[0];
                            const startTime_ = startTime.toTimeString().split(' ')[0];
                            return(
                                <div key={event.event_id} className="event-card">
                                    <h2 className='delete_button'>
                                        <div className='event-name'> {event.name} </div>
                                        {localStorage.getItem('role') === 'Администратор' && 
                                            (<Button onClick={() => handleClickDelete(event.event_id)}> <DeleteIcon className='my-button'/> </Button>)}
                                    </h2>
                                    <p>Место: {event.place.address}</p>
                                    <p>Время начала: {startDate} {startTime_}</p>
                                    <p>Занято мест: {event.taken_places} / {event.max_places}</p>
                                    <p>Стоимость: {event.price}</p>
                                    <Link to={`/events/${event.event_id}`}>Подробнее</Link>
                                </div>
                            );
                        })}
                    </div>
                </div>
            )}
        </div>
    );
};

export default Events;
