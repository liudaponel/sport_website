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
import LoginDialog from '../components/LoginDialog';


// Главный компонент приложения
function Navbar() {
    const [loginOpen, setLoginOpen] = useState(false);
    const [isAdmin, setIsAdmin] = useState(false);
    return (
        <div className="navbar">
            <div className="header">
                <Link to="/events" style={{ textDecoration: 'none' }}>
                    <IconButton>Мероприятия <CalendarMonthIcon/></IconButton>
                </Link>
                {localStorage.getItem('role') == 'Администратор' && (<Link to="/users" style={{ textDecoration: 'none' }}>
                    <IconButton>Посетители  <GroupsIcon/> </IconButton>
                </Link>)}
                {isAdmin > 0 && (<Link to="/areas" style={{ textDecoration: 'none' }}>
                    <IconButton>Площадки <DomainIcon/> </IconButton>
                </Link>)}
                {isAdmin > 0 && (<Link to="/registrations" style={{ textDecoration: 'none' }}>
                    <IconButton>Подтвердить <AccessAlarmIcon/> </IconButton>
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
                    <Route path="/users" exact={true} element={<Events />} />
                    <Route path="/areas" exact={true} element={<Events />} />
                    <Route path="/registrations" exact={true} element={<Events />} />
                </Routes>
            </div>
        </div>
    );
}

export default Navbar;
