import React from 'react';
import {BrowserRouter, useLocation} from 'react-router-dom'
import './styles/App.css';
import Header from './components/Header/Header';
import AppRouter from './routes/AppRouter';
import Footer from './components/Footer/Footer';
import {DateProvider} from './components/Context/DateContext';
import { UserProvider } from './components/Context/UserContext';
import TimeProvider from './components/Context/TimeContext';
import DataProductProvider from './components/Context/DataProductContext';

function App() {
  return (
    <BrowserRouter>
      <UserProvider>
        <DataProductProvider>
          <Header />
            <TimeProvider>
            <DateProvider>
              <AppRouter/>
            </DateProvider>
            </TimeProvider>
          <Footer/>
        </DataProductProvider>
      </UserProvider>
    </BrowserRouter>
  );
}

export default App;
