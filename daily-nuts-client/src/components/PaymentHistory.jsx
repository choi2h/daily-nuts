import '../assets/css/payment-history.css';
import defaultProfile from '../assets/images/default-profile.png';

const PaymentHistory = () => {
  const approvalItems = [
    {
      id: 1,
      name: '김 ○○ 작가님',
      nextPayment: '2025년 8월 20일',
      profileImage: '/api/placeholder/60/60'
    },
    {
      id: 2,
      name: '이 ○○ 작가님',
      nextPayment: '2025년 9월 26일',
      profileImage: '/api/placeholder/60/60'
    }
  ];

  return (
    <div className="profile-card">
      <h2 className="page-title">결제내역</h2>
      
      <div className="approval-list">
        {approvalItems.map((item) => (
          <div key={item.id} className="approval-item">
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
                    <h4 className="author-name">{item.name}</h4>
                    <p className="next-payment">다음 결제일은 {item.nextPayment}입니다.</p>
                  </div>
                </div>
              
              <button className="payment-button">결제하기</button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default PaymentHistory;