import {Fragment} from 'react'
import * as yup from "yup";
import {yupResolver} from '@hookform/resolvers/yup';

import classnames from 'classnames'
import {toast, Slide} from 'react-toastify'
import {Link} from 'react-router-dom'
import {
    Row,
    Col,
    CardTitle,
    CardText,
    Form,
    Input,
    FormGroup,
    Label,
    Button
} from 'reactstrap'

import '../../../assets/scss/main/base/pages/page-auth.scss'
import InputPasswordToggle from "../../../components/input/InputPasswordToggle";
import {LoginRequestModel} from "../../model/auth/LoginRequestModel";
import {useForm} from 'react-hook-form';
import authService from "../../services/auth/AuthService";
import {appRoutes} from "../../../routes";

// Toast content component
const ToastContent = ({name, role}: { name: string | undefined; role: string | undefined }) => (
    <Fragment>
        <div className='toastify-body'>
            <h6 className='toast-title font-weight-bold'>Welcome, {name}</h6>
            <span>You have successfully logged in as an {role} user to FlowCRM. Now you can start to explore. Enjoy!</span>
        </div>
    </Fragment>
)

const Login = () => {
// Correct validation schema for username and password
    const defaultFormSchema = yup.object().shape({
        username: yup.string().required("Username is required").min(3, "Username must be at least 3 characters long"),
        password: yup.string().required("Password is required").min(6, "Password must be at least 6 characters long")
    }).required()
    const {handleSubmit, setValue, formState: {errors}} = useForm<LoginRequestModel>({
        resolver: yupResolver(defaultFormSchema)
    });

    const source = require(`../../../assets/images/pages/login/login-v2.svg`).default

    const onSubmit = (data: LoginRequestModel) => {
        authService.login(data.username, data.password).then(() => {
            toast.success(
                <ToastContent
                    name={data.username}
                    role={'Admin'}
                />,
                {
                    transition: Slide,
                    hideProgressBar: true,
                    autoClose: 800,
                    onClose: () => {
                        window.location.href = appRoutes.main;
                    }
                }
            );


        }).catch(error => {
            toast.error('Login failed!', {transition: Slide, autoClose: 2000});
        });
    };

    return (
        <div className='auth-wrapper auth-v2'>
            <Row className='auth-inner m-0'>
                <Link className='brand-logo' to='/' onClick={e => e.preventDefault()}>
                    <h2 className='brand-text text-primary ml-1'>FlowCRM</h2>
                </Link>
                <Col className='d-none d-lg-flex align-items-center p-5' lg='8' sm='12'>
                    <div className='w-100 d-lg-flex align-items-center justify-content-center px-5'>
                        <img className='img-fluid' src={source} alt='Login V2'/>
                    </div>
                </Col>
                <Col className='d-flex align-items-center auth-bg px-2 p-lg-5' lg='4' sm='12'>
                    <Col className='px-xl-2 mx-auto' sm='8' md='6' lg='12'>
                        <CardTitle tag='h2' className='font-weight-bold mb-1'>
                            Welcome to FlowCRM! 👋
                        </CardTitle>
                        <CardText className='mb-2'>Please sign-in to your account and start the adventure</CardText>
                        <Form className='auth-login-form mt-2' onSubmit={handleSubmit(onSubmit)}>
                            <FormGroup>
                                <Label className='form-label' for='login-username'>
                                    Username
                                </Label>
                                <Input
                                    autoFocus
                                    type='text'
                                    onChange={(event) => setValue("username", event.target.value)}
                                    id='username'
                                    name='username'
                                    placeholder='username'
                                    className={classnames({'is-invalid': errors.username})}
                                />
                                {errors.username && <div className="invalid-feedback">{errors.username.message}</div>}
                            </FormGroup>
                            <FormGroup>
                                <div className='d-flex justify-content-between'>
                                    <Label className='form-label' for='login-password'>
                                        Password
                                    </Label>
                                </div>
                                <InputPasswordToggle
                                    id='password'
                                    className={classnames({'is-invalid': errors.password})}
                                    onChange={(event: any) => setValue("password", event.target.value)}

                                />
                                {errors.password && <div className="invalid-feedback">{errors.password.message}</div>}
                            </FormGroup>
                            <Button type='submit' color='primary' block>
                                Sign in
                            </Button>
                        </Form>
                    </Col>
                </Col>
            </Row>
        </div>
    )
}

export default Login;
