import useNotifications from "../context/useNotifications";
import NotificationToast from "./NotificationToast";

const NotificationContainer = () => {
  const { notifications, removeNotification } = useNotifications();

  return (
    <div style={{ position: "fixed", top: "20px", right: "20px", zIndex: 9999 }}>
      {notifications.map((n) => (
        <NotificationToast
          key={n.id}
          id={n.id}
          message={n.message}
          onClose={removeNotification}
        />
      ))}
    </div>
  );
};

export default NotificationContainer;