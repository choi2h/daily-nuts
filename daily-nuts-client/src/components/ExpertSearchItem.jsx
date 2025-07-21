import defaultProfile from '../assets/images/default-profile.png';
import '../assets/css/SearchResult.css';

function ExpertSearchItem({ expert }) {
  console.log('expert:', expert);
  console.log('isSubscribed:', expert.isSubscribed);
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
        {expert.subscribed ? (
          <button className="subscribed-btn">구독 중</button>
        ) : null}
      </div>
    </div>
  );
}

export default ExpertSearchItem;
