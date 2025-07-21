import defaultProfile from '../assets/images/default-profile.png';
import '../assets/css/Subscribe.css';
import { useNavigate } from 'react-router';

function SubscribeItem ({subscribeInfo}) {
    const navigate = useNavigate();

      const moveProfile = (id) => {
        navigate(`/profile/${id}`);
    }

    return (
        <>
            <div key={subscribeInfo.id} className="approval-item" >
                <div className="approval-content" onClick={() => moveProfile(subscribeInfo.id)}>
                    <div className="payment-info-profile">
                        <div className="profile-image-wrapper">
                        <img 
                            src={!subscribeInfo.profileImageUrl ? defaultProfile : subscribeInfo.profileImageUrl} 
                            className="profile-image"
                        />
                        {/* <div className="online-indicator"></div> */}
                        </div>
                        <div className="approval-info">
                        <h4 className="author-name">{subscribeInfo.name}</h4>
                        
                        </div>
                    </div>
                    
                    {/* <button className="payment-button">결제하기</button> */}
                    <p className="next-payment">{subscribeInfo.subscribeDate}</p>
                </div>
            </div>
        </>
    )
}

export default SubscribeItem;