import '../assets/css/Signup.css';
import { useState } from 'react';
import { FaUser, FaEnvelope, FaLock, FaEye, FaEyeSlash, FaPhone } from 'react-icons/fa';
import { useNavigate } from 'react-router';

const SignupPage = () => {
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const navigate = useNavigate();

  const togglePassword = () => setShowPassword(prev => !prev);
  const toggleConfirmPassword = () => setShowConfirmPassword(prev => !prev);

  const handleLogin = () => {
    navigate('/login');
  }

  return (
    <div className="signup-wrapper">
      <div className="signup-card">
        <h2>회원가입</h2>
        <form className="signup-form">
          <label htmlFor="name">이름</label>
          <div className="input-icon-wrapper">
            <FaUser className="input-icon" />
            <input id="name" type="text" placeholder="Enter your name" autoComplete="off" />
          </div>

          <label htmlFor="userid">아이디</label>
          <div className="id-check-wrapper">
            <input id="userid" type="text" placeholder="2~6글자를 입력해주세요" autoComplete="off" />
            <button type="button" className="check-btn">중복검사</button>
          </div>

          <label htmlFor="phone">휴대폰 번호</label>
          <div className="input-icon-wrapper">
            <FaPhone className="input-icon" />
            <input id="phone" type="tel" placeholder="Enter your phone number" autoComplete="off" />
          </div>

          <label htmlFor="email">이메일</label>
          <div className="input-icon-wrapper">
            <FaEnvelope className="input-icon" />
            <input id="email" type="email" placeholder="Enter your email address" autoComplete="off" />
          </div>

          <label htmlFor="password">비밀번호</label>
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

          <label htmlFor="confirmPassword">비밀번호 확인</label>
          <div className="password-wrapper">
            <div className="input-icon-wrapper">
              <FaLock className="input-icon" />
              <input
                id="confirmPassword"
                type={showConfirmPassword ? 'text' : 'password'}
                placeholder="Confirm Password"
                autoComplete="off"
              />
            </div>
            <button type="button" className="toggle-btn" onClick={toggleConfirmPassword}>
              {showConfirmPassword ? <FaEyeSlash /> : <FaEye />}
            </button>
          </div>

          <div className="birth-gender-wrapper">
            <div className="birth-wrapper">
              <label htmlFor="birth">생년월일</label>
              <input id="birth" type="text" placeholder="YYYY-MM-DD" autoComplete="off" />
            </div>
            <div className="gender-wrapper">
              <label htmlFor="gender">성별</label>
              <select id="gender" defaultValue="">
                <option value="" disabled>선택</option>
                <option value="male">남성</option>
                <option value="female">여성</option>
                <option value="other">기타</option>
              </select>
            </div>
          </div>

          <button type="submit" className="submit-btn">회원가입</button>
        </form>

        <p className="login-text" onClick={handleLogin}>
          이미 회원가입 하셨나요? <strong>로그인</strong>
        </p>
      </div>
    </div>
  );
};

export default SignupPage;