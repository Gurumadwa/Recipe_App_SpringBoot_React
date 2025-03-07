import React, { useState } from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';


export const UserContext = React.createContext({ isAuthorized: false});

const AppWrapper = () => {

  const [user, setUser] = useState({});
  const [isAuthorized, setIsAuthorized] = useState(false);

  return(
    <UserContext.Provider value={{user, setUser, isAuthorized, setIsAuthorized}}>
      <App />
    </UserContext.Provider>
  )

}

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <AppWrapper />
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
