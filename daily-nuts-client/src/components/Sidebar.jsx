import { useLocation, useNavigate } from 'react-router';
import { useEffect, useState } from 'react';
import { IoHome, IoHeartOutline, IoBookmarkOutline, IoPersonOutline, IoNotificationsOutline, IoLogOutOutline, IoAddOutline, IoNewspaperOutline } from 'react-icons/io5';
import '../assets/css/Sidebar.css';
import logo from '../assets/images/daily-nuts-logo.png';
import defaultProfile from '../assets/images/default-profile.png';
import axios from 'axios';

const navs = [
    {
        name: "최신피드",
        api: "/",
        icon: <IoHome />
    },
    {
        name: "구독피드",
        api: "/subscribe/feed",
        icon: <IoNewspaperOutline />
    },
    {
        name: "좋아요",
        api: "/posts/likes",
        icon: <IoHeartOutline />
    },
    {
        name: "구독목록",
        api: "/subscribe",
        icon: <IoBookmarkOutline />
    },
    {
        name: "마이페이지",
        api: "/mypage",
        icon: <IoPersonOutline />
    },
    {
        name: "알림",
        api: "/notification",
        icon: <IoNotificationsOutline />
    }
]

function Sidebar() {
    const [role, setRole] = useState(localStorage.getItem("role"));
    const isExpert = role === "EXPERT";

    const {pathname} = useLocation();
    const navigate = useNavigate();
    const isLogin = localStorage.getItem("loginId");
    const memberName = localStorage.getItem("name");

    useEffect(() => {
        const updateRole = () => {
            const newRole = localStorage.getItem("role");
            setRole(newRole);
        };

        updateRole();

        window.addEventListener("storage", updateRole);

        return () => {
            window.removeEventListener("storage", updateRole);
        };
    }, []);

    const handleNav = (nav) => {
        navigate(nav.api);
    }

    const handleLogin = () => {
        console.log('로그인');
        navigate('/login');
    }

    const handleLogout = async () => {
        // 로그아웃 로직 구현
        if (window.confirm("정말 로그아웃 하시겠습니까?")) {
            try {
                await axios.get('/member/logout');
            } catch (err) {
                console.warn('로그아웃 실패', err);
            } finally {
                localStorage.clear();
                delete axios.defaults.headers.common['Authorization'];
                delete axios.defaults.headers.common['Refresh-Token'];
                window.dispatchEvent(new Event("storage"));
                navigate('/login');
                console.log('로그아웃');
            }
        }
    };

    const handleCreatePost = () => {
        console.log('게시글 작성');

        if (!isExpert) {
            alert("전문가만 글을 작성할 수 있습니다!");
            return;
        }

        navigate('/post/write');
    };

     const moveProfile = () => {
        const id = JSON.parse(localStorage.getItem("memberId"));
        console.log(id);
        navigate(`/profile/${id}`);
    }

    return (
        <div className='sidebar'>
            <div>
                <div className="logo">
                    <img className="logo-item" src={logo} alt="logo"/>
                </div>
                
                <ul className="nav-menu">
                { isLogin ? (
                    <div>
                        <div className="profile-info-section" onClick={localStorage.getItem("role") === "EXPERT" ? moveProfile : ''}>
                            <div className="profile-avatar">
                                <img className="profile-image" src={defaultProfile} alt="Profile" />
                            </div>
                            <div className="profile-info-names">
                                <h3>{memberName}</h3>
                                <p>{isLogin}</p>
                            </div>
                        </div>

                        {/* 게시글 작성 버튼 */}
                        {isExpert && (
                            <div className="create-post-btn" onClick={handleCreatePost}>
                                <IoAddOutline className="create-post-icon" />
                                <span>게시글 작성</span>
                            </div>
                        )}

                        {
                            navs.map((nav) => (
                                <NavItem nav={nav} handleNav={handleNav} currentPath={pathname}/>
                            ))
                        }
                    </div>
                ) : 
                (
                    <NavItem nav={navs[0]} handleNav={handleNav} currentPath={pathname}/>
                )
            
                }
                </ul>
            </div>

            {
                isLogin?
                    (
                        <div className="logout" onClick={handleLogout}>
                            <IoLogOutOutline className="logout-icon" />
                            <h4>로그아웃</h4>
                        </div>
                    ) :
                    (
                        <button type="submit" className="login" onClick={handleLogin}>
                            <h4>로그인</h4>
                        </button>
                    ) 
            }
        </div>
    );
}

function NavItem ({nav, handleNav, currentPath}) {
    return (
        <li 
        key={nav.api} 
        className={`nav-item ${nav.api === currentPath ? 'active' : ''}`}
        >
        <a href={nav.api} onClick={(e) => {
            e.preventDefault();
            handleNav(nav);
        }}>
            <span className="nav-icon">{nav.icon}</span>
            <span className="nav-text">{nav.name}</span>
        </a>
        </li>
    )
}

export default Sidebar;