import { useState } from 'react';
import '../assets/css/profile-card.css';

function ProfileCard() {
    const [isEditMode, setIsEditMode] = useState(false);
    const [profileData, setProfileData] = useState({
        username: '김00',
        userId: 'hahahahaha',
        password: '········',
        gender: '남성',
        birthDate: '2025.12.10',
        phone: '01000000000',
        email: 'user@example.com'
    });

    const [editData, setEditData] = useState({ ...profileData });

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
        setEditData(prev => ({
            ...prev,
            [field]: value
        }));
    };

    // 수정 화면
    if (isEditMode) {
        return (
            <div className="profile-card">
                <h2 className="page-title">회원정보 수정</h2>
                <div className="profile-header">
                    <div className="profile-avatar-image">
                        👤
                    </div>
                    <input
                        type="text"
                        className="profile-username-input"
                        value={editData.username}
                        onChange={(e) => handleInputChange('username', e.target.value)}
                        placeholder="이름을 입력하세요"
                    />
                </div>
                
                <div className="profile-content">
                    <div className="profile-row">
                        <span className="profile-label">아이디</span>
                        <input
                            type="text"
                            className="profile-input"
                            value={editData.userId}
                            onChange={(e) => handleInputChange('userId', e.target.value)}
                            placeholder="아이디를 입력하세요"
                        />
                    </div>
                    
                    <div className="profile-row">
                        <span className="profile-label">비밀번호</span>
                        <input
                            type="password"
                            className="profile-input"
                            placeholder="새 비밀번호를 입력하세요"
                            onChange={(e) => handleInputChange('password', e.target.value)}
                        />
                    </div>
                    
                    <div className="profile-row">
                        <span className="profile-label">성별</span>
                        <select
                            className="profile-select"
                            value={editData.gender}
                            onChange={(e) => handleInputChange('gender', e.target.value)}
                        >
                            <option value="남성">남성</option>
                            <option value="여성">여성</option>
                        </select>
                    </div>
                    
                    <div className="profile-row">
                        <span className="profile-label">생년월일</span>
                        <input
                            type="date"
                            className="profile-input"
                            value={editData.birthDate.replace(/\./g, '-')}
                            onChange={(e) => handleInputChange('birthDate', e.target.value.replace(/-/g, '.'))}
                        />
                    </div>
                    
                    <div className="profile-row">
                        <span className="profile-label">전화번호</span>
                        <input
                            type="tel"
                            className="profile-input"
                            value={editData.phone}
                            onChange={(e) => handleInputChange('phone', e.target.value)}
                            placeholder="전화번호를 입력하세요"
                        />
                    </div>
                    
                    <div className="profile-row">
                        <span className="profile-label">이메일</span>
                        <input
                            type="email"
                            className="profile-input"
                            value={editData.email}
                            onChange={(e) => handleInputChange('email', e.target.value)}
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
        <div className="profile-card">
            <h2 className="page-title">회원정보</h2>
            <div className="profile-header">
                <div className="profile-avatar-image">
                    👤
                </div>
                <h2 className="profile-username">{profileData.username}</h2>
            </div>
            
            <div className="profile-content">
                <div className="profile-row">
                    <span className="profile-label">아이디</span>
                    <span className="profile-value">{profileData.userId}</span>
                </div>
                
                <div className="profile-row">
                    <span className="profile-label">비밀번호</span>
                    <span className="profile-value">········</span>
                </div>
                
                <div className="profile-row">
                    <span className="profile-label">성별</span>
                    <span className="profile-value">{profileData.gender}</span>
                </div>
                
                <div className="profile-row">
                    <span className="profile-label">생년월일</span>
                    <span className="profile-value">{profileData.birthDate}</span>
                </div>
                
                <div className="profile-row">
                    <span className="profile-label">전화번호</span>
                    <span className="profile-value">{profileData.phone}</span>
                </div>
                
                <div className="profile-row">
                    <span className="profile-label">이메일</span>
                    <span className="profile-value">{profileData.email}</span>
                </div>
            </div>
            
            <button className="edit-button" onClick={handleEdit}>
                수정하기
            </button>
            
            <div className="last-update">
                마지막 수정: 2025.07.07
            </div>
        </div>
    );
}

export default ProfileCard;