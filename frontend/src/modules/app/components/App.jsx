import AppGlobalComponents from './AppGlobalComponents';
import Header from './Header';
import Body from './Body';
import Footer from './Footer';

const App = () => (
    <div className="d-flex flex-column min-vh-100">
        <Header />
        <div className="flex-grow-1">
            <Body />
        </div>
        <Footer />
        <AppGlobalComponents />
    </div>
);

export default App;
