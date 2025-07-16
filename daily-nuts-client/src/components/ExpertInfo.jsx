import { useEffect, useState } from 'react';
import '../assets/css/ExpertInfo.css';

const info = {
  description : `성격심리학 박사 | 발달심리 전문가 | 종단연구 분석가
- 연세대학교 심리학 박사
- 성격 및 발달 종단 연구 다수 참여 (청소년 ~ 장노년층 대상)
-「성격의 안정성과 변화 가능성」 주제로 국내외 학술지 논문 다수 게재
- 성격의 유전율과 환경 영향, 성인기 이후 성격 변화에 대한 연구 중점
- 대중심리 칼럼, 기업 인성검사 자문, 방송 심리자문 등 경험 보유`,
  certifications : [
    {
      name: "Search_3c.jpg",
      url: "이미지 주소 받아오기"
    },
    {
      name: "Search_3c.jpg",
      url: "이미지 주소 받아오기"
    },
    {
      name: "Search_3c.jpg",
      url: "이미지 주소 받아오기"
    }
  ]
}

const ExpertInfo = () => {
  const [isEditing, setIsEditing] = useState(false);
  const [formData, setFormData] = useState({
    profileDescription: info.description,
    files: info.certifications,         // 실제 파일
    previewUrls: []    // 미리보기 URL
  });

const handleFileUpload = (e) => {
  const uploadedFiles = Array.from(e.target.files);
  const newPreviewUrls = uploadedFiles.map(file => URL.createObjectURL(file));

  setFormData(prev => ({
    ...prev,
    files: [...prev.files, ...uploadedFiles],
    previewUrls: [...prev.previewUrls, ...newPreviewUrls]
  }));
};

  const handleTextChange = (e) => {
    setFormData({
      ...formData,
      profileDescription: e.target.value
    });
  };

  const removeFile = (index) => {
    const updatedFiles = formData.files.filter((_, i) => i !== index);
    setFormData({
      ...formData,
      files: updatedFiles
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('전문가 등록 정보:', formData);
    setIsEditing(false);
    // 여기에 실제 제출 로직을 추가하세요
  };

  const handleEdit = () => {
    setIsEditing(true);
  };

  const handleCancel = () => {
    setIsEditing(false);
    setFormData({
      profileDescription: info.description,
      files: []
    });
  };


  // 서버 업로드
  // const handleUploadToServer = async () => {
  //   if (files.length === 0) {
  //     alert("업로드할 파일이 없습니다.");
  //     return;
  //   }

  //   const formData = new FormData();
  //   files.forEach((file) => formData.append("files", file));

  //   try {
  //     const response = await axios.post("/api/upload", formData, {
  //       headers: { "Content-Type": "multipart/form-data" },
  //     });
  //     alert("업로드 성공!");
  //     console.log(response.data);
  //   } catch (error) {
  //     console.error("업로드 실패", error);
  //   }
  // };
  

    // 컴포넌트 언마운트 시 메모리 해제
  useEffect(() => {
    return () => {
      formData.previewUrls.forEach((url) => URL.revokeObjectURL(url));
    };
  }, [formData.previewUrls]);



  return (
    <div className="profile-card">
      <h2 className="page-title">전문가 등록 정보</h2>
      
      {!isEditing ? (
        // 조회 화면
        <div className="info-view">
          <div className="info-section">
            <h3 className="section-title">프로필 설명</h3>
            <div className="profile-description">
              {info.description}
            </div>
          </div>

          <div className="info-section">
            <h3 className="section-title">첨부자료</h3>
            <div className="file-list">
              {info.certifications.map((cert, index) => (
                <div key={index} className="file-item-view">
                  <div className="file-info">
                    <span className="file-icon">📄</span>
                    <span className="file-name">{cert.name}</span>
                  </div>
                </div>
              ))}
            </div>
          </div>

          <button onClick={handleEdit} className="edit-button">
            수정하기
          </button>
        </div>
      ) : (
        // 수정 화면
        <form onSubmit={handleSubmit} className="registration-form">
          <div className="form-section">
            <h3 className="section-title">프로필 설명</h3>
            <textarea
              className="profile-textarea"
              value={formData.profileDescription}
              onChange={handleTextChange}
              placeholder={info.description}
              rows="8"
            />
          </div>

          <div className="form-section">
            <h3 className="section-title">첨부자료</h3>
            
            <div className="file-upload-section">
              <label htmlFor="file-upload" className="file-upload-label">
                <div className="upload-icon">📁</div>
                파일 선택
              </label>
              <input
                id="file-upload"
                type="file"
                multiple
                accept=".jpg,.jpeg,.png,.pdf,.doc,.docx"
                onChange={handleFileUpload}
                className="file-input"
              />
            </div>

            <div className="preview-container">
              {formData.previewUrls.map((url, index) => (
                <img key={index} src={url} alt={`preview-${index}`} className="preview-image" />
              ))}
            </div>

            <div className="file-list">
              {formData.files.map((file, index) => (
                <div key={index} className="file-item">
                  <div className="file-info">
                    <span className="file-icon">📄</span>
                    <span className="file-name">{file.name}</span>
                  </div>
                  <button
                    type="button"
                    onClick={() => removeFile(index)}
                    className="remove-file-btn"
                  >
                    ×
                  </button>
                </div>
              ))}
            </div>
          </div>

          <div className="button-group">
            <button type="submit" className="submit-button">
              저장하기
            </button>
            <button type="button" onClick={handleCancel} className="cancel-button">
              취소
            </button>
          </div>
        </form>
      )}
    </div>
  );
};

export default ExpertInfo;