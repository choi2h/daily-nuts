import '../assets/css/PaymentHistory.css';
import { useEffect, useState } from 'react';
import defaultProfile from '../assets/images/default-profile.png';
import axios from 'axios';
import SubscriptionModal from './SubscriptionModal';

const PaymentHistory = () => {
  const [approvalItems, setApprovalItems] = useState([]);
  const [modalOpen, setModalOpen] = useState(false);
  const [selectedExpert, setSelectedExpert] = useState(null);

  const handleOpenModal = (expert) => {
    setSelectedExpert(expert);
    setModalOpen(true);
  };

  const shouldShowPaymentButton = (nextPayment) => {
    const today = new Date();
    const paymentDate = new Date(nextPayment);
    const diffTime = paymentDate.getTime() - today.getTime();
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
    return diffDays <= 2;
  };

  useEffect(() => {
    axios
      .get('/payment/status')
      .then((res) => {
        const items = res.data.payments.map((item, idx) => ({
          id: item.paymentId ?? idx,
          expertName: item.expertName,
          name: `${item.expertName} 작가님`,
          price: item.amount,
          nextPayment: new Date(item.expireAt).toLocaleDateString('ko-KR', {
            year: 'numeric',
            month: 'long',
            day: 'numeric',
          }),
          profileImage: defaultProfile
        }));
        setApprovalItems(items);
      })
      .catch((err) => {
        console.error('결제내역 불러오기 실패', err);
      });
  }, []);

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
                {shouldShowPaymentButton(item.nextPayment) && (
                  <button className="payment-button" 
                    onClick={() => handleOpenModal({
                      expertId: item.expertId,
                      expertName: item.expertName,
                      price: item.price,
                    })}> 결제하기 
                  </button>
                )}
            </div>
          </div>
        ))}
      </div>
      <SubscriptionModal
        isOpen={modalOpen}
        onClose={() => setModalOpen(false)}
        expertId={selectedExpert?.expertId}
        expertName={selectedExpert?.expertName}
        price={selectedExpert?.price}
      />
    </div>
  );
};

export default PaymentHistory;