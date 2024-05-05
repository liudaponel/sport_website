import React, { useState, useEffect } from 'react';
import { Button } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import axios from 'axios';

import * as constList from '../addition/Constants.js';
import '../styles/button.css'


const UserEventsList = ({ events: initialEvents}) => {
    const [events, setEvents] = useState(initialEvents);

    const handleClickDelete = async (registrId) => {
        try {
            const token = localStorage.getItem('token');
            const url = `${constList.BASE_URL}/api/registrations/${registrId}`;
            await axios.delete(url, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            // Обновление списка после удаления
            const updatedEvents = events.filter(event => event.users_events_id != registrId);
            setEvents(updatedEvents);
        } catch (error) {
            console.error('Ошибка при удалении мероприятия:', error);
        }
    };

    useEffect(() => {
        setEvents(initialEvents);
    }, [initialEvents]);

    return (
        <div>
            <h2>Мероприятия, на которые записан:</h2>
            <div style={{ overflowX: 'auto', whiteSpace: 'nowrap' }}>
                {events.map(event => (
                        <div key={event.event_id.event_id} style={{ display: 'inline-block', margin: '0 10px' }}>
                            <div>
                                {event.event_id.name}
                                <Button onClick={() => handleClickDelete(event.users_events_id)}> <DeleteIcon className='my-button'/> </Button>
                            </div>
                        </div>
                ))}
            </div>
        </div>
    );
}

export default UserEventsList;
