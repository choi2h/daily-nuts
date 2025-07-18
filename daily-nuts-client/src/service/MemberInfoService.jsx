import axios, { HttpStatusCode } from "axios"

// 로그인
const login = async (loginInfo) => {
    return axios.post('/member/login', loginInfo, {
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

// 회원가입
const signup = async (signupInfo) => {
     if (
    !signupInfo ||
    !signupInfo.name ||
    !signupInfo.loginId ||
    !signupInfo.email ||
    !signupInfo.birth ||
    !signupInfo.phoneNumber ||
    !signupInfo.password ||
    !signupInfo.confirmPassword
  ) {
    console.warn('signupInfo에 필수 값이 누락되어 있습니다:', signupInfo);
    // 에러 객체를 반환할 수도 있고, 원하는 로직에 맞춰 처리하세요.
    return Promise.reject(new Error('필수 회원가입 정보가 누락되었습니다.'));
  }

    return axios.post('/member/signup', signupInfo, {
        })
        .then((res) => {
            alert('회원가입이 완료되었습니다!');
            // console.log(`res: 회원가입 성공! ${res}`);
            return res;
        })
        .catch((err) => {
            console.log(`error: 회원가입 실패 ${err}`);
            return err.response;
        });
}

// 아이디 중복 체크
const existsLoginId = async (loginId) => {
  const res = await axios.get("/member/exist", {
    params: { loginId },
  });
  // { exists: true/false } 가 반환됨
  return res.data.exists;
};

export { signup, existsLoginId, login };