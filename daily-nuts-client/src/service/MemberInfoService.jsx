import axios from "axios"

const login = async (loginInfo) => {
    return axios.post('/member/login', loginInfo, {
       // ← 이걸 설정해야 쿠키가 자동으로 저장됨
        })
        .then((res) => {
            console.log(res);
            // console.log(`res: 로그인 성공! ${res}`);
            return res;
        })
        .catch((err) => {
            console.log(`error: 로그인 실패 ${err}`);
            return err.response;
        });
}

export {login};