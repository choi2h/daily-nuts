import axios from "axios"

const API_BASE_URL = import.meta.env.VITE_API_SERVER_ADDRESS;
const EXPERT_INFO_URL = `${API_BASE_URL}/member/expert`;
axios.defaults.withCredentials = true;


const saveExpertInfo = async (description, files) => {
    const formData = new FormData();

    const json = JSON.stringify({ description });
    formData.append("info", new Blob([json], { type: "application/json" }));

    files.forEach((file) => formData.append("files", file));

    return axios.post(EXPERT_INFO_URL, formData, {
        headers: { "Content-Type" : "multipart/form-data"},
        withCredentials: true       // ← 이걸 설정해야 쿠키가 자동으로 저장됨
    }).then((res) => {
        console.log(res);
        return res;
    }).catch((error) => {
        console.log(error);
        return error;
    })
}

const getExpertInfo = async () => {
    return axios.get(EXPERT_INFO_URL, { withCredentials: true })
        .then((res) => {
            console.log(res);
            return res;
        }).catch((err) => {
            console.log(err);
        });
}

const updateExpertInfo = async (description, files) => {
    const formData = new FormData();

    const json = JSON.stringify({ description });
    formData.append("info", new Blob([json], { type: "application/json" }));

    files.forEach((file) => formData.append("files", file));

    return axios.put(EXPERT_INFO_URL, formData, {
        headers: { "Content-Type" : "multipart/form-data"},
        withCredentials: true       // ← 이걸 설정해야 쿠키가 자동으로 저장됨
    }).then((res) => {
        console.log(res);
        return res;
    }).catch((error) => {
        console.log(error);
    })
}

export {saveExpertInfo, getExpertInfo, updateExpertInfo};