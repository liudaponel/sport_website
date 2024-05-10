import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import AddIcon from '@mui/icons-material/Add';

import * as constList from '../addition/Constants.js';
import '../styles/Users.css'
import '../styles/button.css'
import { TextField, Button, Select, InputLabel, MenuItem} from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import Switch from '@mui/material/Switch';
import FormControlLabel from '@mui/material/FormControlLabel';

import CreateUser from '../components/CreateUser.js';

const Users = () => {
    const [users, setUsers] = useState([]);
    const [coaches, setCoaches] = useState([]);
    const [showCreateUser, setShowCreateUser] = useState(false);
    const [onlyCoaches, setOnlyCoaches] = useState(false);

    const [filters, setFilters] = useState({
        fio: '',
        email: '',
        phone_number: '',
        role: ''
    });
    const [roles, setRoles] = useState([]);
    const [selectedRoleId, setSelectedRoleId] = useState('');

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

    useEffect(() => {
        const fetchRoles = async () => {
            try {
                const token = localStorage.getItem('token');
                const response = await axios.get(`${constList.BASE_URL}/api/roles`, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                setRoles(response.data);
            } catch (error) {
                console.error('Ошибка при получении списка ролей:', error);
            }
        };
        fetchRoles();
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

    const handleSwitchCoaches = (event) => {
        setOnlyCoaches(event.target.checked);
    }

    const handleSelectChange = (e) => {
        setSelectedRoleId(e.target.value);
    };

    const handleChange = e => {
        const { name, value } = e.target;
        setFilters(prevFilters => ({
            ...prevFilters,
            [name]: value
        }));
    };

    const handleClickOKFilters = async () => {
        const user = {
            fio: filters.fio,
            email: filters.email,
            phone_number: filters.phone_number,
            role: selectedRoleId
        }
        const token = localStorage.getItem('token');
        const url = `${constList.BASE_URL}/api/users/filters`;
        try {
        const response = await axios.post(url, user, {
            headers: {
            Authorization: `Bearer ${token}`
            }
        });
        setUsers(response.data);

        } catch (error) {
        }
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
                    <div style={{ display: 'flex', alignItems: 'center' }}>
                        <TextField
                            fullWidth
                            margin="normal"
                            label="ФИО"
                            name="fio"
                            value={filters.fio}
                            onChange={handleChange}
                        />
                        <TextField
                            fullWidth
                            margin="normal"
                            label="Email"
                            name="email"
                            value={filters.email}
                            onChange={handleChange}
                        />
                        <TextField
                            fullWidth
                            margin="normal"
                            label="Номер телефона"
                            name="phone_number"
                            value={filters.phone_number}
                            onChange={handleChange}
                        />
                        <div>
                        <InputLabel id="select-label">Роль</InputLabel>
                            <Select 
                                labelId="select-label"
                                label="Роль"
                                fullWidth={true}
                                variant="outlined"
                                value={selectedRoleId} 
                                onChange={handleSelectChange}>
                                
                                {roles.map(role => (
                                    <MenuItem key={role.role_id} value={role.role_id}>
                                        {role.name}
                                    </MenuItem>
                                ))}
                            </Select>
                        </div>
                        <Button variant="contained" color='warning' onClick={handleClickOKFilters}>OK</Button>
                    </div>

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
