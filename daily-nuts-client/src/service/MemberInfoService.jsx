import axios, { HttpStatusCode } from "axios"

const login = async (loginInfo) => {
    return axios.post('/member/login', loginInfo, {
       // ← 이걸 설정해야 쿠키가 자동으로 저장됨
        })
        .then((res) => {
            console.log(res);
            if(res.status === HttpStatusCode.Ok) {
                      const accessToken  = res.headers.authorization;      
                      const refreshToken = res.headers['refresh-token'];
            
                      if (accessToken)  localStorage.setItem('accessToken', accessToken);
                      if (refreshToken) localStorage.setItem('refreshToken', refreshToken);
            
                      localStorage.setItem("loginId", res.data.loginId);
                      localStorage.setItem("name", res.data.name);
                      localStorage.setItem("role", res.data.role);
            }
            return res;
        })
        .catch((err) => {
            console.log(`error: 로그인 실패 ${err}`);
            return err.response;
        });
}

export {login};