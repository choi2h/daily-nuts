import '../assets/css/Default.css'
import BasicHeader from '../components/BasicHeader';

function BlankHeaderLayout({type, children, isUseSearch, title}) {
    return (
        <div>
            {
                <BasicHeader isUseSearc={isUseSearch} title={title}/>
            }

            {/* Posts Feed */}
            <div className= {type === 'feed' ?  "feed" : "card"}>
                {children}
            </div>
        </div>
    );
}

export default BlankHeaderLayout;