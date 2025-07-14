import SubTab from "../components/SubTab";
import '../assets/css/default-layout.css'

function TabHeaderLyaout({categories, selectedCategory, changeCategory, type, header, children}) {
    return (
        <div>
            {
                <SubTab categories={categories} selectedCategory={selectedCategory} changeCategory={changeCategory}/>
            }

            {/* Posts Feed */}
            <div className= {type === 'feed' ?  "feed" : "card"}>
                {children}
            </div>
        </div>
    );
}

export default TabHeaderLyaout;