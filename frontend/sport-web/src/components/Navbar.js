import { Route, Routes} from 'react-router-dom';
import React, { useState } from 'react';

import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import IconButton from '@mui/material/IconButton';


import Events from '../pages/Events';
import LoginDialog from '../components/LoginDialog';

// Страницы
const Home = () => <h2>Домашняя страница</h2>;

// Главный компонент приложения
function Navbar() {
    const [loginOpen, setLoginOpen] = useState(false);
    return (
        <div>
            <div className="navbar">
                <div className="header">
                    <IconButton className='header-avatar' onClick={() => setLoginOpen(true)}>
                        <AccountCircleIcon style={{ color: 'red' }} />
                    </IconButton>
                </div>
                <LoginDialog open={loginOpen} onClose={() => setLoginOpen(false)} />
            </div>
            <div className='main-content'>
                <Routes>
                    <Route path="/" exact={true} element={<Home/>} />
                    <Route path="/events" exact={true} element={<Events/>} />
                </Routes>
            </div>
        </div>
    );
}

export default Navbar;