import { useState } from "react";
import "../assets/css/subscription-modal.css";
import axios from "axios";

const SubscriptionModal = ({ isOpen, onClose, expertId, expertName, price}) => {
  const [loading, setLoading] = useState(false);
  
  if (!isOpen) return null;

  const handleSubscribe = async () => {
    try {
      setLoading(true);

      //결제 준비 요청
      const prepareRes = await axios.post("/payment/prepare", { expertId },);

      const {
        merchantUid,
        amount,
        name,
        buyerEmail,
        buyerName,
        buyerTel,
        buyerAddr,
        buyerPostcode,
      } = prepareRes.data;

      //포트원 결제창 호출
      const { IMP } = window;
      IMP.init("imp11205567");

      IMP.request_pay(
        {
          pg: "html5_inicis",
          pay_method: "card",
          merchant_uid: merchantUid,
          name: name,
          amount: amount,
          buyer_email: buyerEmail,
          buyer_name: buyerName,
          buyer_tel: buyerTel,
          buyer_addr: buyerAddr,
          buyer_postcode: buyerPostcode,
        },
        async function (rsp) {
          if (rsp.success) {
            //결제 성공 시 confirm 요청
            await axios.post("/payment/confirm",{
                impUid: rsp.imp_uid,
                merchantUid: merchantUid,
              });
            alert("구독이 완료되었습니다!");
            onClose();
            window.location.reload();
          } else {
            alert("결제가 실패하거나 취소되었습니다.");
          }
        }
      );
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
            성격심리학 박사 | 발달심리 전문가 | 중단연구 분석가 <br />
            - 연세대학교 심리학 박사 <br />
            - 성격 및 발달 종단 연구 다수 참여 (청소년 ~ 장노년층 대상) <br />
            - 「성격의 안정성과 변화 가능성」 주제로 국내외 학술지 논문 다수 게재 <br />
            - 대중심리 칼럼, 방송 심리자문 등 경력 보유
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