// ** React Imports
import {Suspense, FC} from 'react';

// ** Utils

// ** Custom Components
import LayoutWrapper from '../layouts/core/components/layout-wrapper';

// ** Router Components
import {BrowserRouter as AppRouter, Route, Routes, Navigate} from 'react-router-dom';

// ** Routes & Default Routes
import {DefaultRoute, Routes as AppRoutes} from './routes';

// ** Layouts
import VerticalLayout from '../layouts/core/VerticalLayout';
import LoginPage from "../app/pages/auth/LoginPage";
import ErrorPage from "../app/pages/error/ErrorPage";
import authService from "../app/services/auth/AuthService";
import {appRoutes} from "../routes";

// Types for route and layout
interface RouteMeta {
    action?: string;
    resource?: string;
    authRoute?: boolean;
    defaultRoute?: boolean;
    publicRoute?: boolean;
}

interface RouteProps {
    path: string;
    exact?: boolean;
    component: React.ComponentType<any>;
    layout?: string;
    meta?: RouteMeta;
    className?: string;
    appLayout?: boolean;
}

// Final Route Component


// Define Layouts
const Router: FC = () => {
    const FinalRoute: FC<{ route: RouteProps }> = ({route, ...props}) => {
        if (!authService.isAuthenticated() && (!route.meta || (!route.meta.authRoute && !route.meta.publicRoute))) {
            return <Navigate to={appRoutes.auth.login}/>;
        } else if (route.meta?.defaultRoute && authService.isAuthenticated()) {
            return <Navigate to={DefaultRoute}/>;
        } else {
            const Component = route.component;
            return <Component {...props} />;
        }
    };

    return (
        <AppRouter>
            <Routes>
                {authService.isAuthenticated() ? (
                    <>
                        {AppRoutes.map(route => (
                            <Route
                                key={route.path}
                                path={route.path}
                                element={
                                    <VerticalLayout>
                                        <Suspense fallback={null}>
                                            <LayoutWrapper>
                                                <FinalRoute route={route}/>
                                            </LayoutWrapper>
                                        </Suspense>
                                    </VerticalLayout>
                                }
                            />
                        ))}

                        {/* Catch-all route for undefined paths */}
                        <Route path="*" element={<ErrorPage/>}/>
                    </>
                ) : (
                    <Route path="*" element={<LoginPage/>}/>
                )}
            </Routes>
        </AppRouter>
    );
};

export default Router;
