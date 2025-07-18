const connectEventSource = (id) => {
  let retryCount = 0; // ✅ 개별 SSE마다 관리
  const MAX_RETRY = 1;

  const createConnection = () => {
    const url = `http://localhost:8081/notification/subscribe/${id}`;
    console.log("Connecting SSE to:", url);

    const eventSource = new EventSource(url);

    eventSource.onopen = () => {
      console.log("SSE Connected");
      retryCount = 0;
    };

    eventSource.onmessage = (event) => {
      console.log("Message Received:", event.data);
    //   if (onMessageCallback) onMessageCallback(event.data);
    };

    eventSource.onerror = (error) => {
      console.error("SSE Error:", error);
      eventSource.close();

      if (retryCount < MAX_RETRY) {
        retryCount++;
        console.log(`Retrying SSE connection... (${retryCount}/${MAX_RETRY})`);
        setTimeout(createConnection, 2000);
      } else {
        console.log("Max retry attempts reached. SSE connection stopped.");
      }
    };

    return eventSource;
  };

  return createConnection(); // 첫 연결
};

export { connectEventSource };