import defaultProfile from '../assets/images/default-profile.png';

function SubscribeItem ({subscribeInfo}) {
    return (
        <>
            <div key={subscribeInfo.id} className="approval-item">
                <div className="approval-content">
                    <div className="payment-info-profile">
                        <div className="profile-image-wrapper">
                        <img 
                            src={defaultProfile} 
                            className="profile-image"
                        />
                        {/* <div className="online-indicator"></div> */}
                        </div>
                        <div className="approval-info">
                        <h4 className="author-name">{subscribeInfo.name}</h4>
                        <p className="next-payment">{subscribeInfo.subscribeDate}</p>
                        </div>
                    </div>
                    
                    <button className="payment-button">결제하기</button>
                </div>
            </div>
        </>
    )
}

export default SubscribeItem;