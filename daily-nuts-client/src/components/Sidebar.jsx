import { useLocation, useNavigate } from 'react-router';
import '../assets/css/navigationbar.css';
import logo from '../assets/images/daily-nuts-logo.png';
import defaultProfile from '../assets/images/default-profile.png';

const navs = [
    {
        name: "피드",
        api: "/"
    },
    {
        name: "좋아요",
        api: "/posts/likes"
    },
    {
        name: "마이페이지",
        api: "/mypage"
    }
]

function Sidebar() {
    const location = useLocation();
    console.log(location);
    const navigate = useNavigate();

    const handleNav = (nav) => {
        console.log('hello');
        navigate(nav.api);
    }

    const handleLogout = () => {
        // 로그아웃 로직 구현
        console.log('로그아웃');
        // 예: localStorage.removeItem('token');
        // 예: navigate('/login');
    };

    return (
        <div className='sidebar'>
            <div>
                <div className="logo">
                    <img className="logo-item" src={logo} alt="logo"/>
                </div>
                
                <div className="profile-info-section">
                    <div className="profile-avatar">
                        <img className="profile-image" src={defaultProfile} alt="Profile" />
                    </div>
                    <div className="profile-info-names">
                        <h3>김이름</h3>
                        <p>@loginId</p>
                    </div>
                </div>
                <ul className="nav-menu">
                {navs.map((nav) => (
                    <li 
                    key={nav.api} 
                    className={`nav-item  ${nav.api === location.pathname ? 'active' : ''}`}
                    >
                    <a href={nav.api} onClick={(e) => {
                        e.preventDefault();
                        handleNav(nav);
                    }}>
                        {nav.name}
                    </a>
                    </li>
                ))}
                </ul>
            </div>

            <div className="sidebar-footer" onClick={() => handleLogout}>
                <h4>로그아웃</h4>
            </div>
        </div>
    );
}

export default Sidebar;