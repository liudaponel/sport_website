import React, { useState } from 'react';
import axios from 'axios';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';

import * as constList from '../addition/Constants.js';
import '../styles/QueryPage.css'


const QueryPage = () => {
    const [query, setQuery] = useState(null);
    const [results, setResults] = useState([]);

    const handleQueryChange = (event) => {
        setQuery(event.target.value);
    };

    const handleSubmit = async () => {
        const queryRequest = {
            query: query
        }
        try {
            const token = localStorage.getItem('token');
            const url = `${constList.BASE_URL}/api/query`;
            const response = await axios.post(url, queryRequest, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            if(response.data != null){
                setResults(response.data);
            }
            else{
                setQuery(null);
                alert("Запрос выполнен успешно");
            }
        } catch (error) {
            alert("Ошибка при выполнении запрооса");
            console.error('Ошибка в запросе:', error);
        }
    };

    return (
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
            <div style={{ display: 'flex', alignItems: 'center' }}>
                <TextField
                    label="Введите запрос"
                    variant="outlined"
                    fullWidth
                    value={query}
                    onChange={handleQueryChange}
                />
                <Button
                    variant="contained"
                    color='warning'
                    onClick={handleSubmit}
                >
                    OK
                </Button>
            </div>

            {query != null && (
            <div className='container'>
                {results.map((result, index) => (
                    <div key={index} className="card">
                        {Object.keys(result).map((key, i) => (
                            <p>{result[key]}</p>
                        ))}
                    </div>
                ))}
            </div>)}
        </div>
    );
};

export default QueryPage;
