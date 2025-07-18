import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import AxiosInterceptor from './interceptors/AxiosInterceptor';

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <AxiosInterceptor>
      <App />
    </AxiosInterceptor>
  </StrictMode>,
)
