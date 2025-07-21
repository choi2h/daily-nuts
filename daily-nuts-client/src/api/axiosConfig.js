import axios from "axios";

axios.defaults.baseURL = import.meta.env.VITE_API_SERVER_ADDRESS;

const PUBLIC_PATHS = ["/member/login", "/member/signup", "/member/exist"];

// 리프레시 전용 인스턴스
const refreshApi = axios.create({
  baseURL: import.meta.env.VITE_API_SERVER_ADDRESS,
  headers: { "Content-Type": "application/json" },
});

axios.interceptors.request.use(
  async (config) => {
    if (PUBLIC_PATHS.some((path) => config.url.includes(path))) {
      return config;
    }

    const accessToken = localStorage.getItem("accessToken");
    const refreshToken = localStorage.getItem("refreshToken");

    // 액세스 토큰이 없거나 만료됐다면
    if (!accessToken && refreshToken) {
      try {
        const res = await refreshApi.get("/member/refresh", {
          headers: { "Refresh-Token": refreshToken },
        });
        console.log("Refresh token!!!!" + res.headers);
        const newAccessToken = res.headers.authorization;
        const newRefreshToken = res.headers["refresh-token"];

        console.log("new Access!!!!", newAccessToken);
        console.log("new Refresh!!!!", newRefreshToken);

        if (newAccessToken) localStorage.setItem("accessToken", newAccessToken);
        if (newRefreshToken)
          localStorage.setItem("refreshToken", newRefreshToken);
      } catch (err) {
        console.log(err);

        // 리프레시 실패 시 로그인 페이지 이동
        localStorage.clear();
        delete axios.defaults.headers.common["Authorization"];
        delete axios.defaults.headers.common["Refresh-Token"];
        window.location.href = "/login";
        return Promise.reject(err);
      }
    }

    if (!refreshToken) {
      localStorage.clear();
      delete axios.defaults.headers.common["Authorization"];
      delete axios.defaults.headers.common["Refresh-Token"];
      window.location.href = "/login";
      alert("토큰이 만료되었습니다");
      console.log("Refresh token이 없어서 튕김!!!!");
      return
    }

    if (accessToken) {
      console.log("accessToken:", accessToken);
      config.headers.Authorization = `${accessToken}`;
      console.log("Authorization header set to:", config.headers.Authorization);
    }
    return config;
  },
  (error) => Promise.reject(error)
);
export default axios;
