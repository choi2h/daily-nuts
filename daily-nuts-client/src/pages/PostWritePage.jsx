import DefaultLayout from '../layers/DefaultLayout';
import PostForm from '../components/PostForm';
import BlankHeaderLayout from '../layers/BlankHeaderLayout';

const PostWritePage = () => {
    return (
        <DefaultLayout className="app">
            <BlankHeaderLayout>
                <div className="post-detail">
                    <PostForm />
                </div>
            </BlankHeaderLayout>
        </DefaultLayout>
    );
};

export default PostWritePage;