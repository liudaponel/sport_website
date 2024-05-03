import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import AddIcon from '@mui/icons-material/Add';

import * as constList from '../addition/Constants.js';
import '../styles/Places.css'
import '../styles/button.css'
import { Button } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';

import CreatePlace from '../components/CreatePlace.js';

const Places = () => {
    const [places, setPlaces] = useState([]);
    const [showCreatePlace, setShowCreatePlace] = useState(false);

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
                console.log(response.data);
            } catch (error) {
                console.error('Ошибка при получении списка мест:', error);
            }
        };
        fetchPlaces();
    }, []);

    const handleCreatePlaceClick = () => {
        setShowCreatePlace(true);
    };

    const handleClickDelete = async (placeId) => {
        try {
            const token = localStorage.getItem('token');
            const url = `${constList.BASE_URL}/api/places/${placeId}`;
            await axios.delete(url, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            // Обновление списка после удаления
            const updatedPlaces = places.filter(place => place.place_id !== placeId);
            setPlaces(updatedPlaces);
        } catch (error) {
            console.error('Ошибка при удалении места:', error);
        }
    };
    

    return (
        <div>
            {/* Отображаем компонент CreatePlace только если showCreatePlace равно true */}
            {showCreatePlace ? (
                <CreatePlace onClose={() => setShowCreatePlace(false)}/>
            ) : (
                <div>
                    <h1 className='header'>
                        Спортивные Площадки
                        <Button onClick={handleCreatePlaceClick}> <AddIcon className="my-button"/> </Button>
                    </h1>
                    <div className="place-container">
                        {places.map(place => {
                            return(
                                <div key={place.place_id} className="place-card">
                                    <h2 className='delete_button'>
                                        <div className='place-name'> {place.name} </div>
                                        <Button onClick={() => handleClickDelete(place.place_id)}> <DeleteIcon className='my-button'/> </Button>
                                    </h2>
                                    <p>Адрес: {place.address}</p>
                                    <p>Максимально мест: {place.max_places}</p>
                                    <Link to={`/places/${place.place_id}`}>Подробнее</Link>
                                </div>
                            );
                        })}
                    </div>
                </div>
            )}
        </div>
    );
};

export default Places;
