import '../assets/css/Notification.css';
import { IoMdHeartEmpty, IoMdHeart, IoMdContact } from "react-icons/io";
import { IoCheckmarkCircle, IoEllipseOutline } from "react-icons/io5";
import { useNavigate } from 'react-router';
import { updateReadNotification } from '../service/NotificationService';

const getIcon = (type) => {
    if(type === "LIKES") return <IoMdHeart size={24}/>
    if(type === "FOLLOW") return <IoMdContact size={24}/>
    if(type === "COMMENT") return <IoMdHeartEmpty size={24}/>
}

const NotificationItem = ({notification}) => {
    const navigate = useNavigate();

    const onClick = () => {
        updateReadNotification(notification.id);

        const type = notification.type;
        if(type === 'LIKES' || type === 'COMMENT') {
            navigate(`/post/${notification.postId}`);
        }
    }


    return (
            <div 
            key={notification.id} 
            className={`notification-item ${notification.isRead ? 'read' : 'unread'}`}
            onClick={onClick}>
                <div className={`notification-icon`}>
                    {getIcon(notification.type)}
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
                    {notification.isRead ? (
                        <IoCheckmarkCircle className="read-icon" size={18} />
                    ) : (
                        <IoEllipseOutline className="unread-icon" size={18} />
                    )}
                </div>
            </div>
    );
};

export default NotificationItem;