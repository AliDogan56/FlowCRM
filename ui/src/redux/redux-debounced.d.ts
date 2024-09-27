declare module 'redux-debounced' {
    import { Middleware } from 'redux';

    // Define the types for redux-debounced if known
    const createDebounce: () => Middleware;
    export default createDebounce;
}
