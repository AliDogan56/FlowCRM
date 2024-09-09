import {Container, Row, Col, Form, Button, Alert} from 'react-bootstrap';
import {useState} from "react";
import authService from "../../services/auth/AuthService";
import {toast} from "react-toastify";

const LoginPage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState<string | null>(null);

    const handleSubmit = (event: React.FormEvent) => {
        event.preventDefault();
        if (username === '' || password === '') {
            setError('Please enter the username or password!');
            return;
        }
        if (username !== '' && password !== '') {
            setError(null);
        }
        authService.login(username, password).then(response => {
                toast.success("LoginSuccessful");
            },
            error => {
                console.error(error);
                toast.error(error.response.data.message);
            });
    };
    return <>
        <Container className="d-flex align-items-center justify-content-center min-vh-100">
            <Row className="w-100">
                <Col md={6} lg={4} className="mx-auto">
                    <div className="p-4 border rounded bg-light shadow-sm">
                        <h3 className="text-center mb-4">Login</h3>
                        {error && <Alert variant="danger">{error}</Alert>}
                        <Form onSubmit={handleSubmit}>
                            <Form.Group controlId="formBasicUsername">
                                <Form.Label>Username</Form.Label>
                                <Form.Control
                                    type="text"
                                    placeholder="Please enter username"
                                    value={username}
                                    onChange={(e) => setUsername(e.target.value)}
                                />
                            </Form.Group>

                            <Form.Group controlId="formBasicPassword">
                                <Form.Label>Şifre</Form.Label>
                                <Form.Control
                                    type="password"
                                    placeholder="Please enter your password"
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                />
                            </Form.Group>

                            <Button variant="primary" type="submit" className="w-100 mt-3">
                                Login
                            </Button>
                        </Form>
                    </div>
                </Col>
            </Row>
        </Container>
    </>
}
export default LoginPage