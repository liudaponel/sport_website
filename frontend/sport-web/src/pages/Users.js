import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import AddIcon from '@mui/icons-material/Add';

import * as constList from '../addition/Constants.js';
import '../styles/Users.css'
import '../styles/button.css'
import { Button } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import Switch from '@mui/material/Switch';
import FormControlLabel from '@mui/material/FormControlLabel';

import CreateUser from '../components/CreateUser.js';

const Users = () => {
    const [users, setUsers] = useState([]);
    const [coaches, setCoaches] = useState([]);
    const [showCreateUser, setShowCreateUser] = useState(false);
    const [onlyCoaches, setOnlyCoaches] = useState(false);

    // useEffect(() => {
    //     const fetchUsers = async () => {
    //         try {
    //             const token = localStorage.getItem('token');
    //             const url = `${constList.BASE_URL}/api/users`;
    //             const response = await axios.get(url, {
    //             headers: {
    //                 Authorization: `Bearer ${token}` // Добавляем токен в заголовок Authorization
    //             }
    //             });
    //             setUsers(response.data);
    //             console.log(response.data);
    //         } catch (error) {
    //             console.error('Ошибка при получении мероприятий:', error);
    //         }
    //     };
    //     fetchUsers();
    // }, []);

    useEffect(() => {
        if(onlyCoaches === false){
            const fetchUsers = async () => {
                try {
                    const token = localStorage.getItem('token');
                    const url = `${constList.BASE_URL}/api/users`;
                    const response = await axios.get(url, {
                    headers: {
                        Authorization: `Bearer ${token}` // Добавляем токен в заголовок Authorization
                    }
                    });
                    setUsers(response.data);
                } catch (error) {
                    console.error('Ошибка при получении мероприятий:', error);
                }
            };
            fetchUsers();
        }
        else{
            const fetchCoaches = async () => {
                try {
                    const token = localStorage.getItem('token');
                    const url = `${constList.BASE_URL}/api/coaches`;
                    const response = await axios.get(url, {
                    headers: {
                        Authorization: `Bearer ${token}` // Добавляем токен в заголовок Authorization
                    }
                    });
                    setCoaches(response.data);
                } catch (error) {
                    console.error('Ошибка при получении мероприятий:', error);
                }
            };
            fetchCoaches();
        }
    }, [onlyCoaches]);

    const handleCreateUserClick = () => {
        setShowCreateUser(true);
    };

    const handleClickDelete = async (userId) => {
        try {
            const token = localStorage.getItem('token');
            const url = `${constList.BASE_URL}/api/users/${userId}`;
            await axios.delete(url, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            // Обновление списка после удаления
            const updatedUsers = users.filter(user => user.user_id !== userId);
            setUsers(updatedUsers);
        } catch (error) {
            console.error('Ошибка при удалении мероприятия:', error);
        }
    };

    const handleSwitchCoaches = (event) => {
        setOnlyCoaches(event.target.checked);
    }
    

    return (
        <div>
            {/* Отображаем компонент CreateUser только если showCreateUser равно true */}
            {showCreateUser ? (
                <CreateUser onClose={() => setShowCreateUser(false)}/>
            ) : (
                <div>
                    <h1 className='header'>
                        Пользователи
                        <Button onClick={handleCreateUserClick}> <AddIcon className="my-button"/> </Button>
                        <FormControlLabel
                            control={
                                <Switch checked={onlyCoaches} onChange={handleSwitchCoaches} name="switch_coaches" color="warning" />
                            }
                            label="Только тренеры"
                            />
                    </h1>
                    {onlyCoaches ? (
                        <div className="user-container">
                            {coaches.map(coach => {
                                return(
                                    <div key={coach.user_id} className="user-card">
                                        <h2 className='delete_button'>
                                            <div className='user-name'> {coach.user.fio} </div>
                                            <Button onClick={() => handleClickDelete(coach.user_id)}> <DeleteIcon className='my-button'/> </Button>
                                        </h2>
                                        <p>email: {coach.user.email}</p>
                                        <p>роль: {coach.user.role?.name || ''}</p>
                                        <Link to={`/users/${coach.user_id}`}>Подробнее</Link>
                                    </div>
                                );
                            })}
                        </div>
                    ) : (
                        <div className="user-container">
                            {users.map(user => {
                                return(
                                    <div key={user.user_id} className="user-card">
                                        <h2 className='delete_button'>
                                            <div className='user-name'> {user.fio} </div>
                                            <Button onClick={() => handleClickDelete(user.user_id)}> <DeleteIcon className='my-button'/> </Button>
                                        </h2>
                                        <p>email: {user.email}</p>
                                        <p>роль: {user.role?.name || ''}</p>
                                        <Link to={`/users/${user.user_id}`}>Подробнее</Link>
                                    </div>
                                );
                            })}
                        </div>
                    )}
                </div>
            )}
        </div>
    );
};

export default Users;
