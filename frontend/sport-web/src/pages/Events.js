import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import AddIcon from '@mui/icons-material/Add';

import * as constList from '../addition/Constants.js';
import '../styles/Events.css'
import '../styles/button.css'
import { Button } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';

import CreateEvent from '../components/CreateEvent.js';

const Events = () => {
    const [events, setEvents] = useState([]);
    const [showCreateEvent, setShowCreateEvent] = useState(false);

    useEffect(() => {
        const fetchEvents = async () => {
            try {
                const token = localStorage.getItem('token');
                const url = `${constList.BASE_URL}/api/events`;
                const response = await axios.get(url, {
                headers: {
                    Authorization: `Bearer ${token}` // Добавляем токен в заголовок Authorization
                }
                });
                setEvents(response.data);
                console.log(response.data);
            } catch (error) {
                console.error('Ошибка при получении мероприятий:', error);
            }
        };
        fetchEvents();
    }, []);

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
