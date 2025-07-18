import '../assets/css/Login.css';
import { useState } from 'react';
import { FaLock } from 'react-icons/fa';
import '../assets/css/Login.css';
import { useNavigate } from 'react-router';
import { login } from '../service/MemberInfoService';
import { HttpStatusCode } from 'axios';

const LoginPage = () => {
  const [loginInfo, setLoginInfo] = useState({loginId: '', password: ''});
  const [showPassword, setShowPassword] = useState(false);
  const togglePassword = () => setShowPassword(prev => !prev);
  const navigate = useNavigate();

  const handleJoin = () => {
    navigate('/signup');
  }

  const handleLogin = (e) => {
      e.preventDefault(); // 폼의 기본 제출 동작 방지
      console.log("login!!!");
      login(loginInfo).then((res) => {
        if(res.status === HttpStatusCode.Ok) {
          const accessToken  = res.headers['authorization'];      
          const refreshToken = res.headers['refresh-token'];

          if (accessToken)  localStorage.setItem('accessToken', accessToken);
          if (refreshToken) localStorage.setItem('refreshToken', refreshToken);

          localStorage.setItem("loginId", res.data.loginId);
          localStorage.setItem("name", res.data.name);
          localStorage.setItem("role", res.data.role);
          navigate('/');
        } else {
          alert(res.data.message);
        }
      });
  }

  const handleLoginId = (e) => {
      setLoginInfo({...loginInfo, loginId: e.target.value});
  }

   const handleLoginPassword = (e) => {
      setLoginInfo({...loginInfo, password: e.target.value});
  }

  return (
      <div className="login-wrapper">
        <div className="login-container">
            <h2>로그인</h2>
            <form className="login-form">
        
            <div style={{textAlign:'left'}}>
              <label htmlFor="userid">아이디</label>
              <div className="input-icon-wrapper">
                <input
                  id="userid"
                  type="text"
                  placeholder="아이디를 입력해주세요"
                  autoComplete="off"
                  value={loginInfo.id}
                  onChange={(e) => handleLoginId(e)}
                />
              </div>
            </div>

            <div style={{textAlign:'left'}}>
              <label htmlFor="password">비밀번호</label>
              <div className="password-wrapper">
                <div className="input-icon-wrapper">
                  <FaLock className="input-icon" />
                  <input
                    id="password"
                    type={showPassword ? 'text' : 'password'}
                    placeholder="비밀번호를 입력해주세요"
                    autoComplete="off"
                    value={loginInfo.password}
                    onChange={(e) => handleLoginPassword(e)}
                  />
                </div>
                <button
                  type="button"
                  className="toggle-btn"
                  onClick={togglePassword}
                >
                  {showPassword ? '숨김' : '보기'}
                </button>
              </div>
            </div>

            <button type="submit" className="submit-btn" onClick={handleLogin}>로그인</button>
          </form>

          <p className="login-text" onClick={handleJoin}>
            아직 회원가입을 안하셨나요? <strong>회원가입</strong>
          </p>
        </div>
      </div>
  );
};

export default LoginPage;