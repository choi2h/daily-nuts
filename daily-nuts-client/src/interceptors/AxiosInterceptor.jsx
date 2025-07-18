import { useEffect } from 'react';
import apiClient from './apiClient';

/**
 * AxiosInterceptor
 * - 서버로부터 받은 토큰을 저장하고,
 * - 모든 요청에 저장된 토큰을 자동으로 헤더에 추가합니다.
 */
const AxiosInterceptor = ({ children }) => {
  useEffect(() => {
    // 1) Request 인터셉터 등록
    const reqId = apiClient.interceptors.request.use(
      config => {
        // 저장된 토큰을 읽어와 Authorization 헤더에 추가
        const token = localStorage.getItem('accessToken');
        if (token) {
          config.headers.Authorization = `Bearer ${token}`;
        }
        const refreshToken = localStorage.getItem('refreshToken');
        if (refreshToken) {
          config.headers['Refresh-Token'] = refreshToken;
        }
        return config;
      },
      error => Promise.reject(error)
    );

    // 2) Response 인터셉터 등록
    const resId = apiClient.interceptors.response.use(
      response => {
        // 서버가 보낸 토큰(header) 저장
        const authHeader = response.headers['authorization'];
        if (authHeader) {
          // "Bearer <token>" 형태에서 토큰만 저장
          const token = authHeader.split(' ')[1];
          localStorage.setItem('accessToken', token);
        }
        const refreshHeader = response.headers['Refresh-Token'];
        if (refreshHeader) {
          localStorage.setItem('refreshToken', refreshHeader);
        }
        return response;
      },
      error => {
        const status = error.response?.status;
        if (status === 401) {
          // 인증 실패 시 로그인 페이지로
          window.location.href = '/login';
        } else if (status === 403) {
          alert('권한이 없습니다.');
        }
        return Promise.reject(error);
      }
    );

    // 3) 언마운트 시 인터셉터 해제
    return () => {
      apiClient.interceptors.request.eject(reqId);
      apiClient.interceptors.response.eject(resId);
    };
  }, []);

  return children;
};

export default AxiosInterceptor;
