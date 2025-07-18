import axios from 'axios';

/**
 * Axios 인스턴스 설정 모듈
 * - baseURL: 모든 요청의 기본 URL
 * - withCredentials: 쿠키 전송 여부
 * - timeout: 요청 제한 시간 (ms)
 */
const apiClient = axios.create({
  baseURL: '/api',
  withCredentials: true,
  timeout: 10000,
});

export default apiClient;