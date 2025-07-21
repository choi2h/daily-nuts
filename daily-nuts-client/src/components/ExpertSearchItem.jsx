import defaultProfile from '../assets/images/default-profile.png';
import '../assets/css/SearchResult.css'; // CSS 포함 확실히 하기

function ExpertSearchItem({ expert }) {
  return (
    <div className="approval-item">
      <div className="approval-content">
        <div className="payment-info-profile">
          <div className="profile-image-wrapper">
            <img 
              src={expert.profileImageUrl || defaultProfile}
              className="profile-image"
              alt="전문가 프로필"
            />
          </div>
          <div className="approval-info">
            <h4 className="author-name">{expert.name}</h4>
            <p className="next-payment">작성한 글: {expert.postCount}개</p>
          </div>
        </div>
        <button className="payment-button">
          {expert.isSubscribed ? '구독 중' : '구독하기'}
        </button>
      </div>
    </div>
  );
}

export default ExpertSearchItem;
