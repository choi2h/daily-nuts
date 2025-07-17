import axios from "axios"

const API_BASE_URL = import.meta.env.VITE_API_SERVER_ADDRESS;
const LOGIN_URL = `${API_BASE_URL}/member/login`;

const login = async (loginInfo) => {
    return axios.post(LOGIN_URL, loginInfo, {
        withCredentials: true       // ← 이걸 설정해야 쿠키가 자동으로 저장됨
        })
        .then((res) => {
            console.log(`res: 로그인 성공! ${res}`);
            return res;
        })
        .catch((err) => {
            console.log(`error: 로그인 실패 ${err}`);
            return err.response;
        });
}

export {login};