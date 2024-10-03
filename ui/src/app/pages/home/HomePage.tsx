import authService from "../../services/auth/AuthService";
import {CardTitle, CardText, CardImg, Row, Col, Card, CardBody} from "reactstrap";
import {appRoutes} from "../../../routes";
import {useNavigate} from "react-router-dom";

const HomePage = () => {
    const isAdminOrOwner = authService.isSystemAdminOrOwner();
    const navigate = useNavigate();


    return <>
        <div>
            <Row className={"match-height"}>
                {isAdminOrOwner &&
                    <Col onClick={() => {
                        navigate(`/${appRoutes.user.systemowner.list}`);
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
                        navigate(`/${appRoutes.user.systemadmin.list}`);
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
                    navigate(`/${appRoutes.user.customer.list}`);
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