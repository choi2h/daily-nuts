import SubscribeItem from "../components/SubscribeItem";
import BlankHeaderLayout from "../layers/BlankHeaderLayout";
import DefaultLayout from "../layers/DefaultLayout";

const subscribeInfos = [
    {
        name: '김00',
        subscriberCount: 30,
        subscribeDate: '2025-07-11',
    },
    {
        name: '박00',
        subscriberCount: 20,
        subscribeDate: '2025-07-11',
    },
    {
        name: '김00',
        subscriberCount: 10,
        subscribeDate: '2025-07-11',
    },
    {
        name: '최00',
        subscriberCount: 80,
        subscribeDate: '2025-07-11',
    },
    {
        name: '김00',
        subscriberCount: 120,
        subscribeDate: '2025-07-11',
    }
]

function SubscribeListPage() {
    return (
        <DefaultLayout>
            <BlankHeaderLayout>
                <div className="posts-section">
                <div className="posts-header">
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