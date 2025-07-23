export const validators = {
  loginId: [
    // 5~16자, 소문자로 시작 + 영문/숫자만
    v => v.length === 0 || (v.length >= 5 && v.length <= 16),
    v => v.length === 0 || /^[a-z][a-z0-9]*$/.test(v),
    (v, { loginIdChecked, loginIdExists }) =>
          !loginIdChecked
            ? false
            : !loginIdExists,
  ],
  password: [
    // 6~12자
    v => v.length === 0 || (v.length >= 6 && v.length <= 12),
    // 영문 포함
    v => v.length === 0 || /[A-Za-z]/.test(v),
    // 숫자 포함
    v => v.length === 0 || /\d/.test(v),
    // 특수문자 포함 (!@#$%^&*)
    v => v.length === 0 || /[!@#$%^&*]/.test(v),
  ],
  name: [
    // 2~10자
    v => v.length === 0 || (v.length >= 2 && v.length <= 10),
    // 한글만
    v => v.length === 0 || /^[가-힣]+$/.test(v),
  ],
  phoneNumber: [
    // 숫자 11자리
    v => v.length === 0 || /^\d{11}$/.test(v),
  ],
  email: [
    // 5~100자
    v => v.length === 0 || (v.length >= 5 && v.length <= 100),
    // 이메일 포맷
    v => v.length === 0 || /^[^@\s]+@[^@\s]+\.[^@\s]+$/.test(v),
  ],
  birth: [
    // yyyyMMdd 형식 (8자리 숫자)
    v => v.length === 0 || /^\d{8}$/.test(v),
  ],
  // DTO에 없는 confirmPassword는 원래대로 유지
  confirmPassword: [
    (v, allValues) => v.length === 0 || v === allValues.password,
  ],
};