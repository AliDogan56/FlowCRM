import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Card from 'react-bootstrap/Card';
import authService from "../../services/auth/AuthService";
import {CardBody} from "react-bootstrap";
import {CardTitle, CardText, CardImg} from "reactstrap";
import {appRoutes} from "../../../routes";

const HomePage = () => {
    const isAdminOrOwner = authService.isSystemAdminOrOwner();


    return <>
        <div>
            <Row className={"match-height"}>
                {isAdminOrOwner &&
                    <Col onClick={() => {
                        window.location.href = appRoutes.user.systemowner.list
                    }}>
                        <Card>
                            <CardImg variant="top" src="https://primefaces.org/cdn/primereact/images/usercard.png"/>
                            <CardBody>
                                <CardTitle><h3>System Owner</h3></CardTitle>
                                <CardText>
                                    Info About System Owner
                                </CardText>
                            </CardBody>
                        </Card>
                    </Col>
                }

                {isAdminOrOwner &&
                    <Col onClick={() => {
                        window.location.href = appRoutes.user.systemadmin.list
                    }}>
                        <Card>
                            <CardImg variant="top" src="https://primefaces.org/cdn/primereact/images/usercard.png"/>
                            <CardBody>
                                <CardTitle><h3>System Admins</h3></CardTitle>
                                <CardText>
                                    List of System Admins
                                </CardText>
                            </CardBody>
                        </Card>
                    </Col>
                }
                <Col onClick={() => {
                    window.location.href = appRoutes.user.customer.list
                }}>
                    <Card>
                        <CardImg variant="top" src="https://primefaces.org/cdn/primereact/images/usercard.png"/>
                        <CardBody>
                            <CardTitle><h3>Customer</h3></CardTitle>
                            <CardText>
                                List of Customers
                            </CardText>
                        </CardBody>
                    </Card>
                </Col>
            </Row>
        </div>

    </>
}
export default HomePage