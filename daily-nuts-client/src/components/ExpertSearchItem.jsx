import defaultProfile from '../assets/images/default-profile.png';
import '../assets/css/SearchResult.css';
import { useNavigate } from 'react-router';

function ExpertSearchItem({ expert }) {
  console.log('expert:', expert);
  const navigate = useNavigate();

  const handleClick = () => {
    if (expert && expert.memberId) {
      navigate(`/profile/${expert.memberId}`);
    } else {
      console.warn('잘못된 expert 데이터:', expert);
    }
  };

  return (
    <div className="approval-item" onClick={handleClick} style={{ cursor: 'pointer' }}>
      <div className="approval-content">
        <div className="payment-info-profile">
          <div className="profile-image-wrapper">
            <img 
              src={`/profile-images/${expert.profileImage || defaultProfile}`}
              className="profile-image"
              alt="전문가 프로필"
            />
          </div>
          <div className="approval-info">
            <h4 className="author-name">{expert.name}</h4>
            <p className="next-payment">작성한 글: {expert.postCount}개</p>
          </div>
        </div>
        {expert.subscribed ? (
          <button className="subscribed-btn">구독 중</button>
        ) : null}
      </div>
    </div>
  );
}

export default ExpertSearchItem;
