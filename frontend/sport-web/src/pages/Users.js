import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import AddIcon from '@mui/icons-material/Add';

import * as constList from '../addition/Constants.js';
import '../styles/Users.css'
import '../styles/button.css'
import { Button } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';

import CreateUser from '../components/CreateUser.js';

const Users = () => {
    const [users, setUsers] = useState([]);
    const [showCreateUser, setShowCreateUser] = useState(false);

    useEffect(() => {
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
                console.log(response.data);
            } catch (error) {
                console.error('Ошибка при получении мероприятий:', error);
            }
        };
        fetchUsers();
    }, []);

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
                    </h1>
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
                </div>
            )}
        </div>
    );
};

export default Users;
