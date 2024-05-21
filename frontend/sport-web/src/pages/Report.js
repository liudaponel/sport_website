import React, { useState, useEffect } from 'react';
import axios from 'axios';
import TableContainer from '@mui/material/TableContainer';
import Table from '@mui/material/Table';
import TableHead from '@mui/material/TableHead';
import TableBody from '@mui/material/TableBody';
import TableRow from '@mui/material/TableRow';
import TableCell from '@mui/material/TableCell';
import { TextField, Button, MenuItem} from '@mui/material';
import * as constList from '../addition/Constants.js';


const Report = () => {
    const [places, setPlaces] = useState([]);
    const [selectedPlace, setSelectedPlace] = useState('');
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [reportData, setReportData] = useState([]);

    const loadPlaces = async () => {
        try {
            const token = localStorage.getItem('token');
            const url = `${constList.BASE_URL}/api/places`;
            const response = await axios.get(url, {
                headers: {
                Authorization: `Bearer ${token}` // Добавляем токен в заголовок Authorization
                }
            });
            setPlaces(response.data);
        } catch (error) {
            console.error('Ошибка при получении списка мест:', error);
        }
    };

    const fetchReport = async () => {
        try {
            const body = {
                place_id : selectedPlace,
                start_time : startDate,
                end_time : endDate
            }
            const token = localStorage.getItem('token');
            const url = `${constList.BASE_URL}/api/events/report`;
            const response = await axios.post(url, body, {
                headers: {
                    Authorization: `Bearer ${token}` // Добавляем токен в заголовок Authorization
                }
            });
            setReportData(response.data);
        } catch (error) {
            console.error('Ошибка получения отчета:', error);
        }
    };

    useEffect(() => {
        loadPlaces();
    }, []);

    return (
        <div>
            <h1>Отчёт</h1>
            <div>
                <TextField
                    select
                    label="Место"
                    value={selectedPlace}
                    onChange={(e) => setSelectedPlace(e.target.value)}
                    fullWidth
                    sx={{ mr: 2 }}
                >
                    {places.map((place) => (
                        <MenuItem key={place.place_id} value={place.place_id}>
                            {place.name}
                        </MenuItem>
                    ))}
                </TextField>
                <TextField
                    label="Начало"
                    type="datetime-local"
                    value={startDate}
                    onChange={(e) => setStartDate(e.target.value)}
                    fullWidth
                    sx={{ mr: 2 }}
                />
                <TextField
                    label="Конец"
                    type="datetime-local"
                    value={endDate}
                    onChange={(e) => setEndDate(e.target.value)}
                    fullWidth
                    sx={{ mr: 2 }}
                />
                <Button variant="contained" color="warning" onClick={fetchReport}>
                    ОК
                </Button>
            </div>
            <TableContainer>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Название мероприятия</TableCell>
                            <TableCell>Количество посетителей</TableCell>
                            <TableCell>Дата и время</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {
                        reportData.map((row) => (
                            <TableRow key={row.event_id}>
                                <TableCell>{row.event}</TableCell>
                                <TableCell>{row.count_people}</TableCell>
                                <TableCell>
                                    {(() => {
                                        const startTime = new Date(row.date);
                                        const startDate = startTime.toISOString().split('T')[0];
                                        const startTime_ = startTime.toTimeString().split(' ')[0];
                                        return `${startDate} ${startTime_}`;
                                    })()}
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </div>
    );
};

export default Report;
