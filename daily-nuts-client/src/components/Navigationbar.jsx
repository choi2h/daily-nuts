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

function Navigationbar() {
    const location = useLocation();
    console.log(location);
    const navigate = useNavigate();

    // 사용자 정보 (실제로는 상태 관리나 API에서 가져와야 함)
    const userInfo = {
        name: "Victor UX/UI",
        userId: "@victoruxui"
    };

    const updateNav = (nav) => {
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
        <div className="sidebar">
            <div className="sidebar-content">
            <div className="logo">
                <div className="logo-icon">
                    <img className="thumbnail" src={logo} alt="cat"></img>
                </div>
            </div>

            <div className="user-profile">
                <div className="profile-avatar">
                    <img src={defaultProfile} alt="Profile" />
                </div>
                <div className="profile-info">
                    <div className="profile-name">{userInfo.name}</div>
                    <div className="profile-userid">{userInfo.userId}</div>
                </div>
            </div>
            
            <nav className="nav">
                {
                    navs.map((nav, idx) => {
                        return (
                            <div  
                                key = {idx}
                                href={nav.api} 
                                className={`nav-item ${nav.api === location.pathname ? 'active' : ''}`}
                                onClick={() => updateNav(nav)}>
                                {/* <Home size={24} /> */}
                                {nav.name}
                            </div>
                        )
                    })
                }
            </nav>

            <div className="logout-section">
                <div className="nav-item logout-button" onClick={handleLogout}>
                    로그아웃
                </div>
            </div>
            </div>
        </div>
    );
}

export default Navigationbar;