import '../assets/css/default-layout.css'
import BasicHeader from '../components/BasicHeader';

function BlankHeaderLayout({type, children}) {
    return (
        <div>
            {
                <BasicHeader/>
            }

            {/* Posts Feed */}
            <div className= {type === 'feed' ?  "feed" : "card"}>
                {children}
            </div>
        </div>
    );
}

export default BlankHeaderLayout;