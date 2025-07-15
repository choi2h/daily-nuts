import Sidebar from "../components/Sidebar";
import '../assets/css/default-layout.css';
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