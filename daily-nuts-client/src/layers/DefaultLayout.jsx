import Sidebar from "../components/Sidebar";
import '../assets/css/Default.css';

function DefaultLayout({children}) {
    return (
        <div className="defaultlayout">
            <div className="layout-wrapper">
                <Sidebar />
                <div className="main">
                    {children}
                </div>
            </div>
        </div>
    );
}

export default DefaultLayout;