import axios from "axios";
import { EventSourcePolyfill } from "event-source-polyfill";

const BASE_URL = import.meta.env.VITE_API_SERVER_ADDRESS;
const NOTIFICATION_API_PREFIX = "/notification";
const connectEventSource = (onMessageCallback) => {
  let retryCount = 0; // ✅ 개별 SSE마다 관리
  const MAX_RETRY = 1;

  const createConnection = () => {
    const url = `${BASE_URL}${NOTIFICATION_API_PREFIX}/subscribe`;
    console.log("Connecting SSE to:", url);

    const eventSource = new EventSourcePolyfill(url, 
      {
        headers: {
          Authorization: localStorage.getItem("accessToken"),
        },
        withCredentials: true,
        heartbeatTimeout: 3000000
      }
    );

    eventSource.onopen = () => {
      console.log("SSE Connected");
      retryCount = 0;
    };

    eventSource.addEventListener("message", (e) => {
        console.log("📢 Notification:", e.data);
        const newNotification = {
          id: String(Date.now()),
          message: e.data // e.data에서 받은 문자열
        };
        if(onMessageCallback) onMessageCallback(newNotification);
      });

    eventSource.onerror = (error) => {
      eventSource.close();

      if (retryCount < MAX_RETRY) {
        retryCount++;
        console.log(`Retrying SSE connection... (${retryCount}/${MAX_RETRY})`);
        setTimeout(createConnection, 2000);
      } else {
        console.error("SSE Error:", error);
        console.log("Max retry attempts reached. SSE connection stopped.");
      }
    };

    return eventSource;
  };

  return createConnection(); // 첫 연결
};

const getNotifications = async () => {
  return axios.get( `${NOTIFICATION_API_PREFIX}/subscribe`)
    .then((res) => {
      console.log(res);
      return res;
    })
    .catch((err) => {
      console.log(err);
      return err;
    });
}

const updateReadNotification = async (id) => {
  return axios.put(`/notification/${id}`)
    .then((res) => {
      console.log(res);
      return res;
    }).catch((err) => {
      console.log(err);
    })
}

export { connectEventSource, getNotifications, updateReadNotification };