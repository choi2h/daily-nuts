import axios from "axios";
import { useState, useEffect, useRef } from "react";
import "../assets/css/MyPage.css";
import defaultProfile from "../assets/images/default-profile.png";
import { validators } from "../utils/validators";
import { validationMessages } from "../utils/validationMessages";

function MyPageCard() {
  const fileInputRef = useRef(null); // ✅ 파일 input 참조 추가
  const [isEditMode, setIsEditMode] = useState(false);
  const [profileData, setProfileData] = useState({
    name: "",
    loginId: "",
    password: "········",
    birth: "",
    phoneNumber: "",
    email: "",
    updatedAt: "",
  });
  const [editData, setEditData] = useState({
  ...profileData,
  profileImage: null,  // 새로 선택한 이미지 파일
  previewUrl: defaultProfile // 미리보기 URL
});

  useEffect(() => {
    if (!isEditMode) {
      axios
        .get("/member")
        .then((res) => {
          const d = res.data;
          setProfileData({
            name: d.name,
            loginId: d.loginId,
            password: "········",
            birth: d.birth,
            phoneNumber: d.phoneNumber,
            email: d.email,
            updatedAt: d.updatedAt,
            profileImage: d.profileImageName ? `/profile-images/${res.data.profileImageName}`: defaultProfile,
          });
        })
        .catch((err) => console.error("로드 실패:", err));
    }
  }, [isEditMode]);

  const handleProfileImageUpload = (e) => {
    const file = e.target.files[0];
    if (file) {
      setEditData((prev) => ({
        ...prev,
        profileImage: file,
        previewUrl: URL.createObjectURL(file)
      }));
    }
  };

  const handleProfileImageClick = () => {
    if (fileInputRef.current) {
      fileInputRef.current.click(); // ✅ img 클릭 시 input 클릭 트리거
    }
  };

  const handleEdit = () => {
    setIsEditMode(true);
    setEditData({
      ...profileData,
      // YYYY-MM-DD → YYYYMMDD
      birth: profileData.birth ? profileData.birth.replace(/-/g, "") : "",
      // 010-2333-9036 → 01023339036
      phoneNumber: profileData.phoneNumber
        ? profileData.phoneNumber.replace(/\D/g, "")
        : "",
    });
  };

  const handleSave = () => {
    if (
      !fieldValid("birth") ||
      !fieldValid("phoneNumber") ||
      !fieldValid("email")
    )
      return;

    const formData = new FormData();
    const info = JSON.stringify({
      birth: editData.birth,
      phoneNumber: editData.phoneNumber,
      email: editData.email,
    });

    formData.append("info", new Blob([info], { type: "application/json" }));
    if (editData.profileImage) {
      formData.append("file", editData.profileImage);
    }

    axios
      .patch("/member/edit", formData, {
        headers: { "Content-Type": "multipart/form-data" },
      })
      .then((res) => {
          const d = res.data;

          // 서버 응답 기준으로 URL 생성 (없으면 기존 이미지로 fallback)           
          const finalProfile = d.profileImageName             
            ? `/profile-images/${d.profileImageName}`             
            : profileData.profileImage; // fallback to existing image            

          const newProfileData = {               
            name: d.name,               
            loginId: d.loginId,               
            password: "········",               
            birth: d.birth,               
            phoneNumber: d.phoneNumber,               
            email: d.email,               
            updatedAt: d.updatedAt,               
            profileImage: finalProfile,             
          };

            // ✅ localStorage에도 동일한 전체 객체 저장
            localStorage.setItem("profile", newProfileData.profileImage);

            setProfileData(newProfileData);

            // ✅ 커스텀 이벤트 발생
            window.dispatchEvent(new Event("profileUpdated"));

            console.log("✅ 프로필 이미지 저장됨:", newProfileData.profileImage);
            setIsEditMode(false);
    })
    .catch((err) => {
      console.error("정보 저장 실패:", err);
    });
  };

  const handleCancel = () => {
    setIsEditMode(false);
    setEditData({ ...profileData });
  };

  // --- 여기에만 추가/수정 ---
  // 입력값 변경 핸들러
  const handleInputChange = (field, value) => {
    setEditData((prev) => ({ ...prev, [field]: value }));
  };

  // 값 존재 여부
  const hasValue = (field) => {
    const v = editData[field];
    return typeof v === "string" && v.trim().length > 0;
  };

  // 유효성 검사
  const fieldValid = (field) => {
    const fns = validators[field];
    if (!fns) return true;
    return fns.every((fn) => fn(editData[field], editData));
  };

  // --- 수정 모드 JSX (조회 모드 이전까지) ---
  if (isEditMode) {
    return (
      <div className="mypage-card">
        <h2 className="page-title">회원정보 수정</h2>
        <div className="mypage-header">
          <div className="mypage-avatar-image">
            {/* ✅ 이미지 클릭 시 파일 업로드 */}
            <img
              className="profile-image"
              src={editData.profileImage || defaultProfile}
              alt="프로필 사진"
              onClick={handleProfileImageClick}
              style={{ cursor: "pointer" }} // ✅ 클릭 가능
            />
            <input
              type="file"
              accept=".jpg,.jpeg,.png"
              ref={fileInputRef} // ✅ ref 연결
              onChange={handleProfileImageUpload}
              style={{ display: "none" }} // 숨김 처리
            />
          </div>
          <h2 className="mypage-username">{profileData.name}</h2>
        </div>

        <div className="mypage-content">
          {/* 아이디 (읽기 전용) */}
          <div className="mypage-row">
            <span className="mypage-label">아이디</span>
            <span className="mypage-value">{profileData.loginId}</span>
          </div>

          {/* 생년월일 */}
          <div className="mypage-row">
            <span className="mypage-label">생년월일</span>
            <div
              className={`input-icon-wrapper ${
                hasValue("birth")
                  ? fieldValid("birth")
                    ? "valid"
                    : "invalid"
                  : ""
              }`}
            >
              <input
                type="text"
                className="mypage-input"
                value={editData.birth}
                onChange={(e) => handleInputChange("birth", e.target.value)}
                placeholder="생년월일을 입력하세요"
              />
              {hasValue("birth") && !fieldValid("birth") && (
                <span className="tooltip">
                  <ul>
                    {validationMessages.birth
                      .map((msg, i) => ({
                        msg,
                        valid: validators.birth[i](editData.birth),
                      }))
                      .filter((item) => !item.valid)
                      .map((item, idx) => (
                        <li key={idx}>{item.msg}</li>
                      ))}
                  </ul>
                </span>
              )}
            </div>
          </div>

          {/* 전화번호 */}
          <div className="mypage-row">
            <span className="mypage-label">전화번호</span>
            <div
              className={`input-icon-wrapper ${
                hasValue("phoneNumber")
                  ? fieldValid("phoneNumber")
                    ? "valid"
                    : "invalid"
                  : ""
              }`}
            >
              <input
                type="tel"
                className="mypage-input"
                value={editData.phoneNumber}
                onChange={(e) =>
                  handleInputChange("phoneNumber", e.target.value)
                }
                placeholder="전화번호를 입력하세요"
              />
              {hasValue("phoneNumber") && !fieldValid("phoneNumber") && (
                <span className="tooltip">
                  <ul>
                    {validationMessages.phoneNumber
                      .map((msg, i) => ({
                        msg,
                        valid: validators.phoneNumber[i](editData.phoneNumber),
                      }))
                      .filter((item) => !item.valid)
                      .map((item, idx) => (
                        <li key={idx}>{item.msg}</li>
                      ))}
                  </ul>
                </span>
              )}
            </div>
          </div>

          {/* 이메일 */}
          <div className="mypage-row">
            <span className="mypage-label">이메일</span>
            <div
              className={`input-icon-wrapper ${
                hasValue("email")
                  ? fieldValid("email")
                    ? "valid"
                    : "invalid"
                  : ""
              }`}
            >
              <input
                type="email"
                className="mypage-input"
                value={editData.email}
                onChange={(e) => handleInputChange("email", e.target.value)}
                placeholder="이메일을 입력하세요"
              />
              {hasValue("email") && !fieldValid("email") && (
                <span className="tooltip">
                  <ul>
                    {validationMessages.email
                      .map((msg, i) => ({
                        msg,
                        valid: validators.email[i](editData.email),
                      }))
                      .filter((item) => !item.valid)
                      .map((item, idx) => (
                        <li key={idx}>{item.msg}</li>
                      ))}
                  </ul>
                </span>
              )}
            </div>
          </div>
        </div>

        <div className="button-group">
          <button
            className="save-button"
            onClick={handleSave}
            disabled={
              !(
                hasValue("birth") &&
                fieldValid("birth") &&
                hasValue("phoneNumber") &&
                fieldValid("phoneNumber") &&
                hasValue("email") &&
                fieldValid("email")
              )
            }
          >
            저장하기
          </button>
          <button className="cancel-button" onClick={handleCancel}>
            취소
          </button>
        </div>
      </div>
    );
  }

  // 조회 화면
  return (
    <div className="mypage-card">
      <h2 className="page-title">회원정보</h2>
      <div className="mypage-header">
        <div className="mypage-avatar-image">
          <img
            className="profile-image"
            src={profileData.profileImage}
            alt="프로필 사진"
          />
        </div>
        <h2 className="mypage-username">{profileData.name}</h2>
      </div>

      <div className="mypage-content">
        <div className="mypage-row">
          <span className="mypage-label">아이디</span>
          <span className="mypage-value">{profileData.loginId}</span>
        </div>

        <div className="mypage-row">
          <span className="mypage-label">비밀번호</span>
          <span className="mypage-value">········</span>
        </div>

        <div className="mypage-row">
          <span className="mypage-label">생년월일</span>
          <span className="mypage-value">{profileData.birth}</span>
        </div>

        <div className="mypage-row">
          <span className="mypage-label">전화번호</span>
          <span className="mypage-value">{profileData.phoneNumber}</span>
        </div>

        <div className="mypage-row">
          <span className="mypage-label">이메일</span>
          <span className="mypage-value">{profileData.email}</span>
        </div>
      </div>

      <button className="edit-button" onClick={handleEdit}>
        수정하기
      </button>

      <div className="last-update">마지막 수정: {profileData.updatedAt}</div>
    </div>
  );
}

export default MyPageCard;
