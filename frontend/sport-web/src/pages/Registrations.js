import React, { useState, useEffect } from 'react';
import axios from 'axios';

import * as constList from '../addition/Constants.js';
import '../styles/Registrations.css'
import '../styles/button.css'
import { Button } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import DoneIcon from '@mui/icons-material/Done';

const Registrations = () => {
    const [registrations, setRegistrations] = useState([]);

    useEffect(() => {
        const fetchRegistrations = async () => {
            try {
                const token = localStorage.getItem('token');
                const url = `${constList.BASE_URL}/api/registrations/not_checked`;
                const response = await axios.get(url, {
                headers: {
                    Authorization: `Bearer ${token}` // Добавляем токен в заголовок Authorization
                }
                });
                setRegistrations(response.data);
            } catch (error) {
                console.error('Ошибка при получении списка мест:', error);
            }
        };
        fetchRegistrations();
    }, []);

    const handleClickDelete = async (registrationId) => {
        try {
            const token = localStorage.getItem('token');
            const url = `${constList.BASE_URL}/api/registrations/${registrationId}`;
            await axios.delete(url, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            // Обновление списка после удаления
            const updatedRegistrations = registrations.filter(registration => registration.users_events_id !== registrationId);
            setRegistrations(updatedRegistrations);
        } catch (error) {
            console.error('Ошибка при удалении регистрации:', error);
        }
    };

    const handleClickCheck = async (registrationId) => {
        try {
            const token = localStorage.getItem('token');
            const url = `${constList.BASE_URL}/api/registrations/check/${registrationId}`;
            await axios.put(url, null, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            // Обновление списка после принятия
            const updatedRegistrations = registrations.filter(registration => registration.users_events_id !== registrationId);
            setRegistrations(updatedRegistrations);
        } catch (error) {
            console.error('Ошибка при подтверждении регистрации:', error);
        }
    };
    

    return (
        <div>
            <h1 className='header'>
                Нужно подтвердить
            </h1>
            <div className="registration-container">
                {registrations.map(registration => {
                    return(
                        <div key={registration.users_events_id} className="registration-card">
                            <h2 className='delete_button'>
                                <Button onClick={() => handleClickCheck(registration.users_events_id)}> <DoneIcon className='my-button'/> </Button>
                                <Button onClick={() => handleClickDelete(registration.users_events_id)}> <DeleteIcon className='my-button'/> </Button>
                            </h2>
                            <p>Пользователь: {registration.user_id.fio}</p>
                            <p>email: {registration.user_id.email}</p>
                            <p>Мероприятие: {registration.event_id.name}</p>
                        </div>
                    );
                })}
            </div>
        </div>
    );
};

export default Registrations;
