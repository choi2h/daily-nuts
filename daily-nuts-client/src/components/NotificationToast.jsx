import React, { useEffect } from "react";
import "../assets/css/NotificationToast.css";
import { FaBullhorn } from "react-icons/fa";

const NotificationToast = ({ id, message, onClose }) => {
  useEffect(() => {
    const timer = setTimeout(() => {
      onClose(id);
    }, 3000); // 3초 후 자동 닫힘

    return () => clearTimeout(timer);
  }, [onClose, id]);

  return (
    <div className="notification-toast">
      <div className="toast-icon">
        <FaBullhorn size={20} />
      </div>
      <div className="toast-content">
        <strong>알림</strong>
        <p>{message}</p>
      </div>
    </div>
  );
};

export default NotificationToast;