import { useEffect, useState } from "react";
import SubscribeItem from "../components/SubscribeItem";
import BlankHeaderLayout from "../layers/BlankHeaderLayout";
import DefaultLayout from "../layers/DefaultLayout";
import { getSubscribeExperts } from "../service/MemberInfoService";

function SubscribeListPage() {
    const [subscribeInfos, setSubscribeInfos] = useState([]);

    useEffect(() => {
        getSubscribeExperts().then((res) => {
            console.log('Subscribe experts!!! : ' + res.data.experts);
            setSubscribeInfos(res.data.experts);
        })
    }, []);

    return (
        <DefaultLayout>
            <BlankHeaderLayout type='feed' isUseSearch={false} title='구독목록'>
            <div className="subscribe-container">
                <div className="count-header">
                    <span className="posts-label">구독 수</span>
                    <span className="posts-count">{subscribeInfos.length}</span>
                </div>

                <div className="main-content">
                    <div>
                    {subscribeInfos.map((subscribe, idx) => 
                        <SubscribeItem key={idx} subscribeInfo={subscribe}/>
                        )}
                    </div>
                </div>
            </div>
            </BlankHeaderLayout>
        </DefaultLayout>
    )
}

export default SubscribeListPage;