import Sidebar from "../components/Sidebar";
import '../assets/css/Default.css';
function DefaultLayout({children}) {
    return (
        <div>
            <Sidebar />
            <div className="main">
                {children}
            </div>
        </div>
    )
}

export default DefaultLayout;