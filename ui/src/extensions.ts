import {compose} from "redux";

export {}
declare global {
    interface Window {
        config: any;
        __REDUX_DEVTOOLS_EXTENSION_COMPOSE__?: typeof compose;

    }
}