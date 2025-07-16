import "../assets/css/SubscribeModal.css";

const SubscriptionModal = ({ isOpen, onClose, expertName, price }) => {
  if (!isOpen) return null;

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-container" onClick={(e) => e.stopPropagation()}>
        <button className="close-btn" onClick={onClose}>
          ✕
        </button>

        <h2 className="modal-title">{expertName}님을 구독하시겠습니까?</h2>
        <p className="modal-price">
          <span>{price.toLocaleString()}</span> 원
        </p>

        <div className="expert-info">
          <h3>{expertName}</h3>
          <p>
            성격심리학 박사 | 발달심리 전문가 | 중단연구 분석가 <br />
            - 연세대학교 심리학 박사 <br />
            - 성격 및 발달 종단 연구 다수 참여 (청소년 ~ 장노년층 대상) <br />
            - 「성격의 안정성과 변화 가능성」 주제로 국내외 학술지 논문 다수 게재 <br />
            - 대중심리 칼럼, 방송 심리자문 등 경력 보유
          </p>
        </div>

        <button className="subscribe-btn">구독하기</button>
      </div>
    </div>
  );
};

export default SubscriptionModal;