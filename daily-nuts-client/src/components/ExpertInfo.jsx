import { useState, useEffect } from 'react';
import { getExpertInfo, saveExpertInfo, updateExpertInfo } from '../service/ExpertInfoService';
import ExpertInfoView from './ExpertInfoView';
import ExpertInfoForm from './ExpertInfoForm';
import '../assets/css/ExpertInfo.css';
import { HttpStatusCode } from 'axios';

const ExpertInfo = () => {
  const [isEditing, setIsEditing] = useState(false);
  const [isRegistering, setIsRegistering] = useState(false);
  const [info, setInfo] = useState(null);

  useEffect(() => {
    getExpertInfo().then((res) => {
      console.log(res);
      setInfo(res?.data || null);
    });
  }, []);

  const handleEdit = () => {
    setIsEditing(true);
    setIsRegistering(false);
  };

  const handleRegister = () => {
    setIsRegistering(true);
    setIsEditing(false);
  };

  const handleCancel = () => {
    setIsEditing(false);
    setIsRegistering(false);
  };

  const handleSave = (formData) => {
    const action = isRegistering ? saveExpertInfo : updateExpertInfo;
    action(formData.description, formData.files).then((res) => {
      if((isRegistering && res.status === HttpStatusCode.Created) || 
      (!isRegistering && res.status === HttpStatusCode.Ok)) {
        console.log('저장 완료:', res);
        setIsEditing(false);
        setIsRegistering(false);
        setInfo(res.data);
        if(isRegistering) localStorage.setItem("role", res.data.role);
        window.location.reload();
      } else {
        alert(res.data.message);
      }
    });
  };

  return (
    <div className="profile-card">
      <h2 className="page-title">전문가 등록 정보</h2>

      {!isEditing && !isRegistering && (
        info && info.description ? (
          <ExpertInfoView info={info} onEdit={handleEdit} />
        ) : (
          <div className="empty-info">
            <p>전문가 등록 정보가 존재하지 않습니다.</p>
            <button className="register-button" onClick={handleRegister}>
              등록하기
            </button>
          </div>
        )
      )}

      {(isEditing || isRegistering) && (
        <ExpertInfoForm
          initialData={isEditing ? info : { description: '', files: [] }}
          onCancel={handleCancel}
          onSave={handleSave}
        />
      )}
    </div>
  );
};

export default ExpertInfo;