import DefaultLayout from '../layers/DefaultLayout';
import PostForm from '../components/PostForm';
import BlankHeaderLayout from '../layers/BlankHeaderLayout';

const PostWritePage = () => {
    return (
        <DefaultLayout className="app">
            <BlankHeaderLayout title="글 작성" type="card">
                    <PostForm />
            </BlankHeaderLayout>
        </DefaultLayout>
    );
};

export default PostWritePage;