import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Card from 'react-bootstrap/Card';
import authService from "../../services/auth/AuthService";

const HomePage = () => {


    return <>
        <Container>
            <Row>
                {authService.isSystemAdminOrOwner() &&
                    <Col>
                        <Card style={{width: '18rem'}}>
                            <Card.Img variant="top" src="https://primefaces.org/cdn/primereact/images/usercard.png"/>
                            <Card.Body>
                                <Card.Title><h3>System Owner</h3></Card.Title>
                                <Card.Text>
                                    Info About System Owner
                                </Card.Text>
                            </Card.Body>
                        </Card>
                    </Col>
                }

                {authService.isSystemAdminOrOwner() &&
                    <Col>
                        <Card style={{width: '18rem'}}>
                            <Card.Img variant="top" src="https://primefaces.org/cdn/primereact/images/usercard.png"/>
                            <Card.Body>
                                <Card.Title><h3>System Admins</h3></Card.Title>
                                <Card.Text>
                                    List of System Admins
                                </Card.Text>
                            </Card.Body>
                        </Card>
                    </Col>
                }
                <Col>
                    <Card style={{width: '18rem'}}>
                        <Card.Img variant="top" src="https://primefaces.org/cdn/primereact/images/usercard.png"/>
                        <Card.Body>
                            <Card.Title><h3>Customer</h3></Card.Title>
                            <Card.Text>
                                List of Customers
                            </Card.Text>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>

    </>
}
export default HomePage