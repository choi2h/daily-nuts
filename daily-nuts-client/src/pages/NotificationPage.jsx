import DefaultLayout from "../layers/DefaultLayout";
import { useEffect, useState } from "react";
import { getNotifications } from "../service/NotificationService";
import NotificationItem from "../components/NotificationItem";
import '../assets/css/Notification.css';
import BlankHeaderLayout from "../layers/BlankHeaderLayout";
import { HttpStatusCode } from "axios";

function NotificationPage() {
    const [notifications, setNotifications] = useState([]);

    useEffect(() => {
        getNotifications()
          .then((res) => {
            if(res.status === HttpStatusCode.Ok) {
              console.log(res.data);
              setNotifications(res.data);
            }
          });
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