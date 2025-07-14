import '../assets/css/profile-card.css';
import DefaultProfile from '../assets/images/default-profile.png';

function ProfileCard() {
    return (
        <div className="profile-card">
        <div className="profile-header">
            <img 
            src={DefaultProfile} 
            alt="Profile" 
            className="profile-image"
            />
            <h2 className="profile-username">mizanurrahman</h2>
        </div>
        
        <div className="profile-content">
            <div className="profile-row">
            <span className="profile-label">아이디</span>
            <span className="profile-value">hahahahaha</span>
            </div>
            
            <div className="profile-row">
            <span className="profile-label">비밀번호</span>
            <span className="profile-value">········</span>
            </div>
            
            <div className="profile-row">
            <span className="profile-label">성별</span>
            <span className="profile-value">남성</span>
            </div>
            
            <div className="profile-row">
            <span className="profile-label">생년월일</span>
            <span className="profile-value">2025.12.10</span>
            </div>
            
            <div className="profile-row">
            <span className="profile-label">전화번호</span>
            <span className="profile-value">01000000000</span>
            </div>
            
            <div className="profile-row">
            <span className="profile-label">이메일</span>
            <span className="profile-value">user@example.com</span>
            </div>
        </div>
        
        <button className="edit-button">수정하기</button>
        
        <div className="last-update">
            마지막 수정: 2025.07.07
        </div>
        </div>
    );
}

export default ProfileCard;