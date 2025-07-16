import '../assets/css/Join.css';
import { useState } from 'react';
import { FaUser, FaEnvelope, FaLock, FaEye, FaEyeSlash, FaPhone } from 'react-icons/fa';

const LoginPage = () => {
  const [showPassword, setShowPassword] = useState(false);

  const togglePassword = () => setShowPassword(prev => !prev);

  return (
    <div className="signup-container">
      <h2>로그인</h2>
      <form className="signup-form">

        <label htmlFor="userid">아이디</label>
        <div className="id-check-wrapper">
          <input
            id="userid"
            type="text"
            placeholder="2~6글자를 입력해주세요"
            autoComplete="off"
          />
        </div>

        <label htmlFor="password">Password</label>
        <div className="password-wrapper">
          <div className="input-icon-wrapper">
            <FaLock className="input-icon" />
            <input
              id="password"
              type={showPassword ? 'text' : 'password'}
              placeholder="Create Password"
              autoComplete="off"
            />
          </div>
          <button type="button" className="toggle-btn" onClick={togglePassword}>
            {showPassword ? <FaEyeSlash /> : <FaEye />}
          </button>
        </div>

        <button type="submit" className="submit-btn">로그인</button>
      </form>

      <p className="login-text">
        아직  회원가입을 안하셨나요? <strong>Signup</strong>
      </p>
    </div>
  );
};

export default LoginPage;