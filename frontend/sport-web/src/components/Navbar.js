import { Route, Routes, Link } from 'react-router-dom';
import React, { useState } from 'react';
import '../styles/Navbar.css';

import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import IconButton from '@mui/material/IconButton';
import CalendarMonthIcon from '@mui/icons-material/CalendarMonth';
import GroupsIcon from '@mui/icons-material/Groups';
import DomainIcon from '@mui/icons-material/Domain';
import AccessAlarmIcon from '@mui/icons-material/AccessAlarm';

import Events from '../pages/Events';
import Places from '../pages/Places';
import Users from '../pages/Users';
import Registrations from '../pages/Registrations';
import EventDetails from '../pages/EventDetails';
import LoginDialog from '../components/LoginDialog';
import PlaceDetails from '../pages/PlaceDetails';
import UserDetails from '../pages/UserDetails';
import QueryPage from '../pages/QueryPage';


// Главный компонент приложения
function Navbar() {
    const [loginOpen, setLoginOpen] = useState(false);
    return (
        <div className="navbar">
            <div className="header">
                <Link to="/events" style={{ textDecoration: 'none' }}>
                    <IconButton>Мероприятия <CalendarMonthIcon className='my-button'/></IconButton>
                </Link>
                {localStorage.getItem('role') === 'Администратор' && (<Link to="/users" style={{ textDecoration: 'none' }}>
                    <IconButton>Пользователи <GroupsIcon className='my-button'/> </IconButton>
                </Link>)}
                {localStorage.getItem('role') === 'Администратор' && (<Link to="/places" style={{ textDecoration: 'none' }}>
                    <IconButton>Площадки <DomainIcon className='my-button'/> </IconButton>
                </Link>)}
                {localStorage.getItem('role') === 'Администратор' && (<Link to="/registrations" style={{ textDecoration: 'none' }}>
                    <IconButton>Подтвердить <AccessAlarmIcon className='my-button'/> </IconButton>
                </Link>)}
                {localStorage.getItem('role') === 'Администратор' && (<Link to="/query" style={{ textDecoration: 'none' }}>
                    <IconButton>Query </IconButton>
                </Link>)}
                <IconButton onClick={() => setLoginOpen(true)}>
                    <AccountCircleIcon className="header-icon" />
                </IconButton>
                <LoginDialog open={loginOpen} onClose={() => setLoginOpen(false)} />
            </div>
            <div className="main-content">
                <Routes>
                    <Route path="/" exact={true} element={<Events />} />
                    <Route path="/events" exact={true} element={<Events />} />
                    <Route path="/events/:id" element={<EventDetails />} />
                    <Route path="/users" exact={true} element={<Users />} />
                    <Route path="/users/:id" element={<UserDetails />} />
                    <Route path="/places" exact={true} element={<Places />} />
                    <Route path="/places/:id" element={<PlaceDetails />} />
                    <Route path="/registrations" exact={true} element={<Registrations />} />
                    <Route path="/query" element={<QueryPage />} />
                </Routes>
            </div>
        </div>
    );
}

export default Navbar;
