import { HttpStatusCode } from "axios";
import "../assets/css/Signup.css";
import { useState } from "react";
import {
  FaUser,
  FaEnvelope,
  FaLock,
  FaKey,
  FaEye,
  FaEyeSlash,
  FaBirthdayCake,
  FaMobileAlt,
} from "react-icons/fa";
import { useNavigate } from "react-router";
import { validators } from "../utils/validators";
import { validationMessages } from "../utils/validationMessages";
import { signup } from "../service/MemberInfoService";

const SignupPage = () => {
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const navigate = useNavigate();

  const togglePassword = () => setShowPassword((prev) => !prev);
  const toggleConfirmPassword = () => setShowConfirmPassword((prev) => !prev);

  const [signupInfo, setSignupInfo] = useState({
    name: "",
    loginId: "",
    password: "",
    birth: "",
    phoneNumber: "",
    email: "",
    confirmPassword: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setSignupInfo((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault(); // 폼의 기본 제출 동작 방지
    console.log("signup!!!");
    signup(signupInfo).then((res) => {
      if (res.status === HttpStatusCode.Created) {
        console.log("회원가입 성공");
        navigate("/login");
      } else {
        alert("회원가입 실패");
      }
    });
  };

  // 필드값이 검증을 통과하는가
  const fieldValid = (field) => {
    return validators[field].every((fn) =>
      field === "confirmPassword"
        ? fn(signupInfo[field], signupInfo)
        : fn(signupInfo[field])
    );
  };

  // 값이 비어있는가
  const allFilled = Object.keys(signupInfo).every(
    (field) => signupInfo[field].length > 0
  );
  // 값이 fieldValid를 통과했는가
  const allValid = Object.keys(validators).every((field) => fieldValid(field));
  // 폼이 값을 갖고 있는가
  const hasValue = (field) => signupInfo[field].length > 0;

  return (
    <div className="signup-wrapper">
      <div className="signup-card">
        <h2>회원가입</h2>
        <form onSubmit={handleSubmit} className="signup-form">
          <label htmlFor="name">이름</label>
          <div
            className={`input-icon-wrapper ${
              hasValue("name") ? (fieldValid("name") ? "valid" : "invalid") : ""
            }`}
          >
            <FaUser className="input-icon" />
            <input
              id="name"
              type="text"
              placeholder="Enter your name"
              value={signupInfo.name}
              name="name"
              onChange={handleChange}
              autoComplete="off"
            />
            {hasValue("name") && !fieldValid("name") && (
              <span className="tooltip">
                <ul>
                  {validationMessages.name
                    .map((msg, i) => ({
                      msg,
                      valid: validators.name[i](signupInfo.name),
                    }))
                    .filter((item) => !item.valid)
                    .map((item, idx) => (
                      <li key={idx}>{item.msg}</li>
                    ))}
                </ul>
              </span>
            )}
          </div>

          <label htmlFor="loginId">아이디</label>
          <div
            className={`input-icon-wrapper ${
              hasValue("loginId")
                ? fieldValid("loginId")
                  ? "valid"
                  : "invalid"
                : ""
            }`}
          >
            <FaKey className="input-icon" />
            <input
              id="loginId"
              type="text"
              placeholder="Enter your loginID"
              value={signupInfo.loginId}
              name="loginId"
              onChange={handleChange}
              autoComplete="off"
            />
            {hasValue("loginId") && !fieldValid("loginId") && (
              <span className="tooltip">
                <ul>
                  {validationMessages.loginId
                    .map((msg, i) => ({
                      msg,
                      valid: validators.loginId[i](signupInfo.loginId),
                    }))
                    .filter((item) => !item.valid)
                    .map((item, idx) => (
                      <li key={idx}>{item.msg}</li>
                    ))}
                </ul>
              </span>
            )}

            <button type="button" className="check-btn">
              중복검사
            </button>
          </div>

          <label htmlFor="phoneNumber">휴대폰 번호</label>
          <div
            className={`input-icon-wrapper ${
              hasValue("phoneNumber")
                ? fieldValid("phoneNumber")
                  ? "valid"
                  : "invalid"
                : ""
            }`}
          >
            <FaMobileAlt className="input-icon" />
            <input
              id="phoneNumber"
              type="tel"
              placeholder="Enter your phone number"
              value={signupInfo.phoneNumber}
              name="phoneNumber"
              onChange={handleChange}
              autoComplete="off"
            />
            {hasValue("phoneNumber") && !fieldValid("phoneNumber") && (
              <span className="tooltip">
                <ul>
                  {validationMessages.phoneNumber
                    .map((msg, i) => ({
                      msg,
                      valid: validators.phoneNumber[i](signupInfo.phoneNumber),
                    }))
                    .filter((item) => !item.valid)
                    .map((item, idx) => (
                      <li key={idx}>{item.msg}</li>
                    ))}
                </ul>
              </span>
            )}
          </div>

          <label htmlFor="birth">생년월일</label>
          <div
            className={`input-icon-wrapper ${
              hasValue("birth")
                ? fieldValid("birth")
                  ? "valid"
                  : "invalid"
                : ""
            }`}
          >
            <FaBirthdayCake className="input-icon" />
            <input
              id="birth"
              type="text"
              placeholder="Enter your birth"
              value={signupInfo.birth}
              name="birth"
              onChange={handleChange}
              autoComplete="off"
            />
            {hasValue("birth") && !fieldValid("birth") && (
              <span className="tooltip">
                <ul>
                  {validationMessages.birth
                    .map((msg, i) => ({
                      msg,
                      valid: validators.birth[i](signupInfo.birth),
                    }))
                    .filter((item) => !item.valid)
                    .map((item, idx) => (
                      <li key={idx}>{item.msg}</li>
                    ))}
                </ul>
              </span>
            )}
          </div>

          <label htmlFor="email">이메일</label>
          <div
            className={`input-icon-wrapper ${
              hasValue("email")
                ? fieldValid("email")
                  ? "valid"
                  : "invalid"
                : ""
            }`}
          >
            <FaEnvelope className="input-icon" />
            <input
              id="email"
              type="email"
              placeholder="Enter your email address"
              value={signupInfo.email}
              name="email"
              onChange={handleChange}
              autoComplete="off"
            />
            {hasValue("email") && !fieldValid("email") && (
              <span className="tooltip">
                <ul>
                  {validationMessages.email
                    .map((msg, i) => ({
                      msg,
                      valid: validators.email[i](signupInfo.email),
                    }))
                    .filter((item) => !item.valid)
                    .map((item, idx) => (
                      <li key={idx}>{item.msg}</li>
                    ))}
                </ul>
              </span>
            )}
          </div>

          <label htmlFor="password">비밀번호</label>
          <div className="password-wrapper">
            <div
              className={`input-icon-wrapper ${
                hasValue("password")
                  ? fieldValid("password")
                    ? "valid"
                    : "invalid"
                  : ""
              }`}
            >
              <FaLock className="input-icon" />
              <input
                id="password"
                type={showPassword ? "text" : "password"}
                placeholder="Create Password"
                value={signupInfo.password}
                name="password"
                onChange={handleChange}
                autoComplete="off"
              />
              {hasValue("password") && !fieldValid("password") && (
                <span className="tooltip">
                  <ul>
                    {validationMessages.password
                      .map((msg, i) => ({
                        msg,
                        valid: validators.password[i](signupInfo.password),
                      }))
                      .filter((item) => !item.valid)
                      .map((item, idx) => (
                        <li key={idx}>{item.msg}</li>
                      ))}
                  </ul>
                </span>
              )}
            </div>
            <button
              type="button"
              className="toggle-btn"
              onClick={togglePassword}
            >
              {showPassword ? <FaEyeSlash /> : <FaEye />}
            </button>
          </div>

          <label htmlFor="confirmPassword">비밀번호 확인</label>
          <div className="password-wrapper">
            <div
              className={`input-icon-wrapper ${
                hasValue("confirmPassword")
                  ? fieldValid("confirmPassword")
                    ? "valid"
                    : "invalid"
                  : ""
              }`}
            >
              <FaLock className="input-icon" />
              <input
                id="confirmPassword"
                type={showConfirmPassword ? "text" : "password"}
                placeholder="Confirm Password"
                value={signupInfo.confirmPassword}
                name="confirmPassword"
                onChange={handleChange}
                autoComplete="off"
              />
              {hasValue("confirmPassword") &&
                !fieldValid("confirmPassword") && (
                  <span className="tooltip">
                    <ul>
                      {validationMessages.confirmPassword
                        .map((msg, i) => ({
                          msg,
                          valid: validators.confirmPassword[i](
                            signupInfo.confirmPassword,
                            signupInfo
                          ),
                        }))
                        .filter((item) => !item.valid)
                        .map((item, idx) => (
                          <li key={idx}>{item.msg}</li>
                        ))}
                    </ul>
                  </span>
                )}
            </div>
            <button
              type="button"
              className="toggle-btn"
              onClick={toggleConfirmPassword}
            >
              {showConfirmPassword ? <FaEyeSlash /> : <FaEye />}
            </button>
          </div>

          <button
            type="submit"
            className="submit-btn"
            disabled={!(allFilled && allValid)}
          >
            회원가입
          </button>
        </form>

        <p className="login-text" onClick={() => navigate("/login")}>
          이미 회원가입 하셨나요? <strong>로그인</strong>
        </p>
      </div>
    </div>
  );
};

export default SignupPage;
