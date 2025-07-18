import axios from 'axios';

axios.defaults.baseURL = import.meta.env.VITE_API_SERVER_ADDRESS;

// 리프레시 전용 인스턴스
const refreshApi = axios.create({
  baseURL: import.meta.env.VITE_API_SERVER_ADDRESS,
  headers: { 'Content-Type': 'application/json' }
});

axios.interceptors.request.use(async config => {
  let accessToken  = localStorage.getItem('accessToken');
  let refreshToken = localStorage.getItem('refreshToken');

  // 액세스 토큰이 없거나 만료됐다면
  if (!accessToken && refreshToken) {
    try {
      const { data } = await refreshApi.post('/member/refresh', { refreshToken });
      accessToken  = data.accessToken;
      refreshToken = data.refreshToken;
      localStorage.setItem('accessToken', accessToken);
      localStorage.setItem('refreshToken', refreshToken);
    } catch (err) {
      // 리프레시 실패 시 로그인 페이지 이동
      window.location.href = '/login';
      return Promise.reject(err);
    }
  }

  if (accessToken) {
    config.headers.Authorization = `Bearer ${accessToken}`;
  }
  return config;
}, error => Promise.reject(error));