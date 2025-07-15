import { useState } from "react";
import DefaultLayout from "../layers/DefaultLayout";
import ProfileCard from "../components/ProfileCard";
import PaymentHistory from "../components/PaymentHistory";
import TabHeaderLyaout from "../layers/TabHeaderLayout";
import ExpertInfo from "../components/ExpertInfo";

const categories = [
    {
        name: "개인정보",
        api: "/me",
        components: <ProfileCard/>
    },
    {
        name: "전문가 등록 정보",
        api: "/expert",
        components: <ExpertInfo/>
    },
    {
        name: "결제내역",
        api: "/payment",
        components: <PaymentHistory/>
    }
]


function MyPage() {
    const [selectedCategory, setSelectedCategory] = useState(categories[0]);

    const chageCategory = (category) => {
        setSelectedCategory(category);
    }
    
    return (
        <DefaultLayout className="app">
            <TabHeaderLyaout 
                categories={categories} 
                selectedCategory={selectedCategory} 
                changeCategory={chageCategory}
                type = "card">
                {selectedCategory.components}
            </TabHeaderLyaout>
        </DefaultLayout>
    );
}

export default MyPage;