import { DotLoader } from "react-spinners";

function Loader() {

    return (
        <div style={{ display: 'flex', 
                    alignItems: 'center', 
                    justifyContent: 'space-evenly',
                    flexDirection: 'column',
                    height: '120px'
                    }}>
            <DotLoader
                color='#1DBEB4'
                size={60}
                aria-label="Loading Spinner"
                data-testid="loader"
            />
            <p style={{fontSize: '18px'}}>Cargando...</p>
        </div>
    );
}
export default Loader;