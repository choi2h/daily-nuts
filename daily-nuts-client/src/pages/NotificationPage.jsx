import DefaultLayout from "../layers/DefaultLayout";
import { useEffect } from "react";
import { connectEventSource } from "../service/NotificationService";
import NotificationItem from "../components/NotificationItem";
import '../assets/css/Notification.css';
import BlankHeaderLayout from "../layers/BlankHeaderLayout";

  const notifications = [
    {
      id: 1,
      type: 'LIKES',
      title: '박00님이 좋아요를 클릭했습니다.',
      isRead: false,
      createdAt: '2025-01-14'
    },
    {
      id: 2,
      type: 'FOLLOW',
      title: '박00님이 회원님을 구독했습니다.',
      isRead: true,
      createdAt: '2025-01-14'
    }
  ];

function NotificationPage() {

    useEffect(() => {
        connectEventSource(2);
    }, [])

    return (
        <DefaultLayout className="app">
            <BlankHeaderLayout isUseSearch={false} title='알림정보'>
                <div className="notification-container">
            
                    <div className="notification-list">
                        {
                            notifications.map((notification) => <NotificationItem key={notification.id} notification={notification}/>)
                        }
                    </div>
                </div>
            </BlankHeaderLayout>
        </DefaultLayout>
    );
}

export default NotificationPage;