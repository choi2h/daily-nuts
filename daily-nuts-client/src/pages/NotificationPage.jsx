import DefaultLayout from "../layers/DefaultLayout";

import PaymentHistory from "../components/PaymentHistory";
import { useEffect } from "react";
import { connectEventSource } from "../service/NotificationService";

function NotificationPage() {

    useEffect(() => {
        connectEventSource(2);
    }, [])

    return (
        <DefaultLayout className="app">
            {/* <PaymentHistory/> */}
            <h2>알림페이지</h2>
        </DefaultLayout>
    );
}

export default NotificationPage;