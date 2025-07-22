import { useContext } from "react";
import NotificationContext from "./NotificationContext";

const useNotifications = () => useContext(NotificationContext);

export default useNotifications;