import Navigationbar from "../components/Navigationbar";
import '../assets/css/default-layout.css';
function DefaultLayout({children}) {
    return (
        <div>
            <Navigationbar />
            <div className="main">
                {children}
            </div>
        </div>
    )
}

export default DefaultLayout;