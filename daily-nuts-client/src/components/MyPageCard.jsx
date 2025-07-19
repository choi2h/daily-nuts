import axios from "axios";
import { useState, useEffect } from "react";
import "../assets/css/MyPage.css";
import defaultProfile from "../assets/images/default-profile.png";

function MyPageCard() {
  const [isEditMode, setIsEditMode] = useState(false);
  const [profileData, setProfileData] = useState({
    name: "",
    loginId: "",
    password: "········",
    birth: "",
    phone: "",
    email: "",
    updatedAt: "",
  });

  const [editData, setEditData] = useState({ ...profileData });

  useEffect(() => {
    if (!isEditMode) {
      axios
        .get("/member")
        .then((res) => {
          const data = res.data;
          setProfileData({
            name: data.name,
            loginId: data.loginId,
            password: "········",
            birth: data.birth,
            phone: data.phone,
            email: data.email,
            updatedAt: data.updatedAt
          });
        })
        .catch((err) => {
          console.error("마이페이지 로드 실패", err.message);
          if (err.response) {
            console.error("▶︎ 응답 상태 코드:", err.response.status);
            console.error("▶︎ 응답 데이터:", err.response.data);
          } else if (err.request) {
            console.error("▶︎ 요청은 갔으나 응답이 없습니다.", err.request);
          }
        });
    }
  }, [isEditMode]);

  const handleEdit = () => {
    setIsEditMode(true);
    setEditData({ ...profileData });
  };

  const handleSave = () => {
    setProfileData({ ...editData });
    setIsEditMode(false);
    // 실제로는 여기서 서버에 데이터 저장
  };

  const handleCancel = () => {
    setEditData({ ...profileData });
    setIsEditMode(false);
  };

  const handleInputChange = (field, value) => {
    setEditData((prev) => ({
      ...prev,
      [field]: value,
    }));
  };

  // 수정 화면
  if (isEditMode) {
    return (
      <div className="mypage-card">
        <h2 className="page-title">회원정보 수정</h2>
        <div className="mypage-header">
          <div className="mypage-avatar-image">
            <img
              className="profile-image"
              src={defaultProfile}
              alt="프로필 사진"
            />
          </div>
          <h2 className="mypage-username">{profileData.username}</h2>
        </div>

        <div className="mypage-content">
          <div className="mypage-row">
            <span className="mypage-label">아이디</span>
            <span className="mypage-value">{profileData.userId}</span>
          </div>

          <div className="mypage-row">
            <span className="mypage-label">비밀번호</span>
            <input
              type="password"
              className="mypage-input"
              placeholder="새 비밀번호를 입력하세요"
              onChange={(e) => handleInputChange("password", e.target.value)}
            />
          </div>

          <div className="mypage-row">
            <span className="mypage-label">비밀번호 확인</span>
            <input
              type="password"
              className="mypage-input"
              placeholder="새 비밀번호를 입력하세요"
              onChange={(e) => handleInputChange("password", e.target.value)}
            />
          </div>

          <div className="mypage-row">
            <span className="mypage-label">생년월일</span>
            <span className="mypage-value">{profileData.birthDate}</span>
          </div>

          <div className="mypage-row">
            <span className="mypage-label">전화번호</span>
            <input
              type="tel"
              className="mypage-input"
              value={editData.phone}
              onChange={(e) => handleInputChange("phone", e.target.value)}
              placeholder="전화번호를 입력하세요"
            />
          </div>

          <div className="mypage-row">
            <span className="mypage-label">이메일</span>
            <input
              type="email"
              className="mypage-input"
              value={editData.email}
              onChange={(e) => handleInputChange("email", e.target.value)}
              placeholder="이메일을 입력하세요"
            />
          </div>
        </div>

        <div className="button-group">
          <button className="save-button" onClick={handleSave}>
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
            src={defaultProfile}
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
          <span className="mypage-value">{profileData.phone}</span>
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
