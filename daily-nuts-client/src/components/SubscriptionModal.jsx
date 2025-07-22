import "../assets/css/SubscribeModal.css";
import { useState } from "react";
import axios from "axios";

const SubscriptionModal = ({ isOpen, onClose, expertId, expertName, expertDescription, price}) => {
  const [loading, setLoading] = useState(false);
  
  if (!isOpen) return null;

  //결제 요청 객체 생성
  const createPayRequest = (data) => ({
    pg: "html5_inicis",
    pay_method: "card",
    merchant_uid: data.merchantUid,
    name: data.name,
    amount: data.amount,
    buyer_email: data.buyerEmail,
    buyer_name: data.buyerName,
    buyer_tel: data.buyerTel,
    buyer_addr: data.buyerAddr,
    buyer_postcode: data.buyerPostcode,
  });

  const handleSubscribe = async () => {
    try {
      setLoading(true);

      //결제 요청 준비
      const prepareRes = await axios.post("/payment/prepare", { expertId });
      const { data } = prepareRes;

      const { IMP } = window;
      IMP.init("imp11205567");

      IMP.request_pay(createPayRequest(data), async function (rsp) {
        if (rsp.success) {
          await axios.post("/payment/confirm", {
            impUid: rsp.imp_uid,
            merchantUid: data.merchantUid,
          });
          alert("구독이 완료되었습니다!");
          onClose();
          window.location.reload();
        } else {
          alert("결제가 실패하거나 취소되었습니다.");
        }
      });
    } catch (err) {
      alert("결제 처리 중 오류가 발생했습니다.");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

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
            {expertDescription}
          </p>
        </div>

        <button className="subscribe-btn" onClick={handleSubscribe} disabled={loading}>
          {loading ? "처리중..." : "구독하기"}
        </button>
      </div>
    </div>
  );
};

export default SubscriptionModal;