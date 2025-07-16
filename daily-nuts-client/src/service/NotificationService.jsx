const connectEventSource = (id) => {
    const eventSource = new EventSource(`http://localhost:8081/notification/subscribe/${id}`);
    console.log("connect!!!!!");
    
    eventSource.onmessage = async (event) => {
        console.log("receive!!!!!");
        const res = await event.data;
        console.log(`Receive event!!!! ${res}`);
    }

    eventSource.onerror = async (error) => {
        console.log("ERROR!!!!!!");
        console.log(error);
        eventSource.close();
        if(error.target.readyState === EventSource.CLOSED) {
            connectEventSource(id);
            // console.log("종료되었습니다.");
        }
    }
}

export {connectEventSource};