import '../assets/css/Notification.css';
import { IoMdHeartEmpty, IoMdHeart, IoMdContact } from "react-icons/io";

const getIcon = (type) => {
    if(type === "LIKES") return <IoMdHeart size={24}/>
    if(type === "FOLLOW") return <IoMdContact size={24}/>
    if(type === "COMMENT") return <IoMdHeartEmpty size={24}/>
}

const NotificationItem = ({notification}) => {
  return (
        <div key={notification.id} className={`notification-item ${notification.isRead ? 'read' : ''}`}>
            <div className={`notification-icon`}>
                {getIcon(notification.type)}
                {/* <div className="notification-icon-dot"></div> */}
            </div>
            
            <div className="notification-content">
                <div className="notification-item-title">
                {notification.title}
                </div>
                <div className="notification-item-subtitle">
                {notification.createdAt}
                </div>
            </div>
        
            <div className="notification-indicator">
            </div>
        </div>
  );
};

export default NotificationItem;