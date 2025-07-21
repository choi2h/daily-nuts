import axios, { HttpStatusCode } from "axios"

const addLike = async (postId) => {
    return axios.post(`/post/${postId}/like`)
    .then((res) => {
        if(res.status !== HttpStatusCode.Created) return res.response;
        return res;
    }).catch((err) => {
        console.log(err);

        return err.response;
    })
}

const cancleLike = async (postId) => {
    return axios.delete(`/post/${postId}/like`)
    .then((res) => {
        console.log(res);

        return res;
    }).catch((err) => {
        console.log(err);

        return err;
    })
}

export {addLike, cancleLike};